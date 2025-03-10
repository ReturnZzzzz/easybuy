package com.a.easybuy.service;

import com.a.easybuy.dao.CarMapper;
import com.a.easybuy.pojo.*;
import com.a.easybuy.service.CarService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public ResponseMessage getGoodsInCar(String userName) {
        logger.info("CarServiceImpl getCarList start...");
        ResponseMessage rm = new ResponseMessage();
        User user = (User)redisTemplate.opsForValue().get(userName);
        List<Good> goodList = carMapper.getGoodsInCar(user.getId());
        rm.setData(goodList);
        logger.debug("CarServiceImpl getCarList rm values:"+rm);
        return rm;
    }


    @Override
    public ResponseMessage getPages(int id, int pageNow, int pageSize) {
        logger.info("CarServiceImpl getPages start... id:"+id+",pageNow:"+pageNow+",pageSize:"+pageSize);
        ResponseMessage rm = new ResponseMessage();
        PageHelper.startPage(pageNow, pageSize);
        PageInfo<CarDetail> pageInfo= new PageInfo<>();
        List<CarDetail> list= carMapper.getCarDetail(id);
        logger.debug("CarServiceImpl getPages list:"+list);
        pageInfo.setList(list);
        pageInfo.setPageNow(pageNow);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(carMapper.getCount(id));
        logger.debug("CarServiceImpl getPages list size:"+list.size());
        PageHelper.clearPage();
        if(!list.isEmpty()){
            rm.setCode("200");
            rm.setData(pageInfo);
        }else {
            rm.setCode("201");
            rm.setMsg("未查询到数据");
        }
        return rm;
    }


    @Override
    public ResponseMessage createOrder(List<OrderDetail> list) {
        return null;
    }

    @Override
    public ResponseMessage getOrderDetail(int id) {
        logger.info("CarServiceImpl getOrderDetail start... id:"+id);
        ResponseMessage rm = new ResponseMessage();
        PageInfo<CarDetail> pageInfo= new PageInfo<>();
        List<CarDetail> list= carMapper.getCarDetail(id);
        logger.debug("CarServiceImpl getCarDetail list:"+list);
        logger.debug("CarServiceImpl getPages list size:"+list.size());
        PageHelper.clearPage();
        if(!list.isEmpty()){
            rm.setCode("200");
            rm.setData(list);
        }else {
            rm.setCode("201");
            rm.setMsg("未查询到数据");
        }
        return rm;
    }

    @Override
    public ResponseMessage change(CarDetail carDetail) {
        logger.info("CarServiceImpl change start... carDetail:"+carDetail);
        ResponseMessage rm = new ResponseMessage();
        int count = carMapper.change(carDetail);
        if(count>0){
            rm.setCode("200");
        }else {
            rm.setCode("201");
        }
        return rm;
    }

    @Override
    public ResponseMessage del(int id) {
        logger.info("CarServiceImpl del start... id:"+id);
        ResponseMessage rm = new ResponseMessage();
        int count=carMapper.del(id);
        logger.debug("CarServiceImpl del count :"+count+"count"+count);
        if(count>0){
            rm.setCode("200");
        }else {
            rm.setCode("201");
        }
        return rm;
    }
}
