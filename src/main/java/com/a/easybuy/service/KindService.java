package com.a.easybuy.service;

import com.a.easybuy.pojo.Kind;
import com.a.easybuy.pojo.ResponseMessage;

import java.util.List;

public interface KindService {
    ResponseMessage getKindList(Integer pageNow,Integer pageSize);
    ResponseMessage addKind(Kind kind);
    ResponseMessage delKind(Integer id);
    Boolean isDel(Integer pid);
    Boolean goodListByKid(Integer id);
    List<Kind> makeKindLevel();
    List<Kind> makePidName();
}
