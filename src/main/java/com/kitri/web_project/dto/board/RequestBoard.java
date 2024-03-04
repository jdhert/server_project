package com.kitri.web_project.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBoard {

    Long id;
    String title;
    String content;
    String category;
    String tag;
    int subject;
    String writer="";
}
