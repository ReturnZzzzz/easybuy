package com.a.easybuy.controller;

import com.a.easybuy.dao.OrderMapper;
import com.a.easybuy.pojo.CarDetail;
import com.a.easybuy.pojo.Order;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.OrderService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping("getPage")
    @ResponseBody
    @CrossOrigin
    public ResponseMessage getPage(Integer pageNow, Integer pageSize){
        logger.info("orderController getPage start pageNow:"+pageNow+",pageSize:"+pageSize);
        ResponseMessage responseMessage = orderService.getByPage(pageNow,pageSize);
        logger.debug("orderController getPage end pageNow:"+pageNow+",pageSize:"+pageSize+",responseMessage:"+responseMessage);
        return responseMessage;
    }
    @RequestMapping("getUserPage")
    @ResponseBody
    @CrossOrigin
    public ResponseMessage getUserPage(Integer uid,Integer pageNow, Integer pageSize){
        logger.info("orderController getPage start pageNow:"+pageNow+",pageSize:"+pageSize);
        ResponseMessage responseMessage = orderService.getByPage(uid,pageNow,pageSize);
        logger.debug("orderController getPage end pageNow:"+pageNow+",pageSize:"+pageSize+",responseMessage:"+responseMessage);
        return responseMessage;
    }
    @RequestMapping("create")
    @ResponseBody
    @CrossOrigin
    public ResponseMessage create(String carDetails,String loginName){
        logger.info("orderController create start carDetails:"+carDetails);
        List<CarDetail> carDetailList = JSON.parseArray(carDetails, CarDetail.class);
        ResponseMessage responseMessage = orderService.create(carDetailList,loginName);
        logger.debug("orderService create carDetailList:"+carDetailList+"loginName:"+loginName+",responseMessage:"+responseMessage);
        return responseMessage;
    }
    @RequestMapping("close")
    @ResponseBody
    @CrossOrigin
    public ResponseMessage close(Integer id){
        logger.info("orderController close start id:"+id);
        ResponseMessage responseMessage = orderService.close(id);
        logger.debug("orderService close id:"+id);
        return responseMessage;
    }
}
