package com.kitri.web_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {

    String name;
    String email;
    String imgPath;

    int boardCount;
    int commentCount;
    int diaryCount;
}
