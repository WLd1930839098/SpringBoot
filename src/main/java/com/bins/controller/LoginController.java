package com.bins.controller;

import com.bins.bean.User;
import com.bins.service.UserService;
import com.bins.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
//        User user = userService.checkUser(username,password);
//        if(user!=null){
//            session.setAttribute("user",user);
//            return "admin/index";
//        }else{
//            redirectAttributes.addFlashAttribute("msg","用户名或密码错误");
//            return "redirect:/admin/toLogin";
//        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Util.code(password));
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);//执行shiro的认证方法
            User user=(User)subject.getPrincipal();
            session.setAttribute("user",user);
            return "admin/index";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("msg","用户名或密码错误");
            return "redirect:/admin/toLogin";
        }
    }

}
