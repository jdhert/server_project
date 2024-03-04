package com.kitri.web_project.controller;

import com.kitri.web_project.dto.QnaInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import com.kitri.web_project.mybatis.mappers.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/free")
public class FreeboardController {

    @Autowired
    BoardMapper boardMapper;

    @GetMapping("/{page}")
    public List<QnaInfo> get(@PathVariable int page){
        int maxPage=8;
        int offset;
        int limit;
        if(page == 1)
            offset = 0;
        else offset = (page - 1) * maxPage + (page - 2) * (maxPage / 2);
        limit = 8;
        List<QnaInfo> qnaInfos = boardMapper.getFreeBoards(offset, limit);
        if(qnaInfos.isEmpty())
            return null;
        else return qnaInfos;
    }

    @PostMapping
    public void uploadBoard(@RequestBody RequestBoard board){
        boardMapper.uploadQna(board);
    }

    @GetMapping("/search/{page}")
    public List<QnaInfo> search(@RequestParam String search,@RequestParam String type, @PathVariable int page){
        int maxPage=8;
        int offset;
        int limit;
        if(page == 1)
            offset = 0;
        else offset = (page - 1) * maxPage + (page - 2) * (maxPage / 2);
        limit = 8;
        switch (type){
            case "writer":
                return boardMapper.getWritFreeBoards(search+"%", offset, limit);
            case "content":
                return boardMapper.getContFreeBoards(search+"%", offset, limit);
            case "tag":
                return boardMapper.getTagFreeBoards(search+"%", offset, limit);
        }
        return null;
    }
}
