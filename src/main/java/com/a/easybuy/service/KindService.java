package com.a.easybuy.service;

import com.a.easybuy.pojo.Kind;
import com.a.easybuy.pojo.ResponseMessage;

import java.util.List;

public interface KindService {
    ResponseMessage getKindList(Integer pageNow,Integer pageSize);
    ResponseMessage getAll();
    ResponseMessage addKind(String name,Integer pid);
    ResponseMessage delKind(Integer id);
    Boolean isDel(Integer pid);
    Boolean goodListByKid(Integer id);
    List<Kind> getCategoryTree();
    ResponseMessage getAllFirst();
    ResponseMessage getNext(Integer pid);
    ResponseMessage getPrevious(Integer id);
    ResponseMessage getKindListOfName();
    ResponseMessage checkKindName(String name);
    ResponseMessage getChildKind(Integer id);
    ResponseMessage getTwoThirdChild(Integer id);
}