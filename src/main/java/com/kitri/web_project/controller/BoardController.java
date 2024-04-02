package com.kitri.web_project.controller;

import com.kitri.web_project.dto.board.BoardInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import com.kitri.web_project.dto.board.RequestBoardLike;
import com.kitri.web_project.dto.board.UpdateBoard;
import com.kitri.web_project.dto.diary.DiaryImgDto;
import com.kitri.web_project.dto.diary.RequestDiary;
import com.kitri.web_project.dto.image.RequestImage;
import com.kitri.web_project.mappers.BoardMapper;
import com.kitri.web_project.service.BoardService;
import org.eclipse.tags.shaded.org.apache.regexp.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
public class BoardController {

    @Autowired
    BoardMapper boardMapper;


    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/{page}")
    public List<BoardInfo> get(@PathVariable int page, @RequestParam int subject) {
        int maxPage = 8;
        int offset;
        int limit;
        offset = (page - 1) * maxPage;
        limit = 8;
        List<BoardInfo> bm = boardMapper.getBoards(offset, limit, subject);
        ImageSet(bm);
        return bm;
    }

    @PostMapping
    public void uploadBoard(@RequestBody RequestBoard board) {
        boardMapper.uploadBoard(board);
        for(String tag : board.getTags())
            boardMapper.setTag(board.getId(), tag);
        if(board.getImages()!=null) {
            for (String image : board.getImages())
                boardMapper.setImage(board.getUserId(), board.getId(), image);
        }
    }

    @GetMapping("/search/{page}")
    public List<BoardInfo> search(@RequestParam String search, @RequestParam String type, @RequestParam String type1, @RequestParam int subject, @PathVariable int page){
        int maxPage=8;
        int offset;
        int limit;

        offset = (page - 1) * maxPage;
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
        for(String img : updateBoard.getImg())
            boardMapper.setImage(updateBoard.getUserId(), updateBoard.getBoardId(), img);
    }


    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable long id){
        boardMapper.deleteBoard(id);
        boardMapper.deleteAllImgs(id);
    }

    @GetMapping("/getMyBoard/{id}")
    public List<BoardInfo> getMyBoard(@RequestParam int subject, @RequestParam int page, @RequestParam int itemsPerPage, @PathVariable long id) {
        int offset = (page - 1) * itemsPerPage;
        return boardMapper.getMyBoards(id, subject, offset, itemsPerPage);
    }

    @GetMapping("/popular")
    public List<BoardInfo> getPopularBoard(int subject) {
        return boardService.popularBoards(subject);
    }

    @Value("${upload.path.routine}")
    private String uploadRootPath;

    @PostMapping(value = "/img", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<String> insertImages(@RequestPart(value = "image", required = false) MultipartFile[] imageFiles) {
        List<String> s = new ArrayList<>();;
        for (MultipartFile file : imageFiles) {
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

    //수정 페이지 이미지 불러오기
    @GetMapping("/BoardEditImages/{id}")
    public ResponseEntity<List<RequestImage>> getBoardImage(@PathVariable long id) {
        List<RequestImage> imageList = boardMapper.getBoardImage(id);
        List<String> images = new ArrayList<>();

        for(RequestImage bs : imageList){
            images.add(bs.getImagePath());
        }

        List<String> imageUrls = images.stream()
                .map(path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/")
                        .path(path)
                        .toUriString())
                .map(encodedUrl -> URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8)) // URL 디코딩
                .toList();
        for (int i = 0; i < imageUrls.size(); i++) {
            imageList.get(i).setImagePath(imageUrls.get(i));
        }

        ResponseEntity<List<RequestImage>> l = ResponseEntity.ok(imageList);
        return ResponseEntity.ok(imageList);
    }

    //수정 이미지 삭제
    @DeleteMapping("/delete")
    public void deleteImageById(@RequestParam("ids") Long[] ids) {
        for (Long id : ids) {
            boardMapper.deleteImageById(id);
        }
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
    public ResponseEntity<?> updateViewCount(@PathVariable("id") long id) {
        try {
            boardMapper.updateViewCount(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping("/view/{id}")
//    public long totalViewCount(@PathVariable("id") Long id) {
//        return boardMapper.totalViewCount(id);
//    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Long> totalViewCount(@PathVariable("id") long id) {
        Long viewCount = boardMapper.totalViewCount(id);
        return ResponseEntity.ok().body(viewCount);
    }

//    @PostMapping("/liked")
//    public boolean boardLiked(@RequestBody RequestBoardLike requestBoardLike) {
//        try {
//            if (requestBoardLike.getLiked()) {
//                boolean recordExists = boardMapper.checkLikeExists(requestBoardLike.getUserId(), requestBoardLike.getBoardId());
//                if (!recordExists) {
//                    boardMapper.insertLike(requestBoardLike.getUserId(), requestBoardLike.getBoardId());
//                }
//                return true;
//            } else {
//                boardMapper.deleteLike(requestBoardLike.getUserId(), requestBoardLike.getBoardId());
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    @PostMapping("/liked")
    public boolean boardLiked(@RequestBody RequestBoardLike requestBoardLike) {
        try {
            boolean likeExists = boardMapper.checkLikeExists(requestBoardLike); // 좋아요가 이미 존재하는지 확인
            if (likeExists) { // 좋아요가 이미 존재하는 경우
                boardMapper.deleteLike(requestBoardLike); //좋아요 취소
                return false;
            } else { // 좋아요가 존재하지 않는 경우
                boardMapper.insertLike(requestBoardLike); // 좋아요 추가
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


//    @GetMapping("/{id}/likeStatus")
//    public boolean getPostLikeStatus(@PathVariable("id") Long postId) {
//        try {
//            boolean postLiked = boardMapper.getPostLikeStatus(postId);
//            return postLiked;
//        } catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }

    @GetMapping("/{userId}/{boardId}/likeStatus")
    public boolean getPostLikeStatus(@PathVariable("userId") Long userId, @PathVariable("boardId") Long boardId) {
        try {
            RequestBoardLike requestBoardLike = new RequestBoardLike();
            requestBoardLike.setUserId(userId);
            requestBoardLike.setBoardId(boardId);
            boolean postLiked = boardMapper.getPostLikeStatus(requestBoardLike);
            return postLiked;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    @GetMapping("/getMyLike/{id}")
    public List<BoardInfo> getMyLike(@PathVariable long id, int page){
        return boardService.boardInfos(id, page);
    }
}
