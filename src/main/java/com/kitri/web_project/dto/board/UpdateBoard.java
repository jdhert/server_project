package com.kitri.web_project.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBoard {
    Long boardId;
    String title;
    String content;
    String category;
}
