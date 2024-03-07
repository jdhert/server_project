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
    public List<CommentDto> getComments(@PathVariable long id){
        return commentMapper.getComments(id);
    }

    @GetMapping("/mycomment/{id}")
    public List<CommentDto> getMyComments(@PathVariable long id){
        return commentMapper.getMyComments(id);
    }


    @PostMapping
    public void addComment(@RequestBody RequestComment requestComment){
        commentMapper.addComment(requestComment);
    }
}
