package com.bridgelabz.fundoonoteservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
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
    @Pattern(regexp = "(\\w+[.+-]?)*@\\w+(\\.+[a-zA-Z]{2,4})*", message = "Invalid Email, Enter correct Email")
    private String emailId;
    @NotNull(message = " collaborator can't be Empty")
    private List<String> collaborator;

}
