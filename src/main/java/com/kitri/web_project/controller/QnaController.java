package com.kitri.web_project.controller;

import com.kitri.web_project.dto.board.BoardInfo;
import com.kitri.web_project.dto.board.UpdateBoard;
import com.kitri.web_project.mybatis.mappers.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
public class QnaController {

    @Autowired
    BoardMapper boardMapper;

    @GetMapping("/{page}")
    public List<BoardInfo> get(@PathVariable int page){
        int maxPage=4;
        int offset;
        int limit=0;
        if(page == 1) {
            offset = 0;
            limit = 4;
        }
        else offset = (page-1) * maxPage + (page-2) * 4;
        if(limit != 4)
            limit = 8;
        return boardMapper.getBoards(offset, limit, 1);
    }
    @GetMapping("/get/{boardId}")
    public BoardInfo getBoard(@PathVariable long boardId){
        return boardMapper.getBoard(boardId);
    }

    @PutMapping
    public void updateBoard(@RequestBody UpdateBoard updateBoard){
        boardMapper.deleteTags(updateBoard.getBoardId());
        boardMapper.updateBoard(updateBoard);
        for(String tag : updateBoard.getTags())
            boardMapper.setTag(updateBoard.getBoardId(), tag);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable long boardId){
        boardMapper.deleteBoard(boardId);
    }

    @PutMapping("/{postId}/like")
    public void updateLikeStatus(@PathVariable long postId, @RequestParam boolean liked) {
        if(liked) {
            boardMapper.incrementLikeCount(postId);
        } else {
            boardMapper.decrementLikeCount(postId);
        }
    }

    @PutMapping("/{commentId}/commentLike")
    public void updateCommentLikeStatus(@PathVariable long commentId, @RequestParam boolean liked) {
        if(liked) {
            boardMapper.incrementCommentLikeCount(commentId);
        } else {
            boardMapper.decrementCommentLikeCount(commentId);
        }
    }
}
