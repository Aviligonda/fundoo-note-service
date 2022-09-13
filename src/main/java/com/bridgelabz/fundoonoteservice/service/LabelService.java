package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.LabelDTO;
import com.bridgelabz.fundoonoteservice.exception.UserException;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.model.NoteServiceModel;
import com.bridgelabz.fundoonoteservice.repository.LabelRepository;
import com.bridgelabz.fundoonoteservice.repository.NoteServiceRepository;
import com.bridgelabz.fundoonoteservice.util.Response;
import com.bridgelabz.fundoonoteservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Purpose : LabelService to Implement the Business Logic
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Service
public class LabelService implements ILabelService {
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    NoteServiceRepository noteServiceRepository;

    /*
     * Purpose : Implement the Logic of Creating Label Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  labelDTo,token
     * */
    @Override
    public Response createLabel(LabelDTO labelDTO, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            LabelModel labelModel = new LabelModel(labelDTO);
            labelModel.setRegisterDate(LocalDateTime.now());
            labelRepository.save(labelModel);
            return new Response(200, "Success", labelModel);
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Delete Label Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id,token
     * */
    @Override
    public Response delete(Long id, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<LabelModel> isLabelPresent = labelRepository.findById(id);
            if (isLabelPresent.isPresent()) {
                labelRepository.delete(isLabelPresent.get());
                return new Response(200, "Success", isLabelPresent.get());
            } else {
                throw new UserException(400, "Not Found with this Id");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }
    /*
     * Purpose : Implement the Logic of Get All label Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     * */

    @Override
    public List<LabelModel> getAllLabels(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<LabelModel> isLabelPresent = labelRepository.findAll();
            if (isLabelPresent.size() > 0) {
                return isLabelPresent;
            } else {
                throw new UserException(400, "No Labels Found");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Update label Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  labelDTO,token,id
     * */
    @Override
    public Response update(Long id, LabelDTO labelDTO, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<LabelModel> isLabelPresent = labelRepository.findById(id);
            if (isLabelPresent.isPresent()) {
                isLabelPresent.get().setLabelName(labelDTO.getLabelName());
                isLabelPresent.get().setUpdateDate(LocalDateTime.now());
                return new Response(200, "Success", isLabelPresent.get());
            } else {
                throw new UserException(400, "Not Found with this Id");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Add Note List
     * @author : Aviligonda Sreenivasulu
     * @Param :  labelId,noteId,token
     * */
    @Override
    public Response addNotes(Long labelId, List<Long> noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<NoteServiceModel> noteServiceModelList = new ArrayList<>();
            noteId.stream().forEach(note -> {
                Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(note);
                if (isNotePresent.isPresent()) {
                    noteServiceModelList.add(isNotePresent.get());
                }
            });
            Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
            if (isLabelPresent.isPresent()) {
                isLabelPresent.get().setNotes(noteServiceModelList);
                labelRepository.save(isLabelPresent.get());
                return new Response(200, "Success", isLabelPresent.get());
            } else {
                throw new UserException(400, "Label Id is Not Present");
            }
        }
        throw new UserException(400, "Token is Wrong");
    }
}
