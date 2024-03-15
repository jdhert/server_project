package com.kitri.web_project.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestReplyComment {
    Long commentId;
    Long boardId;
    Long userId;
    String content;
    Long parentCommentId;
}
