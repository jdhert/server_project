package com.kitri.web_project.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCommentLike {
    Long userId;
    Long boardId;
    Long commentId;
    Boolean liked;
}
