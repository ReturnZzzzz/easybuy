package com.a.easybuy.service;

import com.a.easybuy.pojo.CarDetail;
import com.a.easybuy.pojo.OrderDetail;
import com.a.easybuy.pojo.ResponseMessage;

import java.util.List;

public interface CarService {
    ResponseMessage getGoodsInCar(String userName);
    ResponseMessage getPages(int id, int pageNow, int pageSize);
    ResponseMessage createOrder(List<OrderDetail> list);
    ResponseMessage getOrderDetail(int uid);
    ResponseMessage change(CarDetail carDetail);
    ResponseMessage del(int id);
    ResponseMessage addCarDetail(CarDetail carDetail);
    ResponseMessage getDetailsByUid(Integer uid);
    ResponseMessage addCarDetail(CarDetail carDetail,Integer uid);
    Integer getCidByUid(Integer uid);
}
