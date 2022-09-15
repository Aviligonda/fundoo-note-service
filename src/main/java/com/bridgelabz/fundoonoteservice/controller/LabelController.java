package com.bridgelabz.fundoonoteservice.controller;

import com.bridgelabz.fundoonoteservice.dto.LabelDTO;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.service.ILabelService;
import com.bridgelabz.fundoonoteservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*
 * Purpose :REST ApIs Controller
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 *
 * */
@RestController
@RequestMapping("/label")
public class LabelController {
    @Autowired
    ILabelService labelService;

    /*
     * Purpose : Label Details Create
     * @author : Aviligonda Sreenivasulu
     * @Param : labelDTO,token
     * */
    @PostMapping("/create")
    public ResponseEntity<Response> createLabel(@Valid @RequestBody LabelDTO labelDTO,
                                                @RequestHeader String token) {
        Response response = labelService.createLabel(labelDTO, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Label Details Update
     * @author : Aviligonda Sreenivasulu
     * @Param : labelDTO,id,token
     * */
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id,
                                           @Valid @RequestBody LabelDTO labelDTO,
                                           @RequestHeader String token) {
        Response response = labelService.update(id, labelDTO, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Get All Label Details
     * @author : Aviligonda Sreenivasulu
     * @Param : token
     * */
    @GetMapping("/getAllLabels")
    public ResponseEntity<List<?>> getAllLabels(@RequestHeader String token) {
        List<LabelModel> response = labelService.getAllLabels(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Delete Label Details
     * @author : Aviligonda Sreenivasulu
     * @Param : token,id
     * */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id,
                                           @RequestHeader String token) {
        Response response = labelService.delete(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
