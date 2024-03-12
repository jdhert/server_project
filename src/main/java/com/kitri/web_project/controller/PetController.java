package com.kitri.web_project.controller;

import com.kitri.web_project.dto.pet.RequestPet;
import com.kitri.web_project.mybatis.mappers.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PetController {

    @Autowired
    private PetMapper petMapper;

    @PostMapping("/api/pet")
    public void addPet(@RequestBody RequestPet pet) {
        petMapper.addPet(pet);
    }

    @PostMapping(value = "/img", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public void insertImages(@RequestPart(value = "image", required = false) MultipartFile[] imageFiles) {
        for (MultipartFile file : imageFiles) {
            System.out.println(file.getOriginalFilename());
            // 파일 처리 로직 구현
        }
    }
}
