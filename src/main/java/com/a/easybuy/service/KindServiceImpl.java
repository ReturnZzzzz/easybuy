package com.a.easybuy.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.a.easybuy.dao.KindMapper;
import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.Kind;
import com.a.easybuy.pojo.PageInfo;
import com.a.easybuy.pojo.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KindServiceImpl implements KindService {
private Logger logger = LoggerFactory.getLogger(getClass());
@Autowired
private KindMapper kindMapper;
    @Override
    public ResponseMessage getKindList(Integer pageNow,Integer pageSize) {
        logger.info("KindServiceImpl  getKindList start..");
        ResponseMessage rm = new ResponseMessage();
        PageInfo pageInfo = new PageInfo();
        List<Kind> kindList = new ArrayList<>();
        Page page = PageHelper.startPage(pageNow,pageSize);
        kindList = kindMapper.getKindList();
        pageInfo.setList(kindList);
        pageInfo.setPageNow(pageNow);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal((int) page.getTotal());
        pageInfo.setPages(page.getPages());
        rm.setData(pageInfo);
        return rm;
    }

    @Override
    public ResponseMessage addKind(Kind kind) {
        return null;
    }

    @Override
    public ResponseMessage delKind(Integer id) {
        logger.info("KindServiceImpl  delKind start.. id:"+id);
        ResponseMessage rm = new ResponseMessage();
        Boolean isFatKind = isDel(id);
        logger.debug("isDel isFatKind :"+isFatKind);
        Boolean isGood = goodListByKid(id);
        logger.debug("goodListByKid isGood :"+isGood);
        int delCount = 0;
        if(isFatKind && isGood){
            delCount = kindMapper.delKind(id);
        }
        if(delCount>0){
            rm.setCode("200");
            rm.setMsg("删除成功!");
        }else{
            rm.setCode("201");
            rm.setMsg("无法删除，存在子种类或该种类还有商品!");
        }
        return rm;
    }

    @Override
    public Boolean isDel(Integer pid) {
        logger.info("KindServiceImpl  delKind start.. fid:"+pid);
        List<Kind> kindList = kindMapper.getKindListByPid(pid);
        Boolean isDel =false;
        if(kindList.size()==0){
            isDel =true;
        }else{
            isDel =false;
        }
        return isDel;
    }

    @Override
    public Boolean goodListByKid(Integer id) {
        logger.info("KindServiceImpl  delKind start.. id:"+id);
        List<Good> goodList = kindMapper.getGoodListBykid(id);
        Boolean isGood = false;
        if(goodList.size()==0){
            isGood = true;
        }else{
            isGood =false;
        }
        return isGood;
    }

    @Override
    public List<Kind> makeKindLevel() {
        return null;
    }

    @Override
    public List<Kind> makePidName() {
        return null;
    }
}
