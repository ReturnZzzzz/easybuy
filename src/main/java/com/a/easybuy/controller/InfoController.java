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
@RequestMapping("info")
public class InfoController {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//    @Autowired
//    private InfoService infoService;
//    @RequestMapping("getInfoList")
//    @ResponseBody
//    public ResponseMessage getInfoList(String pageNow,String pageSize){
//    logger.info("InfoController getInfoList start...");
//    return infoService.getInfoList(Integer.parseInt(pageNow),Integer.parseInt(pageSize));
//    }
//
//    @RequestMapping("findInfo")
//    @ResponseBody
//    public ResponseMessage findInfo(String id){
//        logger.info("InfoController getInfoList start...");
//        return infoService.findInfo(Integer.parseInt(id));
//    }
//
//    @RequestMapping("changeInfo")
//    @ResponseBody
//    public ResponseMessage changeInfo(String id,String title,String content){
//        logger.info("InfoController changeInfo start...");
//        return infoService.changeInfo(Integer.parseInt(id),title,content);
//    }
//
//    @RequestMapping("delInfo")
//    @ResponseBody
//    public ResponseMessage delInfo(String id){
//        logger.info("InfoController delInfo start...");
//        return infoService.delInfo(Integer.parseInt(id));
//    }
//    @RequestMapping("addInfo")
//    @ResponseBody
//    public ResponseMessage addInfo(String title,String content){
//        logger.info("InfoController addInfo start...");
//        return infoService.addInfo(title,content);
//    }
//
//    @RequestMapping("getInfoByES")
//    @ResponseBody
//    public ResponseMessage getInfoByES(String pageNow,String pageSize,String title){
//        logger.info("InfoController getInfoByES start... pageNow"+pageNow+"pageSize:"+pageSize+"title"+title);
//        return infoService.userByESPage(Integer.parseInt(pageNow),Integer.parseInt(pageSize),title);
//    }
//
//    @RequestMapping("addInfoToES")
//    @ResponseBody
//    public ResponseMessage addInfoToES(){
//        logger.info("InfoController addInfoToES start...");
//        return infoService.addInfoToES();
//    }
}
