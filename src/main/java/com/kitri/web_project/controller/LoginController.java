package com.kitri.web_project.controller;

import com.kitri.web_project.dto.LoginUser;
import com.kitri.web_project.dto.ResponseClient;
import com.kitri.web_project.dto.SocialLogin;
import com.kitri.web_project.mybatis.mappers.UserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @PostMapping
    public Long Log(@RequestBody LoginUser loginUser, HttpServletResponse response){
        ResponseClient responseUsers = userMapper.findByEmail(loginUser.getEmail());
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
        cookie1.setMaxAge(0);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie1.setPath("/");
        res.addCookie(cookie);
        res.addCookie(cookie1);
        return true;
    }

    @PostMapping("/social")
    public Long socialLogin(@RequestBody SocialLogin socialLogin, HttpServletResponse response){
        ResponseClient responseClient = userMapper.findByEmail(socialLogin.getEmail());
        if(responseClient != null){
            CookieSet(response, responseClient);
            return responseClient.getId();
        } else {
            userMapper.signup(socialLogin.getName(), socialLogin.getEmail(), socialLogin.getPassword(), "", socialLogin.getImage());
            ResponseClient responseClient1 = userMapper.findByEmail(socialLogin.getEmail());
            CookieSet(response, responseClient1);
            return responseClient1.getId();
        }
    }

    public void CookieSet(HttpServletResponse response, ResponseClient responseClient){
        Cookie cookie = new Cookie("email", responseClient.getEmail());
        Cookie cookie1 = new Cookie("id", responseClient.getId().toString());
        cookie.setPath("/");
        cookie1.setPath("/");
        response.addCookie(cookie);
        response.addCookie(cookie1);
    }
}
