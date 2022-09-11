package com.bridgelabz.fundoonoteservice.controller;

import com.bridgelabz.fundoonoteservice.dto.NoteServiceDTO;
import com.bridgelabz.fundoonoteservice.model.NoteServiceModel;
import com.bridgelabz.fundoonoteservice.service.INoteService;
import com.bridgelabz.fundoonoteservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/*
 * Purpose :REST ApIs Controller
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 *
 * */
@RestController
@RequestMapping("noteService")
public class NoteServiceController {
    @Autowired
    INoteService noteService;

    /*
     * Purpose : Note Details Create
     * @author : Aviligonda Sreenivasulu
     * @Param : noteServiceDTO
     * */
    @PostMapping("/createNote")
    public ResponseEntity<Response> createNote(@Valid @RequestBody NoteServiceDTO noteServiceDTO) {
        Response response = noteService.createNote(noteServiceDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Note Details Update
     * @author : Aviligonda Sreenivasulu
     * @Param : noteServiceDTO,id
     * */
    @PostMapping("/updateNote/{id}")
    public ResponseEntity<Response> updateNote(@PathVariable Long id,
                                               @Valid @RequestBody NoteServiceDTO noteServiceDTO) {
        Response response = noteService.updateNote(id, noteServiceDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Get All Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @PostMapping("/getAllNote")
    public ResponseEntity<List<?>> getAllNote() {
        List<NoteServiceModel> response = noteService.getAllNote();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Get Note Details by ID
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PostMapping("/getNoteById/{id}")
    public ResponseEntity<Response> getNoteById(@PathVariable Long id) {
        Response response = noteService.getNoteById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Permanent Delete Note Details by ID
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PostMapping("/permanentDelete/{id}")
    public ResponseEntity<Response> permanentDelete(@PathVariable Long id) {
        Response response = noteService.permanentDelete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Trash Note  Details By ID
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PostMapping("/trashNote/{id}")
    public ResponseEntity<Response> trashNote(@PathVariable Long id) {
        Response response = noteService.trashNote(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Restore Note Details By id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PostMapping("/restoreNote/{id}")
    public ResponseEntity<Response> restoreNote(@PathVariable Long id) {
        Response response = noteService.restoreNote(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : ArchiveNote Details by Id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PostMapping("/archiveNote/{id}")
    public ResponseEntity<Response> archiveNote(@PathVariable Long id) {
        Response response = noteService.archiveNote(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : pinNote Details by id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PostMapping("/pinNote/{id}")
    public ResponseEntity<Response> pinNote(@PathVariable Long id) {
        Response response = noteService.pinNote(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : change Colour Note Details by id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PostMapping("/changeColourNote/{id}")
    public ResponseEntity<Response> changeColourNote(@PathVariable Long id,
                                                     @RequestParam String colour) {
        Response response = noteService.changeColourNote(id, colour);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Set Remainder time by Id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PostMapping("/setRemainder/{id}")
    public ResponseEntity<Response> setRemainder(@PathVariable Long id,
                                                 @RequestParam LocalDateTime localDateTime) {
        Response response = noteService.setRemainder(id, localDateTime);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
