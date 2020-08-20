package com.bins.service.impl;

import com.bins.bean.News;
import com.bins.bean.NewsQuery;
import com.bins.bean.Tag;
import com.bins.bean.Type;
import com.bins.dao.NewsDao;
import com.bins.service.NewsService;
import com.bins.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public Page<News> findAll(Pageable pageable) {
        return newsDao.findAll(pageable);
    }

    @Override
    public News findById(Long id) {
        return newsDao.getOne(id);
    }

    @Override
    public void add(News news) {
        if(news.getId()==null){
            news.setCreateTime(new Date());
            news.setUpdateTime(new Date());
            newsDao.save(news);
        }else{
            News n = newsDao.getOne(news.getId());
            BeanUtils.copyProperties(news,n, MyBeanUtils.getNullPropertyNames(news));
            n.setUpdateTime(new Date());
            newsDao.save(n);
        }
    }

    @Override
    public String findTagIds(List<Tag> tags) {
        StringBuffer stringBuffer = new StringBuffer();
        if(!tags.isEmpty()){
            boolean flag = false;
            for(Tag tag:tags){
                if(flag){
                    stringBuffer.append(",");
                    stringBuffer.append(tag.getId());
                }else{
                    stringBuffer.append(tag.getId());
                    flag = true;
                }
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public Page<News> searchNews(Pageable pageable, NewsQuery newsQuery) {


        Specification<News> s = new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();     //每一个查询条件都是一个predicate
                if(!StringUtils.isEmpty(newsQuery.getTitle())){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+newsQuery.getTitle()+"%"));
                }
                if(!StringUtils.isEmpty(newsQuery.getTypeId())){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),newsQuery.getTypeId()));
                }
                if(newsQuery.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),newsQuery.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        };
        return newsDao.findAll(s,pageable);
    }

    @Override
    public Page<News> findAll(String query, Pageable pageable) {
        return newsDao.findByQuery("%"+query+"%",pageable);
    }

    @Override
    public Page<News> searchNewsByTagId(Long id, Pageable pageable) {



        return newsDao.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),id);
            }
        }, pageable);
    }

    @Override
    public List<News> findTop(int i) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,i);
        return newsDao.findTop(pageable);
    }
}
