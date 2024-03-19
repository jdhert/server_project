package com.kitri.web_project.controller;

import com.kitri.web_project.dto.comment.CommentDto;
import com.kitri.web_project.dto.comment.RequestComment;
import com.kitri.web_project.mybatis.mappers.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return commentMapper.getChildComments(id, parentCommentId);
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
}
