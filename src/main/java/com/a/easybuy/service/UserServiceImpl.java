package com.a.easybuy.service;

import com.a.easybuy.dao.UserMapper;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResponseMessage login(String userName, String password) {
        logger.info("login userName:{} password:{}", userName, password);
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String,Object> param = new HashMap<>();
        param.put("username",userName);
        User user = userMapper.getById(param);
        if(user == null){
            responseMessage.setCode("201");
            responseMessage.setMsg("没有该用户");
        }else {
            if (user.getPassword().equals(password)) {
                responseMessage.setCode("200");
                responseMessage.setData(user);
                redisTemplate.opsForValue().set(userName,user.getId(),30, TimeUnit.MINUTES);
            }else {
                responseMessage.setCode("201");
                responseMessage.setMsg("密码错误");
            }
        }
        return responseMessage;
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
        User user =userMapper.getById(map);
        ResponseMessage responseMessage = new ResponseMessage();
        if(user!=null){
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
        return responseMessage;
    }

    @Override
    public ResponseMessage getUser(int id) {
        logger.info("get User:"+id);
        ResponseMessage responseMessage = new ResponseMessage();
        HashMap map = new HashMap();
        map.put("id", id);
        User user = userMapper.getById(map);
        if(user != null){
            responseMessage.setCode("200");
            responseMessage.setData(user);
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getUserByEmail(String email) {
        logger.info("getUser email:"+email);
        ResponseMessage responseMessage = new ResponseMessage();
        HashMap map = new HashMap();
        map.put("email", email);
        User user =userMapper.getById(map);
        if(user!=null){
            responseMessage.setCode("200");
            responseMessage.setData(user);
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }
}
