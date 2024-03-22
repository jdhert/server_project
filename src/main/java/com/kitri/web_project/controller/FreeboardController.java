package com.kitri.web_project.controller;

import com.kitri.web_project.dto.BoardInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import com.kitri.web_project.dto.board.RequestBoardLike;
import com.kitri.web_project.dto.board.UpdateBoard;
import com.kitri.web_project.mybatis.mappers.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


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
        List<BoardInfo> bm = boardMapper.getBoards(offset, limit, 0);
        ImageSet(bm);
        return bm;
    }

    @PostMapping
    public void uploadBoard(@RequestBody RequestBoard board) {
        boardMapper.uploadBoard(board);
        for(String tag : board.getTags())
            boardMapper.setTag(board.getId(), tag);
        for(String image : board.getImages())
            boardMapper.setImage(board.getUserId(), board.getId(), image);
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
        List<BoardInfo> bm = boardMapper.getSearchBoards(search+'%', type, type1, offset, limit, subject);
        ImageSet(bm);
        return bm;
    }

    @GetMapping("/get/{boardId}")
    public BoardInfo getBoard(@PathVariable long boardId){
        return boardMapper.getBoard(boardId);
    }

    @GetMapping("/getTag/{boardId}")
    public List<String>getTagS(@PathVariable long boardId){
        return boardMapper.getTags(boardId);
    }

    @GetMapping("/getImage/{boardId}")
    public ResponseEntity<List<String>> getImages(@PathVariable long boardId){
        List<String> images = boardMapper.getImages(boardId);
        List<String> imageUrls = images.stream()
                .map(path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/")
                        .path(path)
                        .toUriString())
                .map(encodedUrl -> URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8)) // URL 디코딩
                .collect(Collectors.toList());
        return ResponseEntity.ok(imageUrls);
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
        ImageSet(results);
        return results;
    }

    @Value("${upload.path.routine}")
    private String uploadRootPath;

    @PostMapping(value = "/img", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<String> insertImages(@RequestPart(value = "image", required = false) MultipartFile[] imageFiles) {
        List<String> s = new ArrayList<>();;
        for (MultipartFile file : imageFiles) {
            // 파일 처리 로직 구현
            try {
                    //1.서버에 이미지파일을 저장, 이미지를 서버에 업로드
                    //1-a.파일 저장 위치를 지정하여 파일 객체에 포장
                    String originalFilename = file.getOriginalFilename();
                    //1-a-1.파일명이 중복되지 않도록 변경
                    String uploadFileName = UUID.randomUUID() + "_" + originalFilename;
                    //1-a-2.압럳, 폴더를 날짜별로 생성
                    String newUploadPath = uploadRootPath;
                    File uploadFile = new File(newUploadPath + File.separator + uploadFileName);
                    //1-b. 파일을 해당 경로에 업로드
                    file.transferTo(uploadFile);
//                String savePath
//                        = newUploadPath.substring(uploadRootPath.length());
//                String s1 = savePath + File.separator + uploadFileName;
                    String savePath = newUploadPath.substring(uploadRootPath.length()).replace("\\", "/"); // 역슬래시를 슬래시로 변환
                    String encodedFileName = URLEncoder.encode(uploadFileName, StandardCharsets.UTF_8); // 파일명 인코딩
                    String s1 = savePath + "/" + encodedFileName; // URL 생성
                    s.add(s1);
            } catch(IOException e){
                System.out.println(e);
            }
        }
        return s;
    }

    @PutMapping("/{postId}/like")
    public void updateLikeStatus(@PathVariable long postId, @RequestParam boolean liked) {
        if(liked) {
            boardMapper.incrementLikeCount(postId);
        } else {
            boardMapper.decrementLikeCount(postId);
        }
    }


    @PutMapping("/{commentId}/commentLike")
    public void updateCommentLikeStatus(@PathVariable long commentId, @RequestParam boolean liked) {
        if(liked) {
            boardMapper.incrementCommentLikeCount(commentId);
        } else {
            boardMapper.decrementCommentLikeCount(commentId);
        }
    }



    public void ImageSet(List<BoardInfo> bm){
        for(BoardInfo b : bm){
            ResponseEntity<List<String>> s = getImages(b.getId());
            if(!Objects.requireNonNull(s.getBody()).isEmpty())
                b.setImgPath(Objects.requireNonNull(s.getBody()).get(0));
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

    @PostMapping("/liked")
    public boolean boardLiked(@RequestBody RequestBoardLike requestBoardLike) {
        try {
            if (requestBoardLike.getLiked()) {
                boolean recordExists = boardMapper.checkLikeExists(requestBoardLike.getUserId(), requestBoardLike.getBoardId());
                if (!recordExists) {
                    boardMapper.insertLike(requestBoardLike.getUserId(), requestBoardLike.getBoardId());
                }
                return true;
            } else {
                boardMapper.deleteLike(requestBoardLike.getUserId(), requestBoardLike.getBoardId());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/{id}/likeStatus")
    public boolean getPostLikeStatus(@PathVariable("id") Long postId) {
        try {
            boolean postLiked = boardMapper.getPostLikeStatus(postId);
            return postLiked;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



}
