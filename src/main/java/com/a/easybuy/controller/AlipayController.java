package com.a.easybuy.controller;

import com.a.easybuy.dao.OrderMapper;
import com.a.easybuy.pojo.Bill;
import com.a.easybuy.pojo.Order;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.AlipayService;
import com.a.easybuy.service.BillService;
import com.a.easybuy.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AlipayController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AlipayService alipayService;
    @Autowired
    private BillService billService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping("/createOrder")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public void createOrder(HttpServletResponse response,String price,String Subject) {
        logger.info("AlipayController createOrder start...");
        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            ResponseMessage msg = alipayService.createOrder(Subject,price);
            logger.debug("AlipayController createOrder result:" + msg);
            if ("200".equals(msg.getCode())) {
                pw.write((String) (msg.getData()));
            }
            pw.close();
        } catch (IOException e) {
            logger.error("AlipayController createOrder is error...");
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/charge")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public void charge(HttpServletResponse response,String price,String phoneNumber) {
        logger.info("AlipayController createOrder start...");
        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            ResponseMessage msg = alipayService.createOrder(phoneNumber+"充值"+price+"元",price);
            logger.debug("AlipayController createOrder result:" + msg);
            if ("200".equals(msg.getCode())) {
                pw.write((String) (msg.getData()));
            }
            pw.close();
        } catch (IOException e) {
            logger.error("AlipayController createOrder is error...");
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/pay")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public void pay(HttpServletResponse response,String orderCode) {
        logger.info("AlipayController pay start...");
        Map<String,Object> map = new HashMap<>();
        map.put("orderCode",orderCode);
        Order order = orderMapper.getOne(map);
        BigDecimal total = order.getTotal();
        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            ResponseMessage msg = alipayService.pay(total.toString(),orderCode);
            logger.debug("AlipayController createOrder result:" + msg);
            if ("200".equals(msg.getCode())) {
                pw.write((String) (msg.getData()));
            }
            pw.close();
        } catch (IOException e) {
            logger.error("AlipayController createOrder is error...");
            throw new RuntimeException(e);
        }
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
                Object value = params.get("total_amount");
                BigDecimal total=null;
                if (value instanceof BigDecimal) {
                    total = (BigDecimal) value;
                } else {
                    total= new BigDecimal(params.get("total_amount").toString());
                }
                Bill bill = new Bill();
                bill.setPhone(phone);
                bill.setPrice(total);
                bill.setCreatetime(new Date());
                ResponseMessage msg = billService.addBill(bill);
                logger.debug("addBill bill:"+bill+",msg " +msg);
                pw.write("success");
            }
        }catch (Exception e) {
            logger.error("AlipayController handleAlipayNotify exception"+e.getMessage());
        }finally {
            pw.close();
        }
    }
    @RequestMapping("/notify1")
    public void handleAlipayNotify1(HttpServletRequest req, HttpServletResponse res ) throws IOException {
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
                String orderCode=  params.get("out_trade_no").toString();
                Object value = params.get("total_amount");
                BigDecimal total=null;
                if (value instanceof BigDecimal) {
                    total = (BigDecimal) value;
                } else {
                    total= new BigDecimal(params.get("total_amount").toString());
                }
                Map<String,Object> map = new HashMap<>();
                map.put("orderCode",orderCode);
                Order order = (Order) orderService.getOne(map).getData();
                int id =(int)order.getId();
                ResponseMessage msg = orderService.pay(id);
                logger.debug("addBill bill:"+id+",msg " +msg);
                pw.write("success");
            }
        }catch (Exception e) {
            logger.error("AlipayController handleAlipayNotify exception"+e.getMessage());
        }finally {
            pw.close();
        }
    }
}
