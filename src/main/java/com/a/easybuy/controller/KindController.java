package com.a.easybuy.controller;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.KindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class KindController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private KindService kindService;
    @RequestMapping("getKindList")
    @ResponseBody
    public ResponseMessage getKindList(String pageNow, String pageSize){
        logger.info("KindController getKindList start...pageNow:"+pageNow+"pageSize:"+pageSize);
        return kindService.getKindList(Integer.parseInt(pageNow),Integer.parseInt(pageSize));
    }
    @RequestMapping("getAll")
    @ResponseBody
//    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getAll(){
        logger.info("KindController getAll start.");
        return kindService.getAll();
    }
    @RequestMapping("getAllFirst")
    @ResponseBody
//    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getAllFirst(){
        logger.info("KindController getAllFirst start.");
        return kindService.getAllFirst();
    }
    @RequestMapping("getNext")
    @ResponseBody
//    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getNext(Integer pid){
        logger.info("KindController getAllFirst start.");
        return kindService.getNext(pid);
    }
    @RequestMapping("delKind")
    @ResponseBody
    @CrossOrigin
    public ResponseMessage delKind(String id){
        logger.info("KindController getKindList start...id:"+id);
        return kindService.delKind(Integer.parseInt(id));
    }
    @RequestMapping("getRelative")
    @ResponseBody
//    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getRelative(Integer id){
        logger.info("KindController getKindList start...id:"+id);
        return kindService.getPrevious(id);
    }
}
