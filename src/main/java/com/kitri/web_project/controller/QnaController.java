package com.kitri.web_project.controller;

import com.kitri.web_project.dto.QnaInfo;
import com.kitri.web_project.dto.board.RequestBoard;
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
    public List<QnaInfo> get(@PathVariable int page){
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

        List<QnaInfo> qnaInfos = boardMapper.getQnaBoards(offset, limit);
        if(qnaInfos.isEmpty())
            return null;
        else return qnaInfos;
    }

    @GetMapping("/detail/{id}")
    public QnaInfo getInfo(@PathVariable int id){
        return boardMapper.getQnaBoard(id);
    }

    @PostMapping
    public void uploadBoard(@RequestBody RequestBoard board){
        boardMapper.uploadQna(board);
    }


}
