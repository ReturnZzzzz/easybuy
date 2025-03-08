package com.a.easybuy.dao;

import com.a.easybuy.pojo.Like;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeMapper {
    int addGoodToLike(@Param("uid") Integer uid, @Param("gid") Integer gid);
    int delGoodInLike(@Param("uid") Integer uid, @Param("gid") Integer gid);
    List<Like> getLikeList(Integer uid);
}
