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
    Response createNote(NoteServiceDTO noteServiceDTO, String token);

    Response updateNote(Long id, NoteServiceDTO noteServiceDTO, String token);

    List<NoteServiceModel> getAllNote(String token);

    Response getNoteById(Long id, String token);

    Response permanentDelete(Long id, String token);

    Response trashNote(Long id, String token);

    Response restoreNote(Long id, String token);

    Response archiveNote(Long id, String token);

    Response pinNote(Long id, String token);

    Response changeColourNote(Long id, String colour, String token);

    Response setRemainder(Long id, String remainder, String token);

    Response unPinNote(Long id, String token);

    Response unArchiveNote(Long id, String token);

    List<NoteServiceModel> getAllNotesInTrash(String token);

    List<NoteServiceModel> getAllNotesInPin(String token);

    List<NoteServiceModel> getAllNotesInArchive(String token);

    Response addLabels(Long labelId, Long noteId, String token);

    Response addCollaborator(String token, Long noteId, String collaborator, Long collbId);

}
