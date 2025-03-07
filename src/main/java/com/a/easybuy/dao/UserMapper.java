package com.a.easybuy.dao;

import com.a.easybuy.pojo.User;
import com.a.easybuy.pojo.UserQuery;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    public List<User> getAll(UserQuery userQuery);
    public User getById(Map<String, Object> params);
    public int getCount(Map<String, Object> params);
    public int addUser(User user);
    public int updateUser(User user);
    public int delUser(int id);
}
