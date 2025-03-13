package com.a.easybuy.util;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.*;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;

@Configuration
public class WxPayConfig {
    @Value("${wxpay.appid}")
    private String appId;
    @Value("${wxpay.mchid}")
    private String mchId;
    @Value("${wxpay.serialno}")
    private String merchantSerialNumber;
    @Value("${wxpay-privatekeypath}")
    private String privateKeyPath;
    @Value("${wxpay-apiv3key}")
    private String apiV3Key;
    @Value("${wxpay-notifyurl}")
    private String notifyUrl;
    @Value("${wxpay.autoupdateinterval:15}")
    private int minutesInterval;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMerchantSerialNumber() {
        return merchantSerialNumber;
    }

    public void setMerchantSerialNumber(String merchantSerialNumber) {
        this.merchantSerialNumber = merchantSerialNumber;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public int getMinutesInterval() {
        return minutesInterval;
    }

    public void setMinutesInterval(int minutesInterval) {
        this.minutesInterval = minutesInterval;
    }

    @Bean
    public CloseableHttpClient wxPayHttpClient() throws Exception {
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(Files.newInputStream(Paths.get(privateKeyPath)));
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId,new PrivateKeySigner(merchantSerialNumber,merchantPrivateKey)),
                apiV3Key.getBytes(StandardCharsets.UTF_8)
        );
        return WechatPayHttpClientBuilder.create().withMerchant(mchId,merchantSerialNumber,merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier)).build();
    }

    public AutoUpdateCertificatesVerifier getVerifier() throws Exception {
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(Files.newInputStream(Paths.get(privateKeyPath)));
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId,new PrivateKeySigner(merchantSerialNumber,merchantPrivateKey)),
                apiV3Key.getBytes(StandardCharsets.UTF_8)
        );
        return verifier;
    }
}
