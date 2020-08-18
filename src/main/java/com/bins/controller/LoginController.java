package com.bins.controller;

import com.bins.bean.User;
import com.bins.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("toLogin")
    public String toLogin(){
        return "admin/login";//默认后缀为".html"
    }

    @RequestMapping("login")
    public String login(String username, String password, HttpSession session){
        User user = userService.checkUser(username,password);
        if(user!=null){
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            return "redirect:/admin/toLogin";
        }
    }

}
