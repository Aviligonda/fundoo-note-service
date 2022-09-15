package com.bridgelabz.fundoonoteservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/*
 * Purpose : NoteServiceDTO fields are Used to Create and Update Note
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Data
public class NoteServiceDTO {
    @NotNull(message = " title can't be Empty")
    private String title;
    @NotNull(message = " description can't be Empty")
    private String description;
    @NotNull(message = " description can't be Empty")
    private String email;


}
