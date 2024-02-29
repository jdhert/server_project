package com.kitri.web_project.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetInfo {
    Long id;
    String species;
    int age;
    Long weight;
    String specSpecies;
    boolean disease;
    boolean recogChip;
    String name;
    String gender;
}
