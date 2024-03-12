package com.kitri.web_project.controller;

import com.kitri.web_project.dto.BoardInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import com.kitri.web_project.dto.board.TagSet;
import com.kitri.web_project.dto.board.UpdateBoard;
import com.kitri.web_project.mybatis.mappers.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        offset = (page - 1) * maxPage;
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
    public List<BoardInfo> search(@RequestParam String search, @RequestParam String type, @RequestParam String type1, @RequestParam int subject, @PathVariable int page){
        int maxPage=8;
        int offset;
        int limit;
        if(subject == 1) {
            if (page == 1)
                offset = 0;
            else offset = (page - 1) * maxPage + (page - 2) * (maxPage / 2);
        } else offset = (page - 1) * maxPage;
        limit = 8;
        return boardMapper.getSearchBoards(search+'%', type, type1, offset, limit, subject);
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
        boardMapper.deleteTags(updateBoard.getBoardId());
        boardMapper.updateBoard(updateBoard);
        for(String tag : updateBoard.getTags())
            boardMapper.setTag(updateBoard.getBoardId(), tag);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable long boardId){
        boardMapper.deleteBoard(boardId);
    }

    @GetMapping("/getMyBoard/{id}")
    public List<BoardInfo> getMyBoard(@RequestParam int subject, @RequestParam int page, @PathVariable long id) {
        int maxPage=10;
        int offset;
        int limit;
        offset = (page - 1) * maxPage;
        limit = 10;
        return boardMapper.getMyBoards(id, subject, offset, limit);
    }
    @GetMapping("/popular")
    public List<BoardInfo> getPopularBoard() {
        String[] intervals = {"7 DAY", "1 MONTH", "1 YEAR", "100 YEAR"};
        List<BoardInfo> results = null;
        for (String interval : intervals) {
            results = boardMapper.getPopularBoards(interval);
            if (!results.isEmpty()) {
                break;
            }
        }
        return results;
    }
    @PostMapping(value = "/{id}", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public void insertImages(@RequestPart(value = "image", required = false) MultipartFile[] imageFiles, @PathVariable long id) {
        for (MultipartFile file : imageFiles) {
            System.out.println(file.getOriginalFilename());
            // 파일 처리 로직 구현
        }
    }

    @PutMapping("/{postId}/like")
    public void updateLikeStatus(@PathVariable long postId,  @RequestParam boolean liked){
        if(liked) {
            //좋아요 추가, like_count 증가
            boardMapper.incrementLikeCount(postId);
        } else {
            //좋아요 취소, like_count 감소
            boardMapper.decrementLikeCount(postId);
        }
    }

    @PutMapping("/view/{id}")
    public ResponseEntity<?> updateViewCount(@PathVariable("id") Long id) {
        try {
            boardMapper.updateViewCount(id); // boardMapper에 정의된 updateViewCount 메소드를 호출
            return new ResponseEntity<>(HttpStatus.OK); // 성공적으로 조회수를 업데이트한 경우, HTTP 200 상태 코드 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 예외 발생 시, HTTP 500 상태 코드 반환
        }
    }

}
