package com.a.easybuy.controller;

import com.a.easybuy.pojo.CarDetail;
import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.OrderDetail;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("car")
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
    @RequestMapping("getCarPage")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getCarPage(int id,int pageNow,int pageSize){
        logger.info("CarController getCarPage start...");
        return carService.getPages(id,pageNow,pageSize);
    }
    @RequestMapping("getAll")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getAll(int id){
        logger.info("CarController getAll start...");
        return carService.getOrderDetail(id);
    }
    @RequestMapping("createOrder")
    @ResponseBody
    public ResponseMessage createOrder(List<OrderDetail> list){
        logger.info("CarController getGoodsInCar start... list:"+list);
        ResponseMessage responseMessage = carService.createOrder(list);
        return responseMessage;
    }
    @RequestMapping("change")
    @ResponseBody
    public ResponseMessage change(CarDetail carDetail){
        logger.info("CarController getGoodsInCar start... carDetail:"+carDetail);
        ResponseMessage responseMessage = carService.change(carDetail);
        return responseMessage;
    }
    @RequestMapping("del")
    @ResponseBody
    public ResponseMessage del(int id){
        logger.info("CarController getGoodsInCar start... id:"+id);
        ResponseMessage responseMessage = carService.del(id);
        return responseMessage;
    }
    @RequestMapping("addCarDetail")
    @ResponseBody
    public ResponseMessage addCarDetail(CarDetail carDetail){
        logger.info("CarController getGoodsInCar start... carDetail:"+carDetail);
        ResponseMessage responseMessage = carService.addCarDetail(carDetail);
        logger.debug("CarController getGoodsInCar...responseMessage"+responseMessage);
        return responseMessage;
    }

    @RequestMapping("/getDetailsByUid")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getDetailsByUid(String uid){
        logger.info("CarController getDetailsByUid start...uid:"+uid);
        return carService.getDetailsByUid(Integer.parseInt(uid));
    }

    @RequestMapping("changeCount")
    @ResponseBody
    public ResponseMessage change(String id,String count){
        logger.info("CarController getGoodsInCar start... id:"+id+"count"+count);
        CarDetail carDetail = new CarDetail();
        carDetail.setId(Integer.parseInt(id));
        carDetail.setCount(Integer.parseInt(count));
        return carService.change(carDetail);
    }
}
