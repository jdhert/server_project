package com.kitri.web_project.dto.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLogin {
    String name;
    String password;
    String email;
    String image;
}
