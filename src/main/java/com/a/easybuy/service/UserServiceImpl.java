package com.a.easybuy.service;

import com.a.easybuy.dao.UserMapper;
import com.a.easybuy.pojo.PageInfo;
import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.pojo.User;
import com.a.easybuy.pojo.UserQuery;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public ResponseMessage login(String userName, String password) {
        logger.info("login userName:{} password:{}", userName, password);
        ResponseMessage responseMessage = new ResponseMessage();
        Map<String,Object> param = new HashMap<>();
        param.put("name",userName);
        User user = userMapper.getById(param);
        if(user == null){
            responseMessage.setCode("201");
            responseMessage.setMsg("没有该用户");
        }else {
            if (user.getPassword().equals(password)) {
                responseMessage.setCode("200");
                User userTemp = new User();
                userTemp.setName(user.getName());
                userTemp.setUserName(userName);
                userTemp.setId(user.getId());
                userTemp.setRole(user.getRole());
                redisTemplate.opsForValue().set(user.getName(),JSON.toJSONString(userTemp),30, TimeUnit.MINUTES);
                responseMessage.setData(userTemp);
//                redisTemplate.opsForValue().set(userName, JSON.toJSONString(user),30, TimeUnit.MINUTES);
            }else {
                responseMessage.setCode("201");
                responseMessage.setMsg("密码错误");
            }
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage register(User user,String testCode) {
        logger.info("register User:"+user+"testCode:"+testCode);
        ResponseMessage responseMessage = new ResponseMessage();
        if (redisTemplate.opsForValue().get("redisKey"+testCode) == null){
            responseMessage.setCode("202");
            return responseMessage;
        }
        user.setUserName("test");
        user.setSex(1);
        user.setRegDate(new Date());
        int count = userMapper.addUser(user);
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
        map.put("name", username);
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
    public ResponseMessage getByPage(UserQuery userQuery,Integer pageNow, Integer pageSize) {
        logger.info("getByPage pageNow:"+pageNow+"pageSize:"+pageSize+"userQuery:"+userQuery);
        ResponseMessage responseMessage = new ResponseMessage();
        PageHelper.startPage(pageNow, pageSize);
        List<User> list = userMapper.getAll(userQuery);
        PageHelper.clearPage();
        if(list.size()>0){
            responseMessage.setCode("200");
            PageInfo<User> pageInfo = new PageInfo<>();
            pageInfo.setList(list);
            pageInfo.setPageNow(pageNow);
            pageInfo.setPageSize(pageSize);
            Map<String,Object> params= new HashMap<>();
            params.put("role",userQuery.getRole());
            pageInfo.setTotal(userMapper.getCount(params));
            responseMessage.setData(pageInfo);
        }else {
            responseMessage.setCode("201");
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

    @Override
    public ResponseMessage checkRandom(String random,String email) {
        String code = (String) redisTemplate.opsForValue().get(email);
        ResponseMessage responseMessage = new ResponseMessage();
        if (StringUtils.isEmpty(code)){
            responseMessage.setCode("201");
            return responseMessage;
        }
        if (code.equals(random)){
            responseMessage.setCode("200");
            HashMap map = new HashMap();
            map.put("email", email);
            User user =userMapper.getById(map);
            responseMessage.setData(user);
            return responseMessage;
        }
        responseMessage.setCode("201");
        return responseMessage;
    }
}
