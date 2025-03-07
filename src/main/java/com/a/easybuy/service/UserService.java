package com.a.easybuy.service;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.pojo.User;
import com.a.easybuy.pojo.UserQuery;

public interface UserService {
    public ResponseMessage login(String username, String password);
    public ResponseMessage register(User user, String testCode);
    public ResponseMessage checkUsername(String username);
    public ResponseMessage getByPage(UserQuery userQuery,Integer pageNow, Integer pageSize);
    public ResponseMessage updateUser(User user);
    public ResponseMessage deleteUser(int id);
    public ResponseMessage getUser(int id);
    public ResponseMessage getUserByEmail(String email);
}
