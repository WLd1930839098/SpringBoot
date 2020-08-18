package com.bins.controller;

import com.bins.bean.User;
import com.bins.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String login(String username, String password, HttpSession session,
                        RedirectAttributes redirectAttributes){
        User user = userService.checkUser(username,password);
        if(user!=null){
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            redirectAttributes.addFlashAttribute("msg","用户名或密码错误");
            return "redirect:/admin/toLogin";
        }
    }

}
