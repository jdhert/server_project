package com.kitri.web_project.service;

import com.kitri.web_project.dto.board.BoardInfo;
import com.kitri.web_project.mappers.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardMapper boardMapper;

    public List<BoardInfo> boardInfos(long id, int page){
        List<BoardInfo> b =boardMapper.getMyLike(id, (page-1) * 10);
        return b;
    }
}
