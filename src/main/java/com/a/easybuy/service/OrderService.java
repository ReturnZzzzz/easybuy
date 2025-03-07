package com.a.easybuy.service;

import com.a.easybuy.pojo.Order;
import com.a.easybuy.pojo.ResponseMessage;


public interface OrderService  {
    public ResponseMessage getByPage(Integer pageNow, Integer pageSize);
    public ResponseMessage getById(Integer id);
    public ResponseMessage create(Order order);
    public ResponseMessage pay(Integer id);
    public ResponseMessage finish(Integer id);
    public ResponseMessage close(Integer id);
}
