package com.a.easybuy.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {
    private final RoleInterceptor roleInterceptor;

    // 通过构造器注入拦截器
    public WebMvcConfig(RoleInterceptor roleInterceptor) {
        this.roleInterceptor = roleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/**")                 // 拦截所有请求
                .excludePathPatterns(                   // 排除以下路径
                        "/user/login",              // 登录接口// 静态资源
                        "/favicon.ico"                  // 网站图标
                );
    }


}