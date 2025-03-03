package com.a.easybuy.controller;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.AlipayService;
import com.a.easybuy.service.BillService;
import com.alipay.api.domain.BillInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AlipayController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AlipayService alipayService;
    @Autowired
    private BillService billService;

    @RequestMapping("/charge")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage charge(String phoneNumber,String price) {
       logger.info("charge phoneNumber:{},price:{}", phoneNumber, price);
       ResponseMessage responseMessage = alipayService.createOrder(phoneNumber+"充值"+price+"元",price);
return
    }

    @RequestMapping("/notify")
    public void handleAlipayNotify(HttpServletRequest req, HttpServletResponse res ) throws IOException {
        logger.info("AlipayController handleAlipayNotify start...");
        PrintWriter pw =res.getWriter();
        try {
            Map<String, String[]> paramsMap = req.getParameterMap();
            Map params = new HashMap<>();
            if (paramsMap != null && !paramsMap.isEmpty()) {
                for (Map.Entry entry : paramsMap.entrySet()) {
                    String key = (String) entry.getKey();
                    String[] values = (String[]) entry.getValue();
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < values.length; i++) {
                        if (i == values.length - 1) {
                            sb.append(values[i]);
                        } else {
                            sb.append(values[i] + ",");
                        }
                    }
                    params.put(key, sb.toString());
                }
            }
            params.remove("sign_type");
            ResponseMessage responseMessage = alipayService.alipayNotify(params);
            logger.debug("AlipayController handleAlipayNotify params"+params);
            if ("200".equals(responseMessage.getCode())) {
                Map<String,Object> billMap = new HashMap<>();
                String phone=  params.get("subject").toString().substring(0,11);
                BillInfo billInfo = billInfoService.getBillInfo(userPhone);
                Object value = params.get("total_amount");
                BigDecimal bill=null;
                if (value instanceof BigDecimal) {
                    bill = (BigDecimal) value;
                } else {
                    bill= new BigDecimal(params.get("total_amount").toString());
                }
                billMap.put("bill",bill.add(billInfo.getBill()));
                billMap.put("userPhone",userPhone);
                boolean isAdd= billInfoService.payPhone(billMap);
                pw.write("success");
                logger.debug("billMap is " +billMap);
            }

        }catch (Exception e) {
            logger.error("AlipayController handleAlipayNotify exception"+e.getMessage());
        }finally {
            pw.close();
        }
    }

}
