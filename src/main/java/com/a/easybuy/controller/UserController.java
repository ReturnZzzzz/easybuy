package com.a.easybuy.controller;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.pojo.User;
import com.a.easybuy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage login(String username, String password) {
        logger.info("login start username:" + username + " password:" + password);
        ResponseMessage responseMessage = userService.login(username, password);
        logger.debug("userService login username:" + username + " password:" + password);
        return responseMessage;
    }

    @RequestMapping("register")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage register(User user) {
        logger.info("register user:" + user);
        ResponseMessage responseMessage = userService.register(user);
        logger.debug("userService register user:" + user);
        return responseMessage;
    }
    @RequestMapping("check")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage check(String username) {
        logger.info("check username:" + username);
        ResponseMessage responseMessage = userService.checkUsername(username);
        logger.debug("userService check username:" + username);
        return responseMessage;
    }
}
