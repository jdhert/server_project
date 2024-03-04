package com.kitri.web_project.mybatis.mappers;

import com.kitri.web_project.dto.QnaInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<QnaInfo> getQnaBoards(int offset, int limit);

    List<QnaInfo> getFreeBoards(int offset, int limit);
    QnaInfo getQnaBoard(int id);

    void uploadQna(RequestBoard board);

    List<QnaInfo> getWritFreeBoards(String search, int offset, int limit);

    List<QnaInfo> getContFreeBoards(String search, int offset, int limit);

    List<QnaInfo> getTagFreeBoards(String search, int offset, int limit);
}
