package com.bins.service.impl;

import com.bins.bean.News;
import com.bins.bean.Tag;
import com.bins.dao.NewsDao;
import com.bins.service.NewsService;
import com.bins.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
