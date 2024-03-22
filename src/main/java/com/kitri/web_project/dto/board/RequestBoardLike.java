package com.kitri.web_project.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBoardLike {
    Long userId;
    Long boardId;
    Boolean liked;
}
