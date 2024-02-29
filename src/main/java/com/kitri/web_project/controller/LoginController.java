package com.kitri.web_project.controller;

import com.kitri.web_project.dto.LoginUser;
import com.kitri.web_project.dto.ResponseClient;
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
        return  0L;
    Cookie cookie = new Cookie("email", responseUsers.getEmail());
    Cookie cookie1 = new Cookie("id", responseUsers.getId().toString());
        cookie.setPath("/");
        cookie1.setPath("/");
        response.addCookie(cookie);
        response.addCookie(cookie1);
        return responseUsers.getId();
        //        cookie.setMaxAge(60*60*24*7);
    }

    @GetMapping("/logout")
    public boolean logOut(HttpServletRequest request, HttpServletResponse res){
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
}
