package com.a.easybuy.dao;

import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.User;

import java.util.List;

public interface CarMapper {
    List<Good> getGoodsInCar(Integer uid);
    User getUser(String userName);
}
