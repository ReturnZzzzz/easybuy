package com.a.easybuy.service;

import com.a.easybuy.pojo.Info;
import com.a.easybuy.pojo.ResponseMessage;

public interface InfoService {
    ResponseMessage getInfoList(Integer pageNow,Integer pageSize);
    ResponseMessage addInfo(Info info);
    ResponseMessage delInfo(Integer id);
    ResponseMessage findInfo(Integer id);
}
