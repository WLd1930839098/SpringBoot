package com.bins.dao;

import com.bins.bean.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsDao extends JpaRepository<News,Long> {
}
