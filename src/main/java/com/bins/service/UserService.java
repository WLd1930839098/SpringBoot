package com.bins.service;

import com.bins.bean.User;

public interface UserService {
    User checkUser(String username,String password);
}
