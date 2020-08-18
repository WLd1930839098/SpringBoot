package com.bins.controller;

import com.bins.bean.Type;
import com.bins.service.TypeService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable, Model model){//利用Model对象向浏览器传递数据

        Page<Type> page = typeService.findAll(pageable);
        model.addAttribute("page",page);
        return "admin/types";
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        typeService.deleteById(id);
        return "redirect:/admin/types";
    }

    @RequestMapping("toInput/{id}")
    public String toInput(@PathVariable Long id,Model model){
        if(id==-1){//新增
            model.addAttribute("type",new Type());
        }else{//更新
            Type type = typeService.findById(id);
            model.addAttribute("type",type);
        }
        return "admin/types-input";
    }

    @RequestMapping("input")
    public String input(Type type){
        typeService.add(type);
        return "redirect:/admin/types";
    }
}
