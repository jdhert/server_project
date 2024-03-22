package com.kitri.web_project.mappers;

import com.kitri.web_project.dto.board.BoardInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import com.kitri.web_project.dto.board.UpdateBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardInfo> getBoards(int offset ,int limit, int subject);
    List<BoardInfo> getPopularBoards(String interval);
    void uploadBoard(RequestBoard board);
    List<BoardInfo> getSearchBoards(String search, String type, String type1, int offset, int limit, int subject);
    void updateViewCount(Long id);

    BoardInfo getBoard(long id);
    void setTag(long id, String tag);

    List<String> getTags(long id);


    void updateBoard(UpdateBoard updateBoard);
  
    void deleteBoard(long id);

    void deleteTags(long id);

    List<BoardInfo> getMyBoards(long id, int subject);

    void incrementLikeCount(long postId);
    void decrementLikeCount(long postId);

    void incrementCommentLikeCount(long commentId);
    void decrementCommentLikeCount(long commentId);

    void setImage(long id, long boardId, String imagePath);

    List<String> getImages(long id);


}
