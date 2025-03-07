package com.a.easybuy.dao;

import com.a.easybuy.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    public List<Order> getAll();
    public int getCount();
    public Order getOne(int id);
    public int create(Order order);
    public int update(Map<String, Object> map);
}
