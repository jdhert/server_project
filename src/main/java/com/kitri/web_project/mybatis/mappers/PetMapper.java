package com.kitri.web_project.mybatis.mappers;

import com.kitri.web_project.dto.PetInfo;
import com.kitri.web_project.dto.board.RequestBoard;
import com.kitri.web_project.dto.pet.RequestPet;
import com.kitri.web_project.dto.pet.UpdatePet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetMapper {
    void addPet(RequestPet pet);
    List<String> getImages(long id);
    List<String> getPetImages(long petId);
    PetInfo getPet(long petId);
    void deletePet(long petId);
    void updatePet(UpdatePet pet);
    void updatePet2(UpdatePet pet);
}
