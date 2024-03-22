package com.kitri.web_project.controller;

import com.kitri.web_project.dto.comment.CommentDto;
import com.kitri.web_project.dto.comment.RequestComment;
import com.kitri.web_project.dto.comment.RequestCommentLike;
import com.kitri.web_project.dto.comment.RequestReplyComment;
import com.kitri.web_project.mappers.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @GetMapping("/{id}")
    public List<CommentDto> getTopLevelComments(@PathVariable long id){
        return commentMapper.getTopLevelComments(id);
    }

    @GetMapping("/detailcomment")
    public List<CommentDto> getChildComments(@RequestParam long id, @RequestParam long parentCommentId){
       List<CommentDto> c = commentMapper.getChildComments(id, parentCommentId);
       for(CommentDto c1 : c){
           c1.setChild(commentMapper.childCount(c1.getId()));
       }
        return c;
    }

    @GetMapping("/mycomment/{id}")
    public List<CommentDto> getFreeComments(@PathVariable long id){
        return commentMapper.getMyComments(id);
    }

    @PostMapping
    public void addComment(@RequestBody RequestComment requestComment){
        commentMapper.addComment(requestComment);
    }

    @PutMapping("/{id}")
    public void editComment(@RequestBody CommentDto commentDto){
        commentMapper.editComment(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable long commentId){
       commentMapper.deleteComment(commentId);
    }

    @PostMapping("/{commentId}/replies")
    @Transactional
    public ResponseEntity<String> addReply(@PathVariable("commentId") long commentId, @RequestBody RequestReplyComment requestReplyComment){
        try {
            requestReplyComment.setParentCommentId(commentId);
            commentMapper.addNewComment(requestReplyComment);
            return ResponseEntity.ok("대댓글이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("대댓글 추가 중에 오류가 발생했습니다.");
        }
    }

    @PutMapping("/{replyId}/replies")
    public void editReply(@RequestBody RequestReplyComment requestReplyComment) {
        commentMapper.editReply(requestReplyComment);
    }

    @DeleteMapping("/{replyId}/replies")
    public void deleteReply(@PathVariable long replyId) {
        commentMapper.deleteReply(replyId);
    }

    @PutMapping("/{replyId}/replyLike")
    public void updateCommentLikeStatus(@PathVariable long replyId, @RequestParam boolean liked) {
        if(liked) {
            commentMapper.incrementReplyLikeCount(replyId);
        } else {
            commentMapper.decrementReplyLikeCount(replyId);
        }
    }

    @GetMapping("/child/{id}")
    public int countchild(@PathVariable long id){
        return commentMapper.childCount(id);
    }

    @PostMapping("/liked")
    public boolean commentLiked(@RequestBody RequestCommentLike requestCommentLike) {
        long userId = requestCommentLike.getUserId();
        long boardId = requestCommentLike.getBoardId();
        long commentId = requestCommentLike.getCommentId();
        try {
            if (requestCommentLike.getLiked()) {
                boolean recordExists = commentMapper.checkCommentLikeExists(userId, boardId, commentId);
                if (!recordExists) {
                    commentMapper.insertCommentLike(userId, boardId, commentId);
                }
                return true;
            } else {
                commentMapper.deleteCommentLike(userId, boardId, commentId);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/{id}/likeStatus")
    public boolean getCommentLikeStatus(@PathVariable("id") Long commentId) {
        try {
            boolean commentLiked = commentMapper.getCommentLikeStatus(commentId);
            return commentLiked;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @PostMapping("/replyLiked")
    public boolean replyLiked(@RequestBody RequestCommentLike requestCommentLike) {
        long userId = requestCommentLike.getUserId();
        long boardId = requestCommentLike.getBoardId();
        long commentId = requestCommentLike.getCommentId();
        boolean liked = requestCommentLike.getLiked();

        try {
            boolean alreadyLiked = commentMapper.checkReplyLikeExists(userId, boardId, commentId);
            if (alreadyLiked && liked) {
                return true;
            }
            else if (!alreadyLiked && !liked) {
                return false;
            }

            if (liked) {
                commentMapper.insertReplyLike(userId, boardId, commentId);
            } else {
                commentMapper.deleteReplyLike(userId, boardId, commentId);
            }
            return liked;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @GetMapping("/{id}/replyLikeStatus")
    public boolean getReplyLikeStatus(@PathVariable("id") Long replyId) {
        try {
            boolean replyLiked = commentMapper.getReplyLikeStatus(replyId);
            return replyLiked;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //게시글의 총 댓글 수
    @GetMapping("/totalCount")
    public int getTotalCommentCount(@RequestParam("boardId") long boardId) {
        return commentMapper.getTotalCommentCount(boardId);
    }


}




