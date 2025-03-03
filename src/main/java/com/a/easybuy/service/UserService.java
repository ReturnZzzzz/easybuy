package com.a.easybuy.service;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.pojo.User;

public interface UserService {
    public ResponseMessage login(String username, String password);
    public ResponseMessage register(User user);
    public ResponseMessage checkUsername(String username);
    public ResponseMessage updateUser(User user);
    public ResponseMessage deleteUser(int id);
    public ResponseMessage getUser(int id);
    public ResponseMessage getUserByEmail(String email);
}
