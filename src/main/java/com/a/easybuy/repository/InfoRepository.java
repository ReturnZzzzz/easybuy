package com.a.easybuy.repository;

import com.a.easybuy.pojo.Info;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoRepository extends ElasticsearchRepository<Info,String> {

    Info findInfoByTitle(String title);
}
