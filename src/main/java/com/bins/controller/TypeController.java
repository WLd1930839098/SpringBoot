package com.bins.controller;

import com.bins.bean.Type;
import com.bins.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping
    public String list(Model model){//利用Model对象向浏览器传递数据
        List<Type> types = typeService.findAll();
        model.addAttribute("types",types);
        return "admin/types";
    }
}
