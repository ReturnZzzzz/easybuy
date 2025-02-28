package com.a.easybuy.service;

import com.a.easybuy.dao.UserMapper;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseMessage login(String username, String password) {
        logger.info("login username:{} password:{}", username, password);

        return null;
    }

    @Override
    public ResponseMessage register(User user) {
        logger.info("register User:"+user);
        int count = userMapper.addUser(user);
        ResponseMessage responseMessage = new ResponseMessage();
        if(count>0){
            responseMessage.setCode("200");
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage checkUsername(String username) {
        logger.info("check username:"+username);
        HashMap map = new HashMap();
        map.put("username", username);
        List<User> userList =userMapper.getByPage(map);
        ResponseMessage responseMessage = new ResponseMessage();
        if(userList.size()>0){
            responseMessage.setCode("201");
        }else {
            responseMessage.setCode("200");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage updateUser(User user) {
        logger.info("update User:"+user);
        int count = userMapper.updateUser(user);
        ResponseMessage responseMessage = new ResponseMessage();
        if(count>0){
            responseMessage.setCode("200");
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage deleteUser(int id) {
        logger.info("delete User:"+id);
        int count = userMapper.delUser(id);
        ResponseMessage responseMessage = new ResponseMessage();
        if(count>0){
            responseMessage.setCode("200");
        }else {
            responseMessage.setCode("201");
        }
        return null;
    }

    @Override
    public ResponseMessage getUser(int id) {
        return null;
    }

}
