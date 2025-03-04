package com.a.easybuy.service;

import com.a.easybuy.pojo.Good;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.pojo.GoodsQuery;

public interface GoodService {
    public ResponseMessage addGood(Good good);
    public ResponseMessage updateGood(Good good);
    public ResponseMessage deleteGood(int id);
    public ResponseMessage getGood(int id);
    public ResponseMessage getGoodsByPage(GoodsQuery query, int pageNum, int pageSize);
}