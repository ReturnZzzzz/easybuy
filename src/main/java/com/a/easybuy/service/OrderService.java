package com.a.easybuy.service;

import com.a.easybuy.pojo.CarDetail;
import com.a.easybuy.pojo.Order;
import com.a.easybuy.pojo.ResponseMessage;

import java.util.List;
import java.util.Map;


public interface OrderService  {
    public ResponseMessage getByPage(Integer pageNow, Integer pageSize);
    public ResponseMessage getOne(Map<String,Object> map);
    public ResponseMessage create(List<CarDetail> carDetails,String loginName);
    public ResponseMessage pay(Integer id);
    public ResponseMessage finish(Integer id);
    public ResponseMessage close(Integer id);
}
