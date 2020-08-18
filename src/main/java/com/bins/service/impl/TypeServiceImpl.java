package com.bins.service.impl;

import com.bins.bean.Type;
import com.bins.dao.TypeDao;
import com.bins.dao.UserDao;
import com.bins.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;
    @Override
    public List<Type> findAll() {
        return typeDao.findAll();
    }
}
