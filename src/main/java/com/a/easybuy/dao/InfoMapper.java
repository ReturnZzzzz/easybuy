package com.a.easybuy.dao;

import com.a.easybuy.pojo.Info;

import java.util.List;

public interface InfoMapper {
    List<Info> getInfoList();
    int addInfo(Info info);
    int delInfo(Integer id);
    Info findInfo(Integer id);
    int changeInfo(Info info);
}
