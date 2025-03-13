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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public ResponseMessage addCarDetail(CarDetail carDetail) {
        logger.info("CarServiceImpl addCarDetail start... carDetail:"+carDetail);
        ResponseMessage rm = new ResponseMessage();
        Map<String,Object> map=new HashMap<>();
        map.put("cid",carDetail.getCid());
        map.put("gid",carDetail.getGood().getId());
        map.put("count",carDetail.getCount());
        int count = carMapper.addCarDetail(map);
        if(count>0){
            rm.setCode("200");
        }else {
            rm.setCode("201");
        }
        return rm;
    }

    @Override
    public ResponseMessage getDetailsByUid() {
        logger.info("CarServiceImpl getCarList start...");
        ResponseMessage rm = new ResponseMessage();
        Car car = (Car) carMapper.getCarDetail(18);
        if(car!=null){
            rm.setCode("200");
            rm.setData(car);
        }else{
            rm.setCode("201");
        }
        return rm;
    }

    @Override
    public ResponseMessage addCarDetail(CarDetail carDetail, Integer uid) {
        logger.info("CarServiceImpl addCarDetail start... carDetail:"+carDetail);
        ResponseMessage rm = new ResponseMessage();
        Map<String,Object> map=new HashMap<>();
        Integer cid=getCidByUid(uid);
        int isAlive=carMapper.checkGoodsIsAlive(carDetail.getGid(),cid);
        if(isAlive>0){
            rm.setCode("205");
            rm.setMsg("该商品以存在购物车!");
            return rm;
        }
        map.put("cid",cid);
        map.put("gid",carDetail.getGid());
        map.put("count",carDetail.getCount());
        int count = carMapper.addCarDetail(map);
        if(count>0){
            rm.setCode("200");
            rm.setMsg("添加成功!");
        }else {
            rm.setCode("201");
            rm.setMsg("添加失败!");
        }
        return rm;
    }

    @Override
    public Integer getCidByUid(Integer uid) {
        logger.info("CarServiceImpl getCidByUid start... uid:"+uid);
        Integer cid=carMapper.getCidByUid(uid);
        return cid;
    }
}
