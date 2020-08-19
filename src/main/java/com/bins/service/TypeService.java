package com.bins.service;

import com.bins.bean.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Page<Type> findAll(Pageable pageable);

    void deleteById(Long id);

    void add(Type type);

    Type findById(Long id);

    List<Type> findAll();
}
