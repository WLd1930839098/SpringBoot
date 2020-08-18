package com.bins.service.impl;

import com.bins.bean.Type;
import com.bins.dao.TypeDao;
import com.bins.dao.UserDao;
import com.bins.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;
    @Override
    public Page<Type> findAll(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        typeDao.deleteById(id);
    }

    @Override
    public void add(Type type) {
        //更新和添加都可以使用sava，若id有值则是更新；否则是添加
        typeDao.save(type);
    }

    @Override
    public Type findById(Long id) {
        return typeDao.getOne(id);
    }
}
