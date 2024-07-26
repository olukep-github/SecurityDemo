package com.bjca.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

//    @RequestMapping("/login")
//    public String login() {
//        return "redirect:main.html";
//    }

    /**
     * 登陆成功的页面跳转
     * @return
     */
    @RequestMapping("toMain")
    public String toMain() {
        return "redirect:main.html";
    }

    /**
     * 登录失败的页面跳转
     * @return
     */
    @RequestMapping("toError")
    public String toError() {
        return "redirect:error.html";
    }
}
