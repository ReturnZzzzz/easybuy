package com.a.easybuy.service;

import com.a.easybuy.pojo.ResponseMessage;

public interface InfoService {
    ResponseMessage getInfoList(Integer pageNow,Integer pageSize);
    ResponseMessage addInfo(String title,String content);
    ResponseMessage delInfo(Integer id);
    ResponseMessage findInfo(Integer id);
    ResponseMessage changeInfo(Integer id,String title,String content);
    ResponseMessage addInfoToES();
    ResponseMessage userByESPage(Integer pageNow,Integer pageSize,String title);
}
