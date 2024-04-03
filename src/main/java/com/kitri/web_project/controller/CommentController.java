package com.kitri.web_project.controller;

import com.kitri.web_project.dto.board.RequestBoardLike;
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
        List<CommentDto> c = commentMapper.getTopLevelComments(id);
        return c;
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
    public List<CommentDto> getFreeComments(@PathVariable long id, @RequestParam int page, @RequestParam int itemsPerPage){
        int offset = (page - 1) * itemsPerPage;
        return commentMapper.getMyComments(id, offset, itemsPerPage);
    }

    @PostMapping
    public void addComment(@RequestBody RequestComment requestComment){
        commentMapper.addComment(requestComment);
        commentMapper.addCommentCount(requestComment.getId());
    }

    @PutMapping("/{id}")
    public void editComment(@RequestBody CommentDto commentDto){
        commentMapper.editComment(commentDto);
    }

    @DeleteMapping("/{commentId}/board/{boardId}")
    public void deleteComment(@PathVariable long commentId, @PathVariable long boardId){
        commentMapper.deleteComment(commentId);
        commentMapper.minusCommentCount(boardId);
    }


    @PostMapping("/{commentId}/replies")
    @Transactional
    public ResponseEntity<String> addReply(@PathVariable("commentId") long commentId, @RequestBody RequestReplyComment requestReplyComment){
        try {
            requestReplyComment.setParentCommentId(commentId);
            commentMapper.addNewComment(requestReplyComment);
            commentMapper.addCommentCount(requestReplyComment.getBoardId());
            return ResponseEntity.ok("대댓글이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("대댓글 추가 중에 오류가 발생했습니다.");
        }
    }

    @PutMapping("/{replyId}/replies")
    public void editReply(@RequestBody RequestReplyComment requestReplyComment) {
        commentMapper.editReply(requestReplyComment);
    }

    @Transactional
    @DeleteMapping("/{replyId}/replies/{boardId}")
    public void deleteReply(@PathVariable long replyId, @PathVariable long boardId) {
        commentMapper.deleteReply(replyId, boardId);
        commentMapper.minusCommentCount(boardId);
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
        try {
            boolean likeExists = commentMapper.checkCommentLikeExists(requestCommentLike); // 좋아요가 이미 존재하는지 확인
            if (likeExists) { // 좋아요가 이미 존재하는 경우
                commentMapper.deleteCommentLike(requestCommentLike); //좋아요 취소
                return false;
            } else { // 좋아요가 존재하지 않는 경우
                commentMapper.insertCommentLike(requestCommentLike); // 좋아요 추가
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @GetMapping("/{userId}/{commentId}/likeStatus")
    public boolean getCommentLikeStatus(@PathVariable("userId") Long userId, @PathVariable("commentId") Long commentId) {
        try {
            RequestCommentLike requestCommentLike = new RequestCommentLike();
            requestCommentLike.setUserId(userId);
            requestCommentLike.setCommentId(commentId);
            boolean commentLiked = commentMapper.getCommentLikeStatus(requestCommentLike);
            return commentLiked;

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




