package com.kitri.web_project.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardInfo {
    @NotEmpty
    Long id;
    @NotEmpty
    String title;
    @NotEmpty
    String writer;
    @NotEmpty
    LocalDateTime createdAt;
    @NotEmpty
    Long userId;



    String content;
    int viewCount;
    int commentCount;
    int likeCount;
    String category;
    String tag;

    String imgPath;
    String userImg;

    int totalRowCount;

}
