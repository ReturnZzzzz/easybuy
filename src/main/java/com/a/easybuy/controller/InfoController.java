package com.a.easybuy.controller;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class InfoController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private InfoService infoService;
    @RequestMapping("getInfoList")
    @ResponseBody
    public ResponseMessage getInfoList(String pageNow,String pageSize){
    logger.info("InfoController getInfoList start...");
    return infoService.getInfoList(Integer.parseInt(pageNow),Integer.parseInt(pageSize));
    }
}
