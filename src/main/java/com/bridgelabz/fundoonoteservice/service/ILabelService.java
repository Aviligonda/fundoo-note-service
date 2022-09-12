package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.LabelDTO;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.util.Response;

import java.util.List;

public interface ILabelService {
    Response createLabel(LabelDTO labelDTO, String token);

    Response delete(Long id, String token);

    List<LabelModel> getAllLabels(String token);

    Response update(Long id, LabelDTO labelDTO, String token);
}
