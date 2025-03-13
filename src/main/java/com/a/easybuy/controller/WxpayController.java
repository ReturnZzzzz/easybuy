package com.a.easybuy.controller;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.WxpayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("wxpay")
@CrossOrigin
public class WxpayController {
    private Logger logger = LoggerFactory.getLogger(WxpayController.class);
    @Autowired
    private WxpayService wxpayService;

    @RequestMapping("createOrder")
    public ResponseEntity<?> createOrder(String orderNo){
        logger.info("WxpayController createOrder orderNo:"+orderNo);
        try {
            Map<String,String> result = wxpayService.createPayQrCode(orderNo);
//            return ResponseEntity.ok(new HashMap<String,Object>(){{
//                put("code",200);
//                put("data",new HashMap<String,Object>(){{
//                    put("qrCodeUrl",result.get("codeUrl"));
//                    put("orderNo",result.get("orderNo"));
//                }});
//            }});
            // 使用明确的 Map 结构（如 LinkedHashMap）
            Map<String, Object> response = new LinkedHashMap<>();
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("qrCodeUrl", result.get("codeUrl"));
            data.put("orderNo", result.get("orderNo"));
            response.put("code", 200);
            response.put("data", data);
            return ResponseEntity.ok(response);
        }catch (IllegalAccessException e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
        catch (Exception e) {
            e.printStackTrace(); // 强制打印到控制台
            return ResponseEntity.badRequest().body( Collections.singletonMap("error", "支付系统异常: " + e.getMessage()));
        }
    }

    @PostMapping("notify")
    public ResponseEntity<?> handleWxPayNotification(
            @RequestHeader("Wechatpay-Serial") String serial,
            @RequestHeader("Wechatpay-Nonce") String nonce,
            @RequestHeader("Wechatpay-Timestamp") String timestamp,
            @RequestHeader("Wechatpay-Signature") String signature,
            @RequestBody String body) {

        // 构建参数映射（需包含所有微信回调参数）
        Map<String, String> params = new HashMap<>();
        params.put("Wechatpay-Serial", serial);
        params.put("Wechatpay-Nonce", nonce);
        params.put("Wechatpay-Timestamp", timestamp);
        params.put("Wechatpay-Signature", signature);
        params.put("body", body);

        try {
            // 调用Service处理逻辑
            ResponseMessage response = wxpayService.handleWxPayNotification(params);

            // 根据业务码返回对应HTTP状态
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);

        } catch (Exception e) {
            logger.error("回调处理异常: ", e);
            // 返回标准错误响应（需符合微信重试策略）
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("系统异常");
        }
    }

    @RequestMapping("checkPaymentStatus")
        public ResponseMessage checkPaymentStatus(String orderNo){
        ResponseMessage msg = null;
        try {
            msg = wxpayService.checkPaymentStatus(orderNo);
        } catch (Exception e) {
            logger.error("检查微信支付状态有误:"+e.getMessage());
        }
        return msg;
    }
}