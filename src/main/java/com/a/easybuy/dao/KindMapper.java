package com.a.easybuy.dao;

import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.Kind;

import java.util.List;

public interface KindMapper {
    List<Kind> getKindList();
    int addKind(Kind kind);
    int delKind(int id);
    List<Kind> getKindListByPid(Integer id);

    List<Good> getGoodListBykid(Integer id);
}
