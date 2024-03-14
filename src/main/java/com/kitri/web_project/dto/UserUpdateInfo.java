package com.kitri.web_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateInfo {
    Long userId;
    String name;
    String email;
    String imgPath;
    String address;
}
