package com.a.easybuy.dao;

import com.a.easybuy.pojo.Info;

import java.util.List;
import java.util.Map;

public interface InfoMapper {
    List<Info> getInfoList(Map<String,Object> params);
    int addInfo(Info info);
    int delInfo(Integer id);
    Info findInfo(Integer id);
}
