package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.LabelDTO;
import com.bridgelabz.fundoonoteservice.exception.UserException;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.repository.LabelRepository;
import com.bridgelabz.fundoonoteservice.repository.NoteServiceRepository;
import com.bridgelabz.fundoonoteservice.util.Response;
import com.bridgelabz.fundoonoteservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
}
