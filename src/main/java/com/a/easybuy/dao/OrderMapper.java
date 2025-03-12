package com.a.easybuy.dao;

import com.a.easybuy.pojo.Order;
import com.a.easybuy.pojo.OrderDetail;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    public List<Order> getAll();
    public List<Order> getUserAll(Map<String,Object> map);
    public int getCount();
    public Order getOne(Map<String, Object> map);
    public int create(Order order);
    public int reload(Order order);
    public int addDetail(OrderDetail orderDetail);
    public int update(Map<String, Object> map);
    public int decrease(Map<String, Object> map);
    public int rollback(Map<String, Object> map);
    public List<OrderDetail> getOrderDetail(int id);
}
