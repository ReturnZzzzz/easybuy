package com.a.easybuy.service;

import com.a.easybuy.dao.GoodMapper;
import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.GoodsQuery;
import com.a.easybuy.pojo.PageInfo;
import com.a.easybuy.pojo.ResponseMessage;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodServiceImpl implements  GoodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public ResponseMessage addGood(Good good) {
        logger.info("addGood good:"+good);
        good.setCreatedate(new Date());
        ResponseMessage responseMessage = new ResponseMessage();
        int count = goodMapper.add(good);
        logger.debug("goodMapper add good:"+good+", count:"+count);
        if(count>0){
            responseMessage.setCode("200");
            responseMessage.setMsg("添加成功");
        }else {
            responseMessage.setCode("201");
            responseMessage.setMsg("添加失败");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage updateGood(Good good) {
        logger.info("updateGood good:"+good);
        ResponseMessage responseMessage = new ResponseMessage();
        int count = goodMapper.update(good);
        logger.debug("goodMapper updateGood:"+good+", count:"+count);
        if(count>0){
            responseMessage.setCode("200");
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage deleteGood(int id) {
        logger.info("deleteGood good:"+id);
        ResponseMessage responseMessage = new ResponseMessage();
        int count = goodMapper.delete(id);
        logger.debug("goodMapper deleteGood:"+id+", count:"+count);
        if(count>0){
            responseMessage.setCode("200");
            responseMessage.setMsg("删除成功");
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getGood(int id) {
        logger.info("getGood good:"+id);
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String,Object> params= new HashMap<>();
        params.put("id",id);
        Good good = goodMapper.getById(params);
        if(good!=null){
            responseMessage.setCode("200");
            responseMessage.setData(good);
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getGoodsByPage(GoodsQuery query, int pageNow, int pageSize) {
        logger.info("getGoodsByPage query:"+query+",pageNow:"+pageNow+",pageSize:"+pageSize);
        ResponseMessage responseMessage = new ResponseMessage();
        PageHelper.startPage(pageNow, pageSize);
        List<Good> goodList = goodMapper.getAll(query);
        PageHelper.clearPage();
        if(goodList.size()>0){
            responseMessage.setCode("200");
            PageInfo<Good> pageInfo = new PageInfo<>();
            pageInfo.setList(goodList);
            pageInfo.setPageNow(pageNow);
            pageInfo.setPageSize(pageSize);
            Map<String,Object> params= new HashMap<>();
            params.put("kid",query.getKid());
            pageInfo.setTotal(goodMapper.getTotal(params));
            responseMessage.setData(pageInfo);
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getMin(String id1, String id2, String id3) {
        logger.info("getMin query:"+id1+",pageNow:"+id2+",pageSize:"+id3);
        ResponseMessage responseMessage = new ResponseMessage();
        List<String> ids = new ArrayList<>();
        int min = 10000;
        for (String id : ids) {
            int count = goodMapper.getStock(Integer.valueOf(id));
            if(count<min){
                min=count;
            }
        }
        if (min>0){
            responseMessage.setCode("200");
            responseMessage.setData(min);
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage  ;
    }
}
