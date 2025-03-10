package com.a.easybuy.dao;

import com.a.easybuy.pojo.CarDetail;
import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.User;

import java.util.List;

public interface CarMapper {
    List<Good> getGoodsInCar(Integer uid);
    User getUser(String userName);
    public List<CarDetail> getCarDetail(Integer uid);
    public int getCount(Integer uid);
    public int change(CarDetail carDetail);
    public int del(int id);
}
