package com.bins.service;

import com.bins.bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentService{

    void save(Comment comment);

    List<Comment> findCommentByNewsId(Long newsId);
}
