package com.a.easybuy.service;

import com.a.easybuy.dao.CarMapper;
import com.a.easybuy.pojo.*;
import com.a.easybuy.service.CarService;
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
}
