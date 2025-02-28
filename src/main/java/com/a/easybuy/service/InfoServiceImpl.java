package com.a.easybuy.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.a.easybuy.dao.InfoMapper;
import com.a.easybuy.pojo.Info;
import com.a.easybuy.pojo.PageInfo;
import com.a.easybuy.pojo.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private InfoMapper infoMapper;
    @Override
    public ResponseMessage getInfoList(Integer pageNow, Integer pageSize) {
        logger.info("InfoServiceImpl getInfoList start...");
        ResponseMessage rm = new ResponseMessage();
        PageInfo pageInfo = new PageInfo();
        Map<String,Object> params = new HashMap<>();
        if(!StringUtils.isEmpty(pageNow.toString())){
            params.put("pageNow",pageNow);
        }
        if(!StringUtils.isEmpty(pageSize.toString())){
            params.put("pageSize",pageSize);
        }
        Page page = PageHelper.startPage(pageNow,pageSize);
        pageInfo.setTotal((int)page.getTotal());
        pageInfo.setPages(page.getPages());
        pageInfo.setPageNow(pageNow);
        pageInfo.setPageSize(pageSize);
        pageInfo.setList(infoMapper.getInfoList(params));
        rm.setData(pageInfo);
        return rm;
    }

    @Override
    public ResponseMessage addInfo(Info info) {
        return null;
    }

    @Override
    public ResponseMessage delInfo(Integer id) {
        return null;
    }

    @Override
    public ResponseMessage findInfo(Integer id) {
        return null;
    }
}
