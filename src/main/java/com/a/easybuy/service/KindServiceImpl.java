package com.a.easybuy.service;

import com.alipay.api.domain.Goods;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseMessage getAll() {
        logger.info("KindServiceImpl  getAll start..");
        ResponseMessage rm = new ResponseMessage();
        List<Kind> kindList = kindMapper.getKindList();
        if (kindList != null && kindList.size() > 0) {
            rm.setData(kindList);
            rm.setCode("200");
        }else {
            rm.setCode("201");
        }
        return rm;
    }

    @Override
    public ResponseMessage addKind(String name, Integer pid) {
        logger.info("KindServiceImpl addKind name:"+name+"pid"+pid);
        ResponseMessage rm = new ResponseMessage();
        Kind kind = new Kind();
        kind.setName(name);
        kind.setPid(pid);
        Kind newKind=kindMapper.getKindByPid(pid);
        kind.setLevel(newKind.getLevel());
        int i =kindMapper.addKind(kind);
        if(i>0){
            rm.setCode("200");
            rm.setMsg("添加成功!");
        }else{
            rm.setCode("201");
            rm.setMsg("添加失败!");
        }
        return rm;
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
    public List<Kind> getCategoryTree() {
        // 1. 查询所有分类
        List<Kind> allKinds = kindMapper.getKindList();

        // 2. 构建分类树
        List<Kind> level1 = allKinds.stream()
                .filter(k -> k.getPid() == 0)
                .peek(l1 -> {
                    // 找二级分类
                    List<Kind> level2 = allKinds.stream()
                            .filter(l2 -> l2.getPid()==(l1.getId()))
                            .peek(l2 -> {
                                // 找三级分类并关联商品
                                List<Kind> level3 = allKinds.stream()
                                        .filter(l3 -> l3.getPid()==(l2.getId()))
                                        .peek(l3 -> l3.setProducts(
                                                kindMapper.getGoodListBykid(l3.getId()) // 查商品
                                        ))
                                        .collect(Collectors.toList());
                                l2.setChildren(level3);
                            })
                            .collect(Collectors.toList());
                    l1.setChildren(level2);
                })
                .collect(Collectors.toList());

        return level1;
    }

    @Override
    public ResponseMessage getAllFirst() {
        logger.info("KindServiceImpl  getAllFirst start..");
        List<Kind> list = kindMapper.getKindListByPid(0);
        ResponseMessage rm = new ResponseMessage();
        rm.setCode("200");
        rm.setData(list);
        return rm;
    }

    @Override
    public ResponseMessage getNext(Integer pid) {
        logger.info("KindServiceImpl  getNext start.. id:"+pid);
        ResponseMessage rm = new ResponseMessage();
        List<Kind> kindList = kindMapper.getKindListByPid(pid);
        rm.setCode("200");
        rm.setData(kindList);
        return rm;
    }

    @Override
    public ResponseMessage getPrevious(Integer id) {
        logger.info("KindServiceImpl  getPrevious start.. id:"+id);
        ResponseMessage rm = new ResponseMessage();
        ArrayList<Kind> list = new ArrayList<>();
        boolean isOK=true;
        while (isOK){
            Kind kind = kindMapper.getKindById(id);
            list.add(kind);
            if (kind.getPid()==0){
                isOK=false;
            }
            id=(int)kind.getPid();
        }
        logger.debug("getKindById list:"+list);
        if (list.isEmpty()){
            rm.setCode("201");
            rm.setMsg("分类加载错误");
        }else {
            rm.setCode("200");
            rm.setData(list);
        }
        return rm;
    }

    @Override
    public ResponseMessage getKindListOfName() {
        logger.info("KindServiceImpl  getKindListOfName start...");
        ResponseMessage rm = new ResponseMessage();
        List<Kind> kindList = kindMapper.getKindList();
        rm.setData(kindList);
        logger.debug("getKindListOfName value kindList:"+kindList);
        return rm;
    }

    @Override
    public ResponseMessage checkKindName(String name) {
        logger.info("KindServiceImpl  checkKindName start... name:"+name);
        ResponseMessage rm = new ResponseMessage();
        Kind kind = kindMapper.getKindByName(name);
        if(kind == null){
            rm.setCode("200");
        }else {
            rm.setCode("201");
        }
        logger.debug("checkKindName value rm:"+rm);
        return rm;
    }

    @Override
    public ResponseMessage getChildKind(Integer id) {
        logger.info("KindServiceImpl  checkKindName start... id:"+id);
        ResponseMessage rm = new ResponseMessage();
        List<Kind> kindList = kindMapper.getChildKind(id);
        rm.setData(kindList);
        return rm;
    }

    @Override
    public ResponseMessage getTwoThirdChild(Integer id) {
        logger.info("KindServiceImpl  getTwoThirdChild start... id:"+id);
        ResponseMessage rm = new ResponseMessage();
        List<Kind> twoList = kindMapper.getChildKind(id);
        for(Kind kind:twoList){
            kind.setChildren(kindMapper.getChildKind(kind.getId()));
        }
        rm.setData(twoList);
        return rm;
    }

    @Override
    public ResponseMessage getFirstKindList() {
        logger.info("KindServiceImpl  getTwoThirdChild start...");
        ResponseMessage rm = new ResponseMessage();
        List<Kind> firstKind = kindMapper.getFirstKindList();
        rm.setData(firstKind);
        return rm;
    }

    @Override
    public ResponseMessage getAllGoodsByFirstId(Integer id) {
        logger.info("KindServiceImpl  getTwoThirdChild start...id"+id);
        ResponseMessage rm = getTwoThirdChild(id);
        List<Kind> allKind = new ArrayList<>();
        List<Good> allGoods = new ArrayList<>();
        List<Kind> twoKind = (List<Kind>) rm.getData();
        Kind kind1 = kindMapper.getKindById(id);
        allKind.add(kind1);
        if(kind1.getChildren()!=null&&!kind1.getChildren().isEmpty()){
            for(Kind kind2:twoKind){
                allKind.add(kind2);
                if(!kind2.getChildren().isEmpty()){
                    allKind.addAll(kind2.getChildren());
                }
            }
        }
        for(Kind kind4:allKind){
            List<Good> findList = kindMapper.getGoodListBykid(kind4.getId());
            if(!findList.isEmpty()){
                allGoods.addAll(findList);
            }

        }
        rm.setData(allGoods);
        return rm;
    }
}
