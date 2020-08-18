package com.bins.service.impl;

import com.bins.bean.User;
import com.bins.dao.UserDao;
import com.bins.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }
}
