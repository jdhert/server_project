package com.kitri.web_project.mybatis.mappers;

import com.kitri.web_project.board.qna_board.dto.QnaInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<QnaInfo> getQnaBoards(int offset, int limit);

    QnaInfo getQnaBoard(int id);

    
}
