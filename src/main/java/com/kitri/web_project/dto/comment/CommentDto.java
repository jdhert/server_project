package com.kitri.web_project.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    Long id;
    Long userId;
    String content;
    LocalDateTime createdAt;
    int likeCount;
    String name;
    Long parentCommentId;
    String imgPath;
}
