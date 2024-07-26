package com.bjca.securitydemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前后端分离操作下，不通过Controller来实现页面跳转
 */

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
//    @Secured("ROLE_abc")
    //PreAuthorize允许角色以ROLE_开头，也允许不用，但配置类不允许以ROLE_开头
    @PreAuthorize("hasAuthority('permission1')")
    @RequestMapping("/toMain")
    public String toMain() {
        return "redirect:main.html";
    }

    /**
     * 登录失败的页面跳转
     * @return
     */
    @RequestMapping("/toError")
    public String toError() {
        return "redirect:error.html";
    }
}
