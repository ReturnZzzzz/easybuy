package com.a.easybuy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.a.easybuy.pojo.User;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;
@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

//    // 免检路径白名单
//    private static final List<String> WHITE_LIST = Arrays.asList(
//            "/user/login",
//            "/proCate/getAllProCateInfo",
//            "/news/getNewslist",
//            "/product/getProductList",
//            "/product/getProductById",
//            "/user/logOff",
//            "/getAllProCateInfo",
//            "/addUserInfo",
//            "/hasUser",
//            "/registerUser",
//            "/captcha",
//            "/send/sendMail",
//            "/user/upDatePassWd",
//            "/getNewProduct",
//            "/getOldProduct",
//            "/product/getProductById",
//            "/product/getProductListByEs",
//            "/product/getCommend"
//    );
//
//    // 需要管理员角色的接口路径模式（支持Ant风格通配符）
//    private static final List<String> ADMIN_PATHS = Arrays.asList(
//            "/admin/**",          // 所有管理员接口
//            "/system/**",         // 系统管理接口
//            "/user/deleteUser"    // 删除用户接口
//    );

    // 需要登录验证的路径模式
    private static final List<String> LOGIN_PATHS = Arrays.asList(
            "/address/**",
            "/car/**",
            "/order/**"
    );

    // 需要管理员权限的路径模式
    private static final List<String> ADMIN_PATHS = Arrays.asList(
            "/user/getByPage",
            "/user/update",
            "/user/del",
            "/info/addInfo",
            "/info/changeInfo",
            "/info/delInfo",
            "/good/getPage",
            "/good/add",
            "/good/update",
            "/good/del",
            "/kind/addKind",
            "/kind/changeKind",
            "/kind/delKind"
    );
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 放行 OPTIONS 请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(200);
            return true; // 必须返回 true 才能继续处理
        }

        // 1. 设置CORS响应头（所有请求都添加）
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "3600");

        // 统一处理 OPTIONS 请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(200);
            return true; // 直接放行，不执行后续逻辑
        }

        String requestURI = request.getRequestURI();
        // 2. 检查是否需要登录验证
        boolean requireLogin = LOGIN_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));

        // 3. 检查是否需要管理员权限
        boolean requireAdmin = ADMIN_PATHS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));

        // 4. 不需要验证的直接放行
        if (!requireLogin && !requireAdmin) {
            return true;
        }


        // 1. 检查是否登录
        String name = request.getHeader("name");
        if (name == null) {
            response.sendError(401, "未登录");
            return false;
        }
        String user = redisTemplate.opsForValue().get(name);
        if (user == null) {
            response.sendError(401, "未登录");
            return false;
        }

        User userInfo = JSON.parseObject(user, User.class);

        // 2. 用户不存在或未登录
        if (userInfo == null) {
            response.sendError(401, "登录已过期");
            return false;
        }

        // 3. 检查访问信息管理接口时的角色权限
        List<String> protectedPaths = Arrays.asList("/api/admin/user", "/api/admin/data");
        if (protectedPaths.contains(requestURI)) {
            if (userInfo.getRole() != 2 && userInfo.getRole() != 3) {
                response.sendError(403, "权限不足");
                return false;
            }
        }
        return true;
    }
}
