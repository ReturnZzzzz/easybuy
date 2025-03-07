package com.a.easybuy.service;

import com.a.easybuy.dao.OrderMapper;
import com.a.easybuy.dao.UserMapper;
import com.a.easybuy.pojo.Order;
import com.a.easybuy.pojo.PageInfo;
import com.a.easybuy.pojo.ResponseMessage;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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
    public ResponseMessage getById(Integer id) {
        logger.info("getById id:{}", id);
        ResponseMessage responseMessage = new ResponseMessage();
        Order order = orderMapper.getOne(id);
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
    public ResponseMessage create(Order order) {
        logger.info("create order:{}", order);
        ResponseMessage responseMessage = new ResponseMessage();
        int count = orderMapper.create(order);
        if (count > 0) {
            responseMessage.setCode("200");
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
