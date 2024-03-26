package com.kitri.web_project.service;

import com.kitri.web_project.dto.login.ResponseClient;
import com.kitri.web_project.dto.login.SocialLogin;
import com.kitri.web_project.mappers.UserMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;

@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    EmailService emailService;

    private final HashSet<String> codeList = new HashSet<>();

    public boolean findPassword(String email){
        ResponseClient responseClient = userMapper.findByEmail(email, false);
        if(responseClient == null)
            return false;
        else{
           String tempPassword =  RandomStringUtils.random(8, true, true);
           userMapper.updatePassword(tempPassword, responseClient.getId());
           emailService.sendSimpleMessage(responseClient.getEmail(), "petSounds 비밀번호 재설정 메일",
                    "귀하의 임시 비밀번호는 \"" + tempPassword + "\" 입니다.\n" + "꼭 로그인하신 후 비밀번호를 변경하시길 바랍니다.");
           return true;
        }
    }

    public void sendCode(String email){
        String verifyCode =  RandomStringUtils.random(8, true, true);
        emailService.sendSimpleMessage(email, "petSounds 계정 등록",
                "petSounds 계정을 등록하시는 유저라면 사이트에서 \"" + verifyCode + "\" 등록 코드를 입력해주세요.");
        codeList.add(verifyCode);
    }

    public boolean verifyCode(String code){
        if(codeList.contains(code)) {
            codeList.remove(code);
            return true;
        }else return false;
    }

    public Long socialLogin(SocialLogin socialLogin, HttpServletResponse response){
        ResponseClient responseClient = userMapper.findByEmail(socialLogin.getEmail(), true);
        System.out.println(socialLogin.getEmail());
        if(responseClient != null){
            CookieSet(response, responseClient);
            return responseClient.getId();
        } else {
            userMapper.signup(socialLogin.getName(), socialLogin.getEmail(), socialLogin.getPassword(), "", socialLogin.getImage(), true);
            ResponseClient responseClient1 = userMapper.findByEmail(socialLogin.getEmail(), true);
            CookieSet(response, responseClient1);
            return responseClient1.getId();
        }
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
