package com.a.easybuy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.a.easybuy.dao")
public class  EasybuyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasybuyApplication.class, args);
    }

}
