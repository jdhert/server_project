package com.kitri.web_project.dto.board;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class RequestBoard {
    Long userId;
    Long id;
    String title;
    String content;
    String category;
    List<String> tags;
    int subject;
    List<String> images;

}
