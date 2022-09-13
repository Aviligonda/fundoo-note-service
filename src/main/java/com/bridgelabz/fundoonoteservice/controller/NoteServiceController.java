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
    public ResponseEntity<Response> createNote(@Valid @RequestBody NoteServiceDTO noteServiceDTO,
                                               @RequestHeader String token) {
        Response response = noteService.createNote(noteServiceDTO,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Note Details Update
     * @author : Aviligonda Sreenivasulu
     * @Param : noteServiceDTO,id
     * */
    @PutMapping("/updateNote/{id}")
    public ResponseEntity<Response> updateNote(@PathVariable Long id,
                                               @Valid @RequestBody NoteServiceDTO noteServiceDTO,
                                               @RequestHeader String token) {
        Response response = noteService.updateNote(id, noteServiceDTO,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Get All Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @GetMapping("/getAllNote")
    public ResponseEntity<List<?>> getAllNote(@RequestHeader String token) {
        List<NoteServiceModel> response = noteService.getAllNote(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Get Note Details by ID
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @GetMapping("/getNoteById/{id}")
    public ResponseEntity<Response> getNoteById(@PathVariable Long id,
                                                @RequestHeader String token) {
        Response response = noteService.getNoteById(id,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Permanent Delete Note Details by ID
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @DeleteMapping("/permanentDelete/{id}")
    public ResponseEntity<Response> permanentDelete(@PathVariable Long id,
                                                    @RequestHeader String token) {
        Response response = noteService.permanentDelete(id,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Trash Note  Details By ID
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @DeleteMapping("/trashNote/{id}")
    public ResponseEntity<Response> trashNote(@PathVariable Long id,
                                              @RequestHeader String token) {
        Response response = noteService.trashNote(id,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Restore Note Details By id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PutMapping("/restoreNote/{id}")
    public ResponseEntity<Response> restoreNote(@PathVariable Long id,
                                                @RequestHeader String token) {
        Response response = noteService.restoreNote(id,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : ArchiveNote Details by Id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PutMapping("/archiveNote/{id}")
    public ResponseEntity<Response> archiveNote(@PathVariable Long id,
                                                @RequestHeader String token) {
        Response response = noteService.archiveNote(id,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : pinNote Details by id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PutMapping("/pinNote/{id}")
    public ResponseEntity<Response> pinNote(@PathVariable Long id,
                                            @RequestHeader String token) {
        Response response = noteService.pinNote(id,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : change Colour Note Details by id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PutMapping("/changeColourNote/{id}")
    public ResponseEntity<Response> changeColourNote(@PathVariable Long id,
                                                     @RequestParam String colour,
                                                     @RequestHeader String token) {
        Response response = noteService.changeColourNote(id, colour,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Set Remainder time by Id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PostMapping("/setRemainder/{id}")
    public ResponseEntity<Response> setRemainder(@PathVariable Long id,
                                                 @RequestParam String remainder,
                                                 @RequestHeader String token) {
        Response response = noteService.setRemainder(id, remainder,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*
     * Purpose : unArchiveNote Details by Id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PutMapping("/unArchiveNote/{id}")
    public ResponseEntity<Response> unArchiveNote(@PathVariable Long id,
                                                  @RequestHeader String token) {
        Response response = noteService.unArchiveNote(id,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : unPinNote Details by id
     * @author : Aviligonda Sreenivasulu
     * @Param : id
     * */
    @PutMapping("/unPinNote/{id}")
    public ResponseEntity<Response> unPinNote(@PathVariable Long id,
                                              @RequestHeader String token) {
        Response response = noteService.unPinNote(id,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*
     * Purpose : Get All Note Details in Trash
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @GetMapping("/getAllNotesInTrash")
    public ResponseEntity<List<?>> getAllNotesInTrash(@RequestHeader String token) {
        List<NoteServiceModel> response = noteService.getAllNotesInTrash(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*
     * Purpose : Get All Note Details in Pin
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @GetMapping("/getAllNotesInPin")
    public ResponseEntity<List<?>> getAllNotesInPin(@RequestHeader String token) {
        List<NoteServiceModel> response = noteService.getAllNotesInPin(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*
     * Purpose : Get All Note Details in Archive
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @GetMapping("/getAllNotesInArchive")
    public ResponseEntity<List<?>> getAllNotesInArchive(@RequestHeader String token) {
        List<NoteServiceModel> response = noteService.getAllNotesInArchive(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/addLabels")
    public ResponseEntity<Response> addLabels(@RequestParam List<Long> labelId,
                                                      @RequestParam Long noteId,
                                                      @RequestHeader String token){
        Response response =noteService.addLabels(labelId,noteId,token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/addCollaborator")
    public ResponseEntity<Response> addCollaborator(@RequestParam String emailId,@RequestParam Long noteId,@RequestParam List<String > collaborator){
        Response response =noteService.addCollaborator(emailId,noteId,collaborator);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
