package com.a.easybuy.service;

import com.a.easybuy.pojo.ResponseMessage;

public interface LikeService {
    public ResponseMessage addGoodToLike(Integer uid, Integer gid);
    public ResponseMessage getLikeList(Integer uid);
    public ResponseMessage delGoodsInLike(Integer uid, Integer gid);
}
