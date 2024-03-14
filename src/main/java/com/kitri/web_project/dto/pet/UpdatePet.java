package com.kitri.web_project.dto.pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePet {
    Long petId;
    String petImg;
    String petName;
    int petAge;
    Long petWeight;
    String spec_species;
    boolean petDisease;
    boolean petRecog_chip;
}
