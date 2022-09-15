package com.bridgelabz.fundoonoteservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/*
 * Purpose : LabelDTO fields are Used to Create and Update Label
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Data
public class LabelDTO {
    @NotNull(message = "labelName Can't be null")
    private String labelName;

}
