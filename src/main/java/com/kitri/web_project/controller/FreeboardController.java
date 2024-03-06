package com.kitri.web_project.controller;

import com.kitri.web_project.dto.BoardInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import com.kitri.web_project.dto.board.TagSet;
import com.kitri.web_project.dto.board.UpdateBoard;
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
    public List<BoardInfo> get(@PathVariable int page) {
        int maxPage = 8;
        int offset;
        int limit;
        if (page == 1)
            offset = 0;
        else offset = (page - 1) * maxPage + (page - 2) * (maxPage / 2);
        limit = 8;
        return boardMapper.getBoards(offset, limit, 0);
    }

    @PostMapping
    public void uploadBoard(@RequestBody RequestBoard board) {
        List<String> tags  = board.getTags();
        boardMapper.uploadBoard(board);
        for(String tag : tags)
            boardMapper.setTag(board.getId(), tag);

    }

    @GetMapping("/search/{page}")
    public List<BoardInfo> search(@RequestParam String search, @RequestParam String type, @PathVariable int page){
        int maxPage=8;
        int offset;
        int limit;
        if(page == 1)
            offset = 0;
        else offset = (page - 1) * maxPage + (page - 2) * (maxPage / 2);
        limit = 8;
        return boardMapper.getSearchBoards(search+"%", type, offset, limit, 0);
    }

    @GetMapping("/get/{boardId}")
    public BoardInfo getBoard(@PathVariable long boardId){
        return boardMapper.getBoard(boardId);
    }

    @GetMapping("/getTag/{boardId}")
    public List<String>getTagS(@PathVariable long boardId){
        return boardMapper.getTags(boardId);
    }


    @PutMapping
    public void updateBoard(@RequestBody UpdateBoard updateBoard){
        boardMapper.updateBoard(updateBoard);
    }
}
