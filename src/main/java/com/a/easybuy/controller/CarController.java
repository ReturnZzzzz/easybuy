package com.a.easybuy.controller;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class CarController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CarService carService;
    @RequestMapping("getCarList")
    @ResponseBody
    public ResponseMessage getGoodsInCar(String userName){
        logger.info("CarController getGoodsInCar start...");
        return carService.getGoodsInCar(userName);
    }
}
