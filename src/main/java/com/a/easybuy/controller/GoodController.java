package com.a.easybuy.controller;

import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.GoodsQuery;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.GoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("good")
public class GoodController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodService goodService;

    @RequestMapping("getPage")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getPage(Integer pageNow,Integer pageSize, @RequestParam(required = false) Integer kid) {
        logger.info("goodService.getPage pageNow:"+pageNow+",pageSize:"+pageSize+",kid"+kid);
        GoodsQuery goodQuery = new GoodsQuery();
        if (kid!=null){
            goodQuery.setKid(kid);
        }
        ResponseMessage responseMessage =goodService.getGoodsByPage(goodQuery,pageNow,pageSize);
        logger.debug("goodService.getPage responseMessage:"+responseMessage+"goodQuery:"+goodQuery);
        return responseMessage;
    }
    @RequestMapping("update")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage updateGood(Good good) {
        logger.info("goodService.updateGood"+good);
        ResponseMessage responseMessage =goodService.updateGood(good);
        logger.debug("goodService.updateGood"+responseMessage);
        return responseMessage;
    }

    @RequestMapping("add")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage addGood(Good good) {
        logger.info("goodService.addGood"+good);
        ResponseMessage responseMessage =goodService.addGood(good);
        logger.debug("goodService.addGood"+responseMessage);
        return responseMessage;
    }

    @RequestMapping("del")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage delGood(Integer id) {
        logger.info("goodService.delGood"+id);
        ResponseMessage responseMessage = goodService.deleteGood(id);
        logger.debug("goodService.delGood"+responseMessage);
        return responseMessage;
    }
}
