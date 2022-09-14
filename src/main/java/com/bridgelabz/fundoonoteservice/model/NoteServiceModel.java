package com.bridgelabz.fundoonoteservice.model;

import com.bridgelabz.fundoonoteservice.dto.NoteServiceDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String color;
    private LocalDateTime reminderTime;
    @ElementCollection(targetClass = String.class)
    private List<String> collaborator;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

//    @ManyToMany
//    @JoinTable(name = "note_service_label_list",
//            joinColumns = @JoinColumn(name = "noteId", referencedColumnName = "labelId"))
//    private List<LabelModel> labelList = new ArrayList<>();
//
//    public List<LabelModel> getLabelList() {
//        return labelList;
//    }
//
//    public void setLabelList(List<LabelModel> labelList) {
//        this.labelList = labelList;
//    }

    //    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private List<LabelModel> labelList;


    public NoteServiceModel(NoteServiceDTO noteServiceDTO) {
        this.title = noteServiceDTO.getTitle();
        this.description = noteServiceDTO.getDescription();
    }


    public NoteServiceModel() {
    }
}
