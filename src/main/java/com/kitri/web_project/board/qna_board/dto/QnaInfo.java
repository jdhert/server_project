package com.kitri.web_project.board.qna_board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QnaInfo {
    @NotEmpty
    Long id;
    @NotEmpty
    String title;
    @NotEmpty
    String writer;
    @NotEmpty
    LocalDateTime createdAt;

    String content;
    int viewCount;
    int commentCount;
    int likeCount;
    String category;

    int totalRowCount;

}
