package com.kitri.web_project.controller;

import com.kitri.web_project.dto.pet.RequestPet;
import com.kitri.web_project.mybatis.mappers.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    @Autowired
    private PetMapper petMapper;

    @PostMapping
    public void addPet(@RequestBody RequestPet pet) {
        petMapper.addPet(pet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<String>> getImages(@PathVariable long id){
        List<String> images = petMapper.getImages(id);
        // URI Components Builder를 사용해 완전한 URL 생성
        List<String> imageUrls = images.stream()
                .map(path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/")
                        .path(path)
                        .toUriString())
                .map(encodedUrl -> URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8)) // URL 디코딩
                .collect(Collectors.toList());
        return ResponseEntity.ok(imageUrls);
    }


}
