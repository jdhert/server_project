package com.kitri.web_project.mappers;

import com.kitri.web_project.dto.comment.CommentDto;
import com.kitri.web_project.dto.comment.RequestComment;
import com.kitri.web_project.dto.comment.RequestReplyComment;
import org.apache.ibatis.annotations.Mapper;
import org.eclipse.tags.shaded.org.apache.regexp.RE;

import java.util.List;

@Mapper
public interface CommentMapper {
//    List<CommentDto> getComments(long id);
    List<CommentDto> getTopLevelComments(long id);
    List<CommentDto> getChildComments(long id, long parentCommentId);
    List<CommentDto> getMyComments(long id);

    void addComment(RequestComment requestComment);
    void deleteComment(long commentId);
    void editComment(CommentDto commentDto);
    void addNewComment(RequestReplyComment requestReplyComment);
    void editReply(RequestReplyComment requestReplyComment);
    void deleteReply(long replyId);

    void incrementReplyLikeCount(long replyId);
    void decrementReplyLikeCount(long replyId);

    int childCount(long id);
}
