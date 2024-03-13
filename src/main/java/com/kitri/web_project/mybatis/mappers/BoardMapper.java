package com.kitri.web_project.mybatis.mappers;

import com.kitri.web_project.dto.BoardInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import com.kitri.web_project.dto.board.TagSet;
import com.kitri.web_project.dto.board.UpdateBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardInfo> getBoards(int offset ,int limit, int subject);
    List<BoardInfo> getPopularBoards(String interval);
    void uploadBoard(RequestBoard board);
    List<BoardInfo> getSearchBoards(String search, String type, String type1, int offset, int limit, int subject);

    BoardInfo getBoard(long id);
    void setTag(long id, String tag);

    List<String> getTags(long id);


    void updateBoard(UpdateBoard updateBoard);
  
    void deleteBoard(long id);

    void deleteTags(long id);

    List<BoardInfo> getMyBoards(long id, int subject, int offset, int limit);

    void incrementLikeCount(long postId);
    void decrementLikeCount(long postId);

    void incrementCommentLikeCount(long commentId);
    void decrementCommentLikeCount(long commentId);


//    void incrementLikeCountqna(long postId);
//    void decrementLikeCountqna(long postId);
//
//    void incrementCommentLikeCountqna(long commentId);
//    void decrementCommentLikeCountqna(long commentId);
}
