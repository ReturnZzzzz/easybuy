package com.a.easybuy.dao;

import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.GoodsQuery;

import java.util.List;
import java.util.Map;

public interface GoodMapper {
    public List<Good> getAll(GoodsQuery query);
    public int getTotal(Map<String, Object> params);
    public int add(Good goods);
    public int update(Good goods);
    public int delete(Integer id);
    public Good getById(Map<String,Object> params);
}
