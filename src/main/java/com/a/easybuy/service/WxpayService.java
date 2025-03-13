package com.a.easybuy.service;

import com.a.easybuy.pojo.ResponseMessage;

import java.util.Map;

public interface WxpayService {
    Map<String, String> createPayQrCode(String orderId) throws Exception;
    ResponseMessage handleWxPayNotification(Map<String, String> params) throws Exception;
    ResponseMessage closeWxpayOrder(String orderNo) throws Exception;
    ResponseMessage checkPaymentStatus(String orderNo) throws Exception;
}
