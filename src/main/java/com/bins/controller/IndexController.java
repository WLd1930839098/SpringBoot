package com.bins.controller;

import com.bins.bean.*;
import com.bins.service.CommentService;
import com.bins.service.NewsService;
import com.bins.service.TagService;
import com.bins.service.TypeService;
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
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @RequestMapping
    //根路径
    public String index(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                        Model model){
        Page<News> page = newsService.findAll(pageable);
        List<Type> types = typeService.findTop(5);
        List<Tag> tags = tagService.findTop(5);
        model.addAttribute("page",page);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        return "index";
    }

    @RequestMapping("/search")
    public String search(@PageableDefault(size = 5,sort={"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                          Model model,String query){
        Page<News> page = newsService.findAll(query,pageable);


        model.addAttribute("page",page);

        return "search";
    }

    @RequestMapping("/tags/{id}")
    public String types(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                        @PathVariable Long id, Model model){
        //TODO
        List<Tag> tags = tagService.findTop(tagService.findAll().size());
        if(id==-1){
            id=tags.get(0).getId();
        }
        Page<News> page = newsService.searchNewsByTagId(id,pageable);
        model.addAttribute("tags",tags);
        model.addAttribute("page",page);
        return "tags";
    }

    @RequestMapping("/types/{id}")
    public String tags(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable
            ,@PathVariable Long id,Model model){
        //TODO
        List<Type> types = typeService.findTop(typeService.findAll().size());
        if(id==-1){
            id=types.get(0).getId();
        }
        NewsQuery newsQuery = new NewsQuery();
        newsQuery.setTypeId(id.toString());
        Page<News> page = newsService.searchNews(pageable,newsQuery);
        model.addAttribute("types",types);
        model.addAttribute("page",page);
        return "types";
    }

    @RequestMapping("/footer/lastestNews")
    public String lst(Model model){
        List<News> newsList = newsService.findTop(3);
        model.addAttribute("lastestNewsList",newsList);
        return "_fragments::lastestNewsList1";
    }

    @RequestMapping("about")
    public String about(){
        return "about";
    }

    @RequestMapping("/news/{id}")
    public String findById(@PathVariable Long id,Model model){

        News news = newsService.findById(id);

        model.addAttribute("news",news);
        return "news";
    }

    @RequestMapping("/comments")
    public String comment(Comment comment, HttpSession session){

        User user = (User) session.getAttribute("user");
        if(user==null){
            comment.setAdminComment(false);
        }else{
            comment.setAdminComment(true);
        }
        commentService.save(comment);

        return "redirect:/comments/"+comment.getNews().getId();
    }

    @RequestMapping("/comments/{newsId}")
    public String comments(@PathVariable Long newsId,Model model){
        List<Comment> comments = commentService.findCommentByNewsId(newsId);

        model.addAttribute("comments",comments);
        return "news::commentList";
    }

    @RequestMapping("/archives")
    public String archives(Model model){
        HashMap<String,List<News>> map = newsService.getMap();
        long count = newsService.getCount();
        model.addAttribute("archiveMap",map);
        model.addAttribute("newsCount",count);
        return "archives";
    }
}
