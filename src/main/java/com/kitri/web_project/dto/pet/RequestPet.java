package com.kitri.web_project.dto.pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPet {
    Long id;
    Long userId;
    String petImg;
    String petName;
    int petAge;
    Long petWeight;
    String species;
    String spec_species;
    String petGender;
    boolean petDisease;
    boolean petRecog_chip;
    String petColor;
}
