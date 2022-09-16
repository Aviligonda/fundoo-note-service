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
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        Long userId = tokenUtil.decodeToken(token);
        if (isUserPresent.getStatusCode() == 200) {
            LabelModel labelModel = new LabelModel(labelDTO);
            labelModel.setRegisterDate(LocalDateTime.now());
            labelModel.setUserId(userId);
            labelRepository.save(labelModel);
            return new Response(200, "Success", labelModel);
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Delete Label Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id,token
     * */
    @Override
    public Response delete(Long id, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        Long userId = tokenUtil.decodeToken(token);
        if (isUserPresent.getStatusCode() == 200) {
            Optional<LabelModel> isLabelPresent = labelRepository.findById(id);
            if (isLabelPresent.isPresent()) {
                if (isLabelPresent.get().getUserId() == userId) {
                    labelRepository.delete(isLabelPresent.get());
                    return new Response(200, "Success", isLabelPresent.get());
                }
                throw new UserException(400, "No Label Found with this UserId");
            }
            throw new UserException(400, "No Label Found with this Id");
        }
        throw new UserException(400, "Token Wrong");
    }
    /*
     * Purpose : Implement the Logic of Get All label Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     * */

    @Override
    public List<LabelModel> getAllLabels(String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        Long userId = tokenUtil.decodeToken(token);
        if (isUserPresent.getStatusCode() == 200) {
            List<LabelModel> isLabelPresent = labelRepository.findAllByUserId(userId);
            if (isLabelPresent.size() > 0) {
                return isLabelPresent;
            }
            throw new UserException(400, "No Labels Found With this UserId");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Update label Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  labelDTO,token,id
     * */
    @Override
    public Response update(Long id, LabelDTO labelDTO, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        Long userId = tokenUtil.decodeToken(token);
        if (isUserPresent.getStatusCode() == 200) {
            Optional<LabelModel> isLabelPresent = labelRepository.findById(id);
            if (isLabelPresent.isPresent()) {
                if (isLabelPresent.get().getUserId() == userId) {
                    isLabelPresent.get().setLabelName(labelDTO.getLabelName());
                    isLabelPresent.get().setUpdateDate(LocalDateTime.now());
                    return new Response(200, "Success", isLabelPresent.get());
                }
                throw new UserException(400, "Not Found with this UserId");
            }
            throw new UserException(400, "Not Found with this Id");
        }
        throw new UserException(400, "Token Wrong");
    }
}
