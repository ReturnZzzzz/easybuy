package com.a.easybuy.dao;

import com.a.easybuy.pojo.Good;

import java.util.List;
import java.util.Map;

public interface GoodMapper {
    public List<Good> getAll();
    public int getTotal(Map<String, Object> params);
    public int add(Good goods);
    public int update(Good goods);
    public int delete(Good goods);
    public Good getById(Integer id);
}
