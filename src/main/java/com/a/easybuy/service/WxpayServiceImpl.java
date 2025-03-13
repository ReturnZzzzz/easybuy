package com.a.easybuy.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a.easybuy.dao.OrderMapper;
import com.a.easybuy.pojo.Order;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.util.WxPayConfig;
import com.wechat.pay.contrib.apache.httpclient.exception.ValidationException;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class WxpayServiceImpl implements WxpayService {
    private Logger logger = LoggerFactory.getLogger(WxpayServiceImpl.class);
    @Autowired
    private CloseableHttpClient wxPayClient;
    @Autowired
    private WxPayConfig wxPayConfig;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderDao;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Map<String, String> createPayQrCode(String orderId) throws Exception {
        logger.info("WxpayServiceImpl createPayQrCode params:" + orderId);
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderId);
        ResponseMessage msg = orderService.getOne(params);
        Order order = (Order) msg.getData();
        String orderName = "易买网订单支付";
        String orderNo = order.getOrderCode();
        JSONObject requestBody = JSONUtil.createObj()
                .set("appid", wxPayConfig.getAppId())
                .set("mchid", wxPayConfig.getMchId())
                .set("description", orderName)
                .set("out_trade_no", orderNo)
                .set("notify_url", wxPayConfig.getNotifyUrl())
                .set("amount", JSONUtil.createObj()
                        .set("total", new BigDecimal("0.01").multiply(BigDecimal.valueOf(100)).intValue())  // 金额单位转为分
                        .set("currency", "CNY"));

        // 3. 创建HTTP请求（需使用配置证书的HttpClient）
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/native");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(requestBody.toString(), StandardCharsets.UTF_8));

        // 4. 发送请求并处理响应
        try (CloseableHttpResponse response = wxPayClient.execute(httpPost)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject result = JSONUtil.parseObj(responseBody);
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("微信支付请求失败: {}" + result);
                throw new RuntimeException("支付二维码生成失败: " + result.getStr("message"));
            }
            return new HashMap<String, String>() {{
                put("codeUrl", result.getStr("code_url"));  // 支付二维码链接
                put("orderNo", orderNo);  // 返回生成的商户订单号
            }};
        } catch (IOException e) {
            logger.error("微信支付通信异常", e);
            throw new RuntimeException("支付系统通信异常");
        }
    }

    @Override
    public ResponseMessage handleWxPayNotification(Map<String, String> params) throws Exception {
        logger.info("WxPayServiceImpl handleWxPayNotification param " + params);
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            //构建通知处理器（使用配置类初始化的验证器）
            NotificationHandler notificationHandler = new NotificationHandler(wxPayConfig.getVerifier(), wxPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
            //构建通知请求对象
            NotificationRequest request = new NotificationRequest.Builder()
                    .withSerialNumber(params.get("Wechatpay-Serial"))
                    .withNonce(params.get("Wechatpay-Nonce"))
                    .withTimestamp(params.get("Wechatpay-Timestamp"))
                    .withSignature(params.get("Wechatpay-Signature"))
                    .withBody(params.get("body")) // 原始报文，不能修改顺序
                    .build();

            // 解析并验证通知（自动完成验签和解密）
            Notification notification = notificationHandler.parse(request);

            // 获取解密后的交易数据
            String plainText = notification.getDecryptData();
            JSONObject result = JSONUtil.parseObj(plainText);
            logger.debug("解析后的回调数据: {}" + result);

            // 5. 获取订单信息和交易状态
            String outTradeNo = result.getStr("out_trade_no"); // 商户订单号
            String transactionId = result.getStr("transaction_id"); // 微信支付订单号
            String tradeState = result.getStr("trade_state"); // 交易状态

            // 6. 处理交易结果
            if ("SUCCESS".equals(tradeState)) {
                // 6.1 成功逻辑
                logger.info("订单支付成功: outTradeNo={}, transactionId={}" + outTradeNo + transactionId);
                Order order = new Order();
                order.setOrderCode(outTradeNo);
                order.setStatus(2);
                orderMapper.pay(order);
                responseMessage.setCode("200");
                responseMessage.setMsg("SUCCESS");
                responseMessage.setData(result);
            } else {
                // 6.3 失败逻辑
                logger.warn("订单支付失败: outTradeNo={}, tradeState={}" + outTradeNo + tradeState);
                responseMessage.setCode("201");
                responseMessage.setMsg("支付失败: " + tradeState);
            }

        } catch (ValidationException e) {
            // 7. 验签失败处理
            logger.error("验签失败: {}" + e.getMessage());
            responseMessage.setCode("200");
            responseMessage.setMsg("验签失败: " + e.getMessage());
        } catch (Exception e) {
            // 8. 其他异常处理
            logger.error("回调处理异常: ", e);
            responseMessage.setCode("500");
            responseMessage.setMsg("系统异常: " + e.getMessage());
        }
        return responseMessage;
    }

    public ResponseMessage closeWxpayOrder(String orderNo) throws Exception {
        logger.info("WxPayServiceImpl closeWxpayOrder orderNo:" + orderNo);
        ResponseMessage responseMsg = new ResponseMessage();
        JSONObject requestBodyClose = JSONUtil.createObj()
                .set("Authorization", "WECHATPAY2-SHA256-RSA2048 ")
                .set("appid", wxPayConfig.getAppId())
                .set("mchid", wxPayConfig.getMchId())
                .set("description", "易买网订单支付")
                .set("out_trade_no", orderNo);

        String urlClose = String.format("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/%s/close", orderNo);
        // 3. 创建HTTP请求（需使用配置证书的HttpClient）
        HttpPost httpPost = new HttpPost(urlClose);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(requestBodyClose.toString(), StandardCharsets.UTF_8));

        String urlQuery = String.format("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/%s?mchid=%s",
                orderNo, wxPayConfig.getMchId());
        HttpGet httpGet = new HttpGet(urlQuery);
        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.setHeader("Authorization", "WECHATPAY2-SHA256-RSA2048 " + "mchid=\"" + wxPayConfig.getMchId() + "\""
                + "out_trade_no\"" + orderNo + "\"");
        try (CloseableHttpResponse response1 = wxPayClient.execute(httpGet)) {
            if (response1.getStatusLine().getStatusCode() == 200) {
                try (CloseableHttpResponse response2 = wxPayClient.execute(httpPost)) {
                    if (response2.getStatusLine().getStatusCode() == 200) {
                        responseMsg.setCode("200");
                    }
                } catch (IOException e) {
                    logger.error("微信关闭订单异常", e);
                    throw new RuntimeException("关闭订单异常");
                }
            } else {
                responseMsg.setCode("200");
            }
        } catch (IOException e) {
            logger.error("微信查询订单异常", e);
            throw new RuntimeException("查询订单异常");
        }
        return responseMsg;
    }

    @Override
    public ResponseMessage checkPaymentStatus(String orderNo) throws Exception {
        logger.info("WxPayServiceImpl checkPaymentStatus orderNo:" + orderNo);
        ResponseMessage msg = new ResponseMessage();
        // 1. 构建查单请求
        String urlQuery = String.format("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/%s?mchid=%s",
                orderNo, wxPayConfig.getMchId());
        HttpGet httpGet = new HttpGet(urlQuery);
        httpGet.addHeader("Accept", "application/json");
//        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("mchid", wxPayConfig.getMchId());
        httpGet.addHeader("out_trade_no", orderNo);
        // 2. 发送请求
        try (CloseableHttpResponse response = wxPayConfig.wxPayHttpClient().execute(httpGet)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject result = JSONUtil.parseObj(responseBody);
            if (response.getStatusLine().getStatusCode() == 200 && "SUCCESS".equals(result.getStr("trade_state"))) {
                msg.setCode("200");
            }
        }
        return msg;
    }
}
