package com.kitri.web_project.mybatis.mappers;

import com.kitri.web_project.dto.BoardInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardInfo> getBoards(int offset ,int limit, int subject);
    void uploadBoard(RequestBoard board);
    List<BoardInfo> getSearchBoards(String search, String type, int offset, int limit, int subject);
}
