package com.bins.controller;

import com.bins.bean.*;
import com.bins.service.NewsService;
import com.bins.service.TagService;
import com.bins.service.TypeService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/admin/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @RequestMapping
    public String findAll(@PageableDefault(size=5,sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model){

        Page<News> page = newsService.findAll(pageable);
        List<Type> types = typeService.findAll();
        model.addAttribute("page",page);
        model.addAttribute("types",types);

        return "/admin/news";
    }

    @RequestMapping("input/{id}")
    public String toInput(@PathVariable Long id,Model model){
        if(id==-1){//新增
            model.addAttribute("news",new News());
        }else{
            News news = newsService.findById(id);
            List<Tag> tags = news.getTags();
            String tagIds = newsService.findTagIds(tags);
            news.setTagIds(tagIds);
            model.addAttribute("news",news);
        }
        List<Type> types = typeService.findAll();
        model.addAttribute("types",types);
        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags",tags);
        return "admin/news-input";
    }

    @RequestMapping("input")
    public String input(News news, HttpSession session){
        List<Tag> tags = tagService.findByIds(news.getTagIds());
        news.setTags(tags);//在t_news_tags插入数据
        User user = (User)session.getAttribute("user");
        news.setUser(user);
        newsService.add(news);
        return "redirect:/admin/news";
    }

    @RequestMapping("search")
    public String search(@PageableDefault(size = 5,sort ={"updateTime"} ,direction = Sort.Direction.DESC)Pageable pageable,
                         NewsQuery newsQuery, Model model){
        Page<News> page = newsService.searchNews(pageable,newsQuery);
        model.addAttribute("page",page);
        return "admin/news::newsList";
    }
}
