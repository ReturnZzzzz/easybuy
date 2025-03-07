package com.a.easybuy.controller;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.pojo.User;
import com.a.easybuy.service.UserService;
import com.a.easybuy.pojo.UserQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseMessage register(User user,String testCode) {
        logger.info("register user:" + user);
        ResponseMessage responseMessage = userService.register(user,testCode);
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

    @RequestMapping("getPage")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getPage(Integer pageNow,Integer pageSize, @RequestParam(required = false) Integer role) {
        logger.info("goodService.getPage pageNow:"+pageNow+",pageSize:"+pageSize+",role"+role);
        UserQuery userQuery = new UserQuery();
        if (role!=null){
            userQuery.setRole(role);
        }
        ResponseMessage responseMessage =userService.getByPage(userQuery,pageNow,pageSize);
        logger.debug("goodService.getPage responseMessage:"+responseMessage+"goodQuery:"+userQuery);
        return responseMessage;
    }

    @RequestMapping("update")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage update(User user) {
        logger.info("update user:" + user);
        ResponseMessage responseMessage = userService.updateUser(user);
        logger.debug("userService update user:" + user);
        return responseMessage;
    }
    @RequestMapping("del")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage del(int id) {
        logger.info("del id:" + id);
        ResponseMessage responseMessage = userService.deleteUser(id);
        logger.debug("userService del user:" + id);
        return responseMessage;
    }
    @RequestMapping("getById")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public ResponseMessage getById(int id) {
        logger.info("getById id:" + id);
        ResponseMessage responseMessage = userService.getUser(id);
        logger.debug("userService getUser user:" + id);
        return responseMessage;
    }
}
