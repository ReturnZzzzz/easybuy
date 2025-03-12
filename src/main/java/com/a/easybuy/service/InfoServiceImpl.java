package com.a.easybuy.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.a.easybuy.dao.InfoMapper;
import com.a.easybuy.pojo.Info;
import com.a.easybuy.pojo.PageInfo;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.repository.InfoRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.BulkFailureException;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InfoServiceImpl implements InfoService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private InfoRepository infoRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Override
    public ResponseMessage getInfoList(Integer pageNow, Integer pageSize) {
        logger.info("InfoServiceImpl getInfoList start...");
        ResponseMessage rm = new ResponseMessage();
        PageInfo pageInfo = new PageInfo();
        Page<Info> page = PageHelper.startPage(pageNow,pageSize);
        List<Info> infoList = infoMapper.getInfoList();
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal((int)page.getTotal());
        pageInfo.setPages(page.getPages());
        pageInfo.setPageNow(pageNow);
        pageInfo.setList(infoList);
        rm.setData(pageInfo);
        return rm;
    }

    @Override
    public ResponseMessage addInfo(String title,String content) {
        logger.info("InfoServiceImpl delInfo start... title:"+title+"content:"+content);
        ResponseMessage rm = new ResponseMessage();
        Info info = new Info();
        info.setTitle(title);
        info.setContent(content);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String newDate=sdf.format(date);
        logger.debug("........date:"+newDate);
        info.setCreateDate(newDate);
        int addCount = infoMapper.addInfo(info);
        if(addCount>0){
            rm.setCode("200");
            rm.setMsg("添加成功!");
        }else{
            rm.setCode("201");
            rm.setMsg("添加失败!");
        }
        return rm;
    }

    @Override
    public ResponseMessage delInfo(Integer id) {
        logger.info("InfoServiceImpl delInfo start... id:"+id);
        ResponseMessage rm = new ResponseMessage();
        int delCount = infoMapper.delInfo(id);
        if(delCount>0){
            rm.setCode("200");
            rm.setMsg("删除成功！");
        }else{
            rm.setCode("201");
            rm.setMsg("删除失败!");
        }
        return rm;
    }

    @Override
    public ResponseMessage findInfo(Integer id) {
        logger.info("InfoServiceImpl findInfo start...");
        ResponseMessage rm = new ResponseMessage();
        Info info = infoMapper.findInfo(id);
        rm.setData(info);
        logger.debug("InfoServiceImpl findInfo values rm:"+rm);
        return rm;
    }

    @Override
    public ResponseMessage changeInfo(Integer id,String title,String content) {
        logger.info("InfoServiceImpl changeInfo start... id:"+id+"title:"+title+"content:"+content);
        ResponseMessage rm = new ResponseMessage();
        Info info = new Info();
        info.setId(id);
        info.setTitle(title);
        info.setContent(content);
        int changeCount = infoMapper.changeInfo(info);
        if(changeCount>0){
            rm.setCode("200");
            rm.setMsg("修改成功!");
        }else{
            rm.setCode("201");
            rm.setMsg("修改失败!");
        }
        return rm;
    }

    @Override
    public ResponseMessage addInfoToES() {
        logger.info("InfoServiceImpl addInfoToES start...");
        ResponseMessage rm = new ResponseMessage();
        List<Info> infoList = infoMapper.getInfoList();

        try {
            // 捕获保存异常
            infoRepository.saveAll(infoList);
            rm.setCode("200");
        } catch (BulkFailureException e) {
            logger.error("批量保存失败: {}", e.getMessage());
            rm.setCode("500");
            rm.setMsg("部分数据保存失败");
        } catch (Exception e) {
            logger.error("保存异常: {}", e.getMessage());
            rm.setCode("500");
            rm.setMsg("数据保存失败");
        }
        return rm;
    }

    public ResponseMessage userByESPage(Integer pageNow,Integer pageSize,String title){
    logger.info("InfoServiceImpl userByESPage start...pageNow:"+pageNow+"pageSize:"+pageSize+"title:"+title);
    ResponseMessage rm = new ResponseMessage();
    PageInfo<Info> pageInfo = new PageInfo<>();
    pageInfo.setPageNow(pageNow);
    pageInfo.setPageSize(pageSize);
    BoolQueryBuilder bqb = QueryBuilders.boolQuery();
    if(!title.isEmpty()){
        bqb.must(QueryBuilders.matchQuery("title",title));
        pageNow=1;
    }
    HighlightBuilder hb =new HighlightBuilder();
    hb.field("title");
    hb.preTags("<font style='color:OrangeRed'>");
    hb.postTags("</font>");
    NativeSearchQueryBuilder nsqb = new NativeSearchQueryBuilder();
    nsqb.withHighlightBuilder(hb);
    SearchHits<Info> sh = elasticsearchTemplate.search(nsqb.withQuery(bqb).build(),Info.class);
    pageInfo.setTotal((int)sh.stream().count());
    nsqb.withQuery(bqb).withPageable(PageRequest.of(pageNow-1,pageSize));
    SearchHits<Info> search = elasticsearchTemplate.search(nsqb.build(),Info.class);
    logger.debug("the values search:"+search);
    List<Info> list = new ArrayList<>();
    for(SearchHit<Info> searchHit:search){
        Info info = searchHit.getContent();
        List<String> highTitle = searchHit.getHighlightField("title");
        if(highTitle!=null&&highTitle.size()>0){
            for(String str : highTitle){
                info.setTitle(str);
            }
        }
        list.add(info);
        pageInfo.setList(list);
    }
    if(!list.isEmpty()){
        rm.setCode("200");
    }
    rm.setData(pageInfo);
    return rm;
    }
}
