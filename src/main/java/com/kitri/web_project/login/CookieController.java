package com.kitri.web_project.login;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cookie")
public class CookieController {
    @PostMapping
    public String makecookie(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("email", request.getParameter("email"));
        cookie.setMaxAge(60*60*24*7);
        response.addCookie(cookie);

        return cookie.getValue();
    }


}
