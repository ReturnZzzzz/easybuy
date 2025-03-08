package com.a.easybuy.service;

import com.a.easybuy.dao.GoodMapper;
import com.a.easybuy.dao.LikeMapper;
import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.Like;
import com.a.easybuy.pojo.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LikeServiceImpl implements  LikeService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private GoodMapper goodsMapper;
    @Override
    public ResponseMessage addGoodToLike(Integer uid, Integer gid) {
        logger.info("LikeServiceImpl addGoodToLike start...");
        ResponseMessage rm = new ResponseMessage();
        int addCount = likeMapper.addGoodToLike(uid,gid);
        if(addCount>0){
            rm.setCode("200");
            rm.setMsg("添加成功!");
        }else{
            rm.setCode("201");
            rm.setMsg("添加成功!");
        }
        return rm;
    }

    @Override
    public ResponseMessage getLikeList(Integer uid) {
        logger.info("LikeServiceImpl addGoodToLike start... uid:"+uid);
        ResponseMessage rm = new ResponseMessage();
        List<Like> likeList = likeMapper.getLikeList(uid);
        List<Good> GoodsListInLike = new ArrayList<>();
        Map<String,Object> param = new HashMap<>();
        for(Like like:likeList){
            param.put("id",like.getGid());
            GoodsListInLike.add(goodsMapper.getById(param));
        }
        rm.setData(GoodsListInLike);
        return rm;
    }

    @Override
    public ResponseMessage delGoodsInLike(Integer uid, Integer gid) {
        logger.info("LikeServiceImpl addGoodToLike start... uid:"+uid+"gid:"+gid);
        ResponseMessage rm = new ResponseMessage();
        int delCount = likeMapper.delGoodInLike(uid,gid);
        if(delCount>0){
            rm.setCode("200");
            rm.setMsg("删除成功!");
        }else{
            rm.setCode("201");
            rm.setMsg("删除失败!");
        }
        return rm;
    }
}
