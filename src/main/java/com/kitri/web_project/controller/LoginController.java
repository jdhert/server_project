package com.kitri.web_project.controller;

import com.kitri.web_project.dto.login.LoginUser;
import com.kitri.web_project.dto.login.ResponseClient;
import com.kitri.web_project.dto.login.SocialLogin;
import com.kitri.web_project.mappers.UserMapper;
import com.kitri.web_project.service.ApiService;
import com.kitri.web_project.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    UserMapper userMapper;

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }


    @PostMapping
    public Long Log(@RequestBody LoginUser loginUser, HttpServletResponse response){
        ResponseClient responseUsers = userMapper.findByEmail(loginUser.getEmail(), false);
        if(responseUsers == null)
            return 0L;
        if(!Objects.equals(loginUser.getPassword(), responseUsers.getPassword()))
            return 0L;
        if(!responseUsers.getSocial()) {
            CookieSet(response, responseUsers);
            return responseUsers.getId();
        } return 0L;
    }

    @GetMapping("/logout")
    public boolean logOut(HttpServletResponse res){
        Cookie cookie = new Cookie("email", null);
        Cookie cookie1 = new Cookie("id", null);
        Cookie cookie2 = new Cookie("name", null);
        cookie1.setMaxAge(0);
        cookie.setMaxAge(0);
        cookie2.setMaxAge(0);
        cookie.setPath("/");
        cookie1.setPath("/");
        cookie2.setPath("/");
        res.addCookie(cookie);
        res.addCookie(cookie1);
        res.addCookie(cookie2);
        return true;
    }

    @PostMapping("/social")
    public Long socialLogin(@RequestBody SocialLogin socialLogin, HttpServletResponse response){
       return loginService.socialLogin(socialLogin, response);
    }

    @GetMapping("/findPass")
    public boolean findPassword(String email){
        return loginService.findPassword(email);
    }

    @GetMapping("/sendCode")
    public void sendCode(String email){
        loginService.sendCode(email);
    }

    @GetMapping("/codeVerify")
    public boolean verifyCode(String code){
        return loginService.verifyCode(code);
    }

    public void CookieSet(HttpServletResponse response, ResponseClient responseClient){
        Cookie cookie = new Cookie("email", responseClient.getEmail());
        Cookie cookie1 = new Cookie("id", responseClient.getId().toString());
        Cookie cookie2 = new Cookie("name", responseClient.getName());
        cookie.setPath("/");
        cookie1.setPath("/");
        cookie2.setPath("/");
        response.addCookie(cookie);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
    }
}
