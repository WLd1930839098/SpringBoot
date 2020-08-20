package com.bins.service;

import com.bins.bean.News;
import com.bins.bean.NewsQuery;
import com.bins.bean.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {
    Page<News> findAll(Pageable pageable);

    News findById(Long id);

    void add(News news);

    String findTagIds(List<Tag> tags);

    Page<News> searchNews(Pageable pageable, NewsQuery newsQuery);

    Page<News> findAll(String query, Pageable pageable);

    Page<News> searchNewsByTagId(Long id, Pageable pageable);

    List<News> findTop(int i);
}
