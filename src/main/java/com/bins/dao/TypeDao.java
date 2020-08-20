package com.bins.dao;

import com.bins.bean.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeDao extends JpaRepository<Type,Long> {
    @Query("select t from Type t")  //对对象进行操作
    List<Type> findTop(Pageable pageable);
}
