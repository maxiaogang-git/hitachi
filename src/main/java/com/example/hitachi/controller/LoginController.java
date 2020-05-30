package com.example.hitachi.controller;

import com.example.hitachi.entity.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    /**
     * 页面登陆
     * @param username
     * @param password
     * @param session
     * @param model
     * @return
     */
    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model){
        if(!StringUtils.isEmpty(username) && "123456".equals(password)){
            session.setAttribute("loginUser",username);
            return "redirect:/hitachi/list.html";
        }else{
            model.addAttribute("msg","用户名密码错误");
            return "login";
        }

    }

    /**
     * 退出登陆
     * @param session
     * @return
     */
    @GetMapping(value = "/login_out")
    public String loginOut(HttpSession session){
        Object loginUser = session.getAttribute("loginUser");
        if(loginUser != null){
            session.invalidate();
        }
        return "login";
    }

}
