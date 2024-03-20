package com.kitri.web_project.controller;

import com.kitri.web_project.dto.*;
import com.kitri.web_project.dto.diary.PetCalendar;
import com.kitri.web_project.dto.diary.RequestDiary;
import com.kitri.web_project.mybatis.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/myinfo")
public class UserInfoController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/{id}")
    public UserInfo getInfo(@PathVariable String id) {
        long id1 = Long.parseLong(id);
        return userMapper.findById(id1);
    }

    @PutMapping
    public void updateUser(@RequestBody UserUpdateInfo userUpdateInfo) {

        String imgPath = userMapper.getUserImages(userUpdateInfo.getUserId());
        userMapper.updateUser(userUpdateInfo);

        String fullPath = "/D:/imageStore" + imgPath;
        File file = new File(fullPath);
        if (file.exists()) {
            try {
                if (file.delete()) {
                    System.out.println("Success: Image deleted");
                } else {
                    System.out.println("Failed: Image could not be deleted");
                }
            } catch (SecurityException e) {
                System.out.println("Failed: Security Exception occurred while deleting image");
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed: Image not found at path " + fullPath);
        }
    }

    @PostMapping("/updatepw/{id}")
    public void updateNewPassword(@PathVariable Long id, @RequestBody Map<String, String> passwords) {
        String currentPassword = passwords.get("currentPassword");
        String newPassword = passwords.get("newPassword");

        List<Object> params = Arrays.asList(id, currentPassword, newPassword);
        userMapper.updateNewPassword(params);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {

        String imgPath = userMapper.getUserImages(id);
        userMapper.deleteUser(id);

        String fullPath = "/D:/imageStore" + imgPath;
        File file = new File(fullPath);
        if (file.exists()) {
            try {
                if (file.delete()) {
                    System.out.println("Success: Image deleted");
                } else {
                    System.out.println("Failed: Image could not be deleted");
                }
            } catch (SecurityException e) {
                System.out.println("Failed: Security Exception occurred while deleting image");
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed: Image not found at path " + fullPath);
        }
    }

    @GetMapping("/img/{id}")
    public ResponseEntity<String> getUserImages(@PathVariable long id){
        String images = userMapper.getUserImages(id);
        if(images==null)
            return null;

        if (images.startsWith("http")) {
            return ResponseEntity.ok(images);
        } else {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(images)
                    .toUriString();
            imageUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8); // URL 디코딩
            return ResponseEntity.ok(imageUrl);
        }
    }

    @GetMapping("/pet/{id}")
    public List<PetInfo> getPet(@PathVariable String id) {
        long id1 = Long.parseLong(id);
        return userMapper.getPets(id1);
    }

    @GetMapping("/diary/{id}")
    public List<DiaryInfo> getDiary(@PathVariable String id) {
        long id1 = Long.parseLong(id);
        return userMapper.getDiary(id1);
    }
    @PostMapping
    public void adddiary(@RequestBody RequestDiary diaryInfo) {
        userMapper.save(diaryInfo);
    }
    //다이어리 등록 매핑

    @GetMapping("/select/{id}")
    public List<RequestDiary>requestDiaries(@PathVariable long id){
//        long id1 = Long.parseLong(id);
        return userMapper.petDiary(id);
    }
    //다이어리 미리보기 매핑

    @DeleteMapping("/{diaryId}")
    public void RequestDiary(@PathVariable long diaryId) {
        userMapper.deleteDiary(diaryId);
        System.out.println("success");
    }

    @GetMapping("/calendar/{id}")
    public List<PetCalendar>petCalendars(@PathVariable long id){
        return userMapper.petCalendar(id);
    }

    @GetMapping("/update/{id}") //다이어리 수정 불러오기
    public List<RequestDiary>requestDiaries1(@PathVariable long id){
        return userMapper.UpdateDiary(id);
    }

    @PutMapping ("/edit/{id}") //다이어리 수정
    public void editDiary(@RequestBody RequestDiary requestDiary){
        userMapper.editDiary(requestDiary);
    }


    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/findpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        // 사용자가 제공한 이메일로 사용자를 찾습니다.
        ResponseClient user = userMapper.findByEmail(email);
        if (user == null) {
            // 사용자가 존재하지 않는 경우에는 오류 응답을 반환합니다.
            return ResponseEntity.badRequest().body("User with provided email not found");
        }

        // 새로운 임시 비밀번호 생성
        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(newPassword);

        // DB에 새로운 비밀번호 저장
        userMapper.updatePassword(user);

        // 이메일 보내기
        sendEmail(user.getEmail(), "Your new password", "Your new password is: " + newPassword);

        return ResponseEntity.ok("New password sent to your email");
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
