package com.a.easybuy.dao;

import com.a.easybuy.pojo.CarDetail;
import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CarMapper {
    List<Good> getGoodsInCar(Integer uid);
    User getUser(String userName);
    public List<CarDetail> getCarDetail(Integer uid);
    public int getCount(Integer uid);
    public int change(CarDetail carDetail);
    public int del(int id);
    public int addCarDetail(Map<String, Object> map);
    Integer getCidByUid(Integer uid);
    int checkGoodsIsAlive(@Param("gid") Integer gid, @Param("cid") Integer cid);
}
