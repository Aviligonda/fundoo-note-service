package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.NoteServiceDTO;
import com.bridgelabz.fundoonoteservice.model.NoteServiceModel;
import com.bridgelabz.fundoonoteservice.util.Response;

import java.util.List;
/*
 * Purpose : INoteService to Show The all APIs
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
public interface INoteService {
    Response createNote(NoteServiceDTO noteServiceDTO);

    Response updateNote(Long id, NoteServiceDTO noteServiceDTO);

    List<NoteServiceModel> getAllNote();

    Response getNoteById(Long id);

    Response permanentDelete(Long id);

    Response trashNote(Long id);

    Response restoreNote(Long id);

    Response archiveNote(Long id);

    Response pinNote(Long id);

    Response changeColourNote(Long id, String colour);

    Response setRemainder(Long id, String remainder);

    Response unPinNote(Long id);

    Response unArchiveNote(Long id);

    List<NoteServiceModel> getAllNotesInTrash();

    List<NoteServiceModel> getAllNotesInPin();

    List<NoteServiceModel> getAllNotesInArchive();
}
