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
    public ResponseMessage createOrder(String subject,String price) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            // 初始化SDK
            AlipayClient alipayClient = new DefaultAlipayClient(alipayUtil.getAlipayConfig());
            // 构造请求参数以调用接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            // 设置商户订单号
            model.setOutTradeNo(UUID.randomUUID().toString());
            // 设置订单总金额
            model.setTotalAmount(price);
            // 设置订单标题
            model.setSubject(subject);
            // 设置产品码（固定）
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            request.setReturnUrl(alipay.getReturnUrl());
            request.setNotifyUrl(alipay.getNotifyUrl());
            request.setBizModel(model);
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            String pageRedirectionData = response.getBody();//获取支付宝响应的html支付页面
            System.out.println(pageRedirectionData);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                responseMessage.setCode("200");
                responseMessage.setData(pageRedirectionData);
            } else {
                System.out.println("调用失败");
                responseMessage.setCode("201");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage pay(String total, String orderCode) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            // 初始化SDK
            AlipayClient alipayClient = new DefaultAlipayClient(alipayUtil.getAlipayConfig());
            // 构造请求参数以调用接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            // 设置商户订单号
            model.setOutTradeNo(orderCode);
            // 设置订单总金额
            model.setTotalAmount(total);
            // 设置订单标题
            model.setSubject("易买网消费"+total+"元");
            // 设置产品码（固定）
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            request.setReturnUrl(alipay.getReturnUrl());
            request.setNotifyUrl(alipay.getNotifyUrl());
            request.setBizModel(model);
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
            String pageRedirectionData = response.getBody();//获取支付宝响应的html支付页面
            System.out.println(pageRedirectionData);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                responseMessage.setCode("200");
                responseMessage.setData(pageRedirectionData);
            } else {
                System.out.println("调用失败");
                responseMessage.setCode("201");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseMessage;
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
