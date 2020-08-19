package com.bins.controller;

import com.bins.bean.Tag;
import com.bins.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/tags")
public class TagController {

    @Autowired
    private TagService tagService;
    @RequestMapping
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable, Model model){//利用Model对象向浏览器传递数据

        Page<Tag> page = tagService.findAll(pageable);

        model.addAttribute("page",page);
        return "admin/tags";
    }

    @RequestMapping("toInput/{id}")
    public String toInput(@PathVariable Long id,Model model){
        if(id==-1){     //新增
            model.addAttribute("tag",new Tag());
        }else{//更新
            Tag tag=tagService.findById(id);
            model.addAttribute("tag",tag);
        }

        return "admin/tags-input";
    }

    @RequestMapping("input")
    public String input(Tag tag){
        tagService.input(tag);
        return "redirect:/admin/tags";
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        tagService.deleteById(id);
        return "redirect:/admin/tags";
    }
}
