package com.a.easybuy.service;

import com.a.easybuy.pojo.ResponseMessage;
import com.alipay.api.AlipayApiException;

import java.util.Map;

public interface AlipayService {
    public ResponseMessage alipayNotify(Map<String, String> map) throws AlipayApiException;
    public ResponseMessage createOrder(String subject,String Amount);
}
