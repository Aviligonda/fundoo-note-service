package com.bridgelabz.fundoonoteservice.model;

import com.bridgelabz.fundoonoteservice.dto.NoteServiceDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
/*
 * Purpose : NoteServiceModel Are Used Create A table and connection to Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Entity
@Table(name = "noteService")
@Data
public class NoteServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private long userId;
    private boolean trash;
    private boolean isArchive;
    private boolean pin;
    private Long labelId;
    private String emailId;
    private String color;
    private LocalDateTime reminderTime;
    @ElementCollection(targetClass=String.class)
    private List<String> collaborator;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    public NoteServiceModel(NoteServiceDTO noteServiceDTO) {
        this.title = noteServiceDTO.getTitle();
        this.description = noteServiceDTO.getDescription();
        this.userId = noteServiceDTO.getUserId();
        this.emailId = noteServiceDTO.getEmailId();
        this.collaborator = noteServiceDTO.getCollaborator();
    }



    public NoteServiceModel() {
    }
}
