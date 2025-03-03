package com.a.easybuy.util;

import com.a.easybuy.pojo.Alipay;
import com.alipay.api.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlipayUtil {
    @Autowired
    private Alipay alipay;

    public AlipayConfig getAlipayConfig(){
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId(alipay.getAppId());
        alipayConfig.setPrivateKey(alipay.getPrivateKey());
        alipayConfig.setServerUrl(alipay.getServerURL());
        alipayConfig.setAlipayPublicKey(alipay.getAlipayPublicKey());
        alipayConfig.setFormat("json");
        alipayConfig.setCharset("utf-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }
}
