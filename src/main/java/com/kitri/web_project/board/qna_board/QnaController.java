package com.kitri.web_project.board.qna_board;

import com.kitri.web_project.board.qna_board.dto.QnaInfo;
import com.kitri.web_project.mybatis.mappers.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
public class QnaController {

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
        limit = page * maxPage + (page- 1) * (maxPage / 2);
        List<QnaInfo> qnaInfos = boardMapper.getQnaBoards(offset, limit);
        if(qnaInfos.isEmpty())
            return null;
        else return qnaInfos;
    }

    @GetMapping("/detail/{id}")
    public QnaInfo getInfo(@PathVariable int id){
        return boardMapper.getQnaBoard(id);
    }


}
