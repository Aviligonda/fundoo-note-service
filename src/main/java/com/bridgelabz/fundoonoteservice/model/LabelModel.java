package com.bridgelabz.fundoonoteservice.model;

import com.bridgelabz.fundoonoteservice.dto.LabelDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "label")
public class LabelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String labelName;
    private Long userId;
    private Long noteId;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

    @ManyToMany(mappedBy = "labelList")
    private List<NoteServiceModel> notes;

    public LabelModel(LabelDTO labelDTO) {
        labelName = labelDTO.getLabelName();
    }

    public LabelModel() {
    }
}
