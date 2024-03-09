package com.kitri.web_project.dto.board;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateBoard {
    Long boardId;
    String title;
    String content;
    String category;
    List<String> tags;
}
