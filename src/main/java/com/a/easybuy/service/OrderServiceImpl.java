package com.a.easybuy.service;

import com.a.easybuy.dao.OrderMapper;
import com.a.easybuy.dao.UserMapper;
import com.a.easybuy.pojo.*;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
//@Transactional
public class OrderServiceImpl implements  OrderService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseMessage getByPage(Integer pageNow, Integer pageSize) {
        logger.info("getByPage pageNow:{},pageSize:{}", pageNow, pageSize);
        ResponseMessage responseMessage = new ResponseMessage();
        PageHelper.startPage(pageNow, pageSize);
        List<Order> list = orderMapper.getAll();
        logger.debug("orderMapper.getByPage list:{}", list);
        PageHelper.clearPage();
        PageInfo<Order> pageInfo = new PageInfo<>();
        pageInfo.setList(list);
        pageInfo.setPageNow(pageNow);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(orderMapper.getCount());

        if (list != null && list.size() > 0) {
            responseMessage.setCode("200");
            responseMessage.setData(pageInfo);
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getByPage(Integer uid, Integer pageNow, Integer pageSize) {
        logger.info("getByPage pageNow:{},pageSize:{}", pageNow, pageSize);
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        PageHelper.startPage(pageNow, pageSize);
        List<Order> list = orderMapper.getUserAll(map);
        logger.debug("orderMapper.getByPage list:{}", list);
        PageHelper.clearPage();
        PageInfo<Order> pageInfo = new PageInfo<>();
        pageInfo.setList(list);
        pageInfo.setPageNow(pageNow);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(orderMapper.getCount());

        if (list != null && list.size() > 0) {
            responseMessage.setCode("200");
            responseMessage.setData(pageInfo);
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getOne(Map<String,Object> map) {
        logger.info("getById map:{}", map);
        ResponseMessage responseMessage = new ResponseMessage();
        Order order = orderMapper.getOne(map);
        logger.debug("orderMapper.getById order:{}", order);
        if (order != null) {
            responseMessage.setCode("200");
            responseMessage.setData(order);
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage create(List<CarDetail> carDetails,String loginName,Integer uid) {
        logger.info("create carDetails:{}", carDetails);
        String code = UUID.randomUUID().toString();
        String orderCode = code.replaceAll("-","");
        Order orderTemp = new Order();
        orderTemp.setOrderCode(orderCode);
        orderMapper.create(orderTemp);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("orderCode",orderCode);
        Order order =orderMapper.getOne(map);
        order.setLoginName(loginName);
        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        BigDecimal total=new BigDecimal(0);
        for (CarDetail carDetail:carDetails){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setGood(carDetail.getGood());
            orderDetail.setGid(orderDetail.getGood().getId());
            orderDetail.setCount(carDetail.getCount());
            Map<String,Object> params = new HashMap<>();
            params.put("gid",orderDetail.getGid());
            orderMapper.delInCar(params);
            params.put("count",orderDetail.getCount());
            orderMapper.decrease(params);
            orderDetail.setOid(order.getId());
            orderDetail.setTotal(orderDetail.getGood().getPrice().multiply(new BigDecimal( orderDetail.getCount())));
            orderDetails.add(orderDetail);
            total=total.add(orderDetail.getTotal());
            orderMapper.addDetail(orderDetail);
        }
        order.setList(orderDetails);
        order.setTotal(total);
        order.setAdress("111");
        order.setUid(uid);
        order.setCreateDate(new Date());
        ResponseMessage responseMessage = new ResponseMessage();
        int count = orderMapper.reload(order);
        if (count > 0) {
            responseMessage.setCode("200");
            responseMessage.setData(order);
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage pay(Integer id) {
        logger.info("pay order:{}", id);
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("status",2);
        int count = orderMapper.update(map);
        if (count > 0) {
            responseMessage.setCode("200");
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage finish(Integer id) {
        logger.info("finish order:{}", id);
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("status",3);
        int count = orderMapper.update(map);
        if (count > 0) {
            responseMessage.setCode("200");
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage close(Integer id) {
        logger.info("close order:{}", id);
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        Order order = orderMapper.getOne(map);
        List<OrderDetail> orderDetails = orderMapper.getOrderDetail((int) order.getId());
        for (OrderDetail orderDetail:orderDetails){
            Map<String,Object> params = new HashMap<>();
            params.put("gid",orderDetail.getGood().getId());
            params.put("count",orderDetail.getCount());
            int count = orderMapper.rollback(params);
            logger.debug("orderMapper.rollback count:{}", count);
        }
        map.put("status",0);
        int count = orderMapper.update(map);
        if (count > 0) {
            responseMessage.setCode("200");
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }
}
