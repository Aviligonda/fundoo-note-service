package com.bridgelabz.fundoonoteservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
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
    @NotNull(message = " userId can't be Empty")
    private long userId;
    @NotNull(message = " labelId can't be Empty")
    @Pattern(regexp = "(\\w+[.+-]?)*@\\w+(\\.+[a-zA-Z]{2,4})*", message = "Invalid Email, Enter correct Email")
    private String emailId;
    private List<String> collaborator;

}
