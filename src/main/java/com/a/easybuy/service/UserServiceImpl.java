package com.a.easybuy.service;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public ResponseMessage login(String username, String password) {
        return null;
    }

    @Override
    public ResponseMessage register(User user) {
        return null;
    }

    @Override
    public ResponseMessage checkUsername(String username) {
        return null;
    }

    @Override
    public ResponseMessage updateUser(User user) {
        return null;
    }

    @Override
    public ResponseMessage deleteUser(int id) {
        return null;
    }
}
