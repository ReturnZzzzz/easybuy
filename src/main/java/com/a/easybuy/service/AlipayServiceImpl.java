package com.a.easybuy.service;

import com.a.easybuy.pojo.Alipay;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.util.AlipayUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class AlipayServiceImpl implements AlipayService{

    private Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);

    @Autowired
    private Alipay alipay;

    @Autowired
    private AlipayUtil alipayUtil;
    @Override
    public ResponseMessage createOrder(String subject,String Amount) {
        logger.info("createOrder is start...");
        ResponseMessage message = new ResponseMessage();
        try {
            // 初始化SDK
            AlipayClient alipayClient = new DefaultAlipayClient(alipayUtil.getAlipayConfig());
            // 构造请求参数以调用接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            // 设置商户订单号
            model.setOutTradeNo(UUID.randomUUID().toString());
            // 设置订单总金额
            model.setTotalAmount(Amount);
            // 设置订单标题
            model.setSubject(subject);
            // 设置产品码
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            request.setBizModel(model);
            // 第三方代调用模式下请设置app_auth_token
            // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            // 如果需要返回GET请求，请使用
            // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
            String pageRedirectionData = response.getBody();
            System.out.println(pageRedirectionData);
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
            if (response.isSuccess()) {
                message.setCode("200");
                message.setData(pageRedirectionData);
                logger.debug("createOrder success request:"+request+",pageRedirectionData:"+pageRedirectionData);
                System.out.println("调用成功");
            } else {
                message.setCode("201");
                message.setData(response.getMsg());
                logger.error("createOrder error request:"+request+",pageRedirectionData:"+pageRedirectionData);
            }
        }catch (Exception e){
            message.setCode("500");
            logger.error("AlipayServiceImpl createOrder error:"+e);
        }
        return message;
    }

    @Override
    public   ResponseMessage alipayNotify(Map<String, String> map) throws AlipayApiException {
        logger.info("AlipayServiceImpl method alipayNotify params " + map);
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            if (map == null || map.isEmpty()) {
                logger.error("AlipayServiceImpl method alipayNotify params is null or empty");
                responseMessage.setCode("301");
                responseMessage.setMsg("ill is empty.pleas check ill");
                return responseMessage;
            }
            // 2. 签名验证
            boolean signVerified = AlipaySignature.rsaCheckV1(
                    map,                             // 支付宝回调参数
                    alipay.getAlipayPublicKey(),
                    "utf-8",
                    "RSA2"
            );
            if (signVerified) {
                // 获取订单信息
                String outTradeNo = map.get("out_trade_no");
                String tradeNo = map.get("trade_no");
                String totalAmount = map.get("total_amount");
                String tradeStatus = map.get("trade_status");
                logger.debug("AlipayServiceImpl alipayNotify param: outTradeNo:" + outTradeNo + " tradeNo:" + tradeNo + " totalAmount:" + totalAmount + " tradeStatus:" + tradeStatus);
                // 校验交易状态
                if (!"TRADE_SUCCESS".equals(tradeStatus) && !"TRADE_FINISHED".equals(tradeStatus)) {
                    responseMessage.setCode("301");
                } else {
                    responseMessage.setCode("200");
                }
            } else {
                responseMessage.setCode("401");
                logger.debug("AlipayServiceImpl alipayNotify signVerified:" + signVerified);
            }
        }catch (Exception e){
            logger.error("Order processing failed: {}", e.getMessage());
            responseMessage.setCode("307");
            responseMessage.setMsg("Order processing error");
        }
        return responseMessage;
    }
}
