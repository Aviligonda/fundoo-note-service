package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.NoteServiceDTO;
import com.bridgelabz.fundoonoteservice.exception.UserException;
import com.bridgelabz.fundoonoteservice.model.LabelModel;
import com.bridgelabz.fundoonoteservice.model.NoteServiceModel;
import com.bridgelabz.fundoonoteservice.repository.LabelRepository;
import com.bridgelabz.fundoonoteservice.repository.NoteServiceRepository;
import com.bridgelabz.fundoonoteservice.util.Response;
import com.bridgelabz.fundoonoteservice.util.TokenUtil;
import com.bridgelabz.fundoonoteservice.util.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Purpose : NoteService to Implement the Business Logic
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Service
public class NoteService implements INoteService {
    @Autowired
    NoteServiceRepository noteServiceRepository;
    @Autowired
    MailService mailService;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LabelRepository labelRepository;

    /*
     * Purpose : Implement the Logic of Creating Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  noteServiceDTO
     * */
    @Override
    public Response createNote(NoteServiceDTO noteServiceDTO, String token) {
        UserResponse isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, UserResponse.class);
        if (isUserPresent.getStatusCode() == 200) {
                Long userId = tokenUtil.decodeToken(token);
                NoteServiceModel noteServiceModel = new NoteServiceModel(noteServiceDTO);
                noteServiceModel.setRegisterDate(LocalDateTime.now());
                noteServiceModel.setUserId(userId);
                noteServiceModel.setTrash(false);
                noteServiceModel.setPin(false);
                noteServiceModel.setArchive(false);
                noteServiceModel.setColor("White");
                noteServiceModel.setEmail(isUserPresent.getObject().getEmailId());
                noteServiceRepository.save(noteServiceModel);
                return new Response(200, "Success", noteServiceModel);
            }
            throw new UserException(400, "Token Wrong");
        }

    /*
     * Purpose : Implement the Logic of Updating Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id,noteServiceDTO
     * */
    @Override
    public Response updateNote(Long id, NoteServiceDTO noteServiceDTO, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    isNotePresent.get().setTitle(noteServiceDTO.getTitle());
                    isNotePresent.get().setDescription(noteServiceDTO.getDescription());
                    isNotePresent.get().setUpdateDate(LocalDateTime.now());
                    noteServiceRepository.save(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                }
                throw new UserException(400, "UserId Did't Match");
            }
            throw new UserException(400, "No Note Found With this Id");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Get All Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @Override
    public List<NoteServiceModel> getAllNote(String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            List<NoteServiceModel> isNotePresent = noteServiceRepository.findAllByUserId(userId);
            if (isNotePresent.size() > 0) {
                return isNotePresent;
            }
            throw new UserException(400, "No notes Found With This User");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Get Note Details By id
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response getNoteById(Long id, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    return new Response(200, "Success", isNotePresent.get());
                }
                throw new UserException(400, "No Note Found With this UserId");
            }
            throw new UserException(400, "No Note Found With this Id");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Permanent Delete Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response permanentDelete(Long id, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    if (isNotePresent.get().isTrash()) {
                        noteServiceRepository.delete(isNotePresent.get());
                        return new Response(200, "Success", isNotePresent.get());
                    }
                    throw new UserException(400, "No Note found in trash with this id");
                }
                throw new UserException(400, "No Note Found With this UserId");
            }
            throw new UserException(400, "No Note Found With this UserId");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Trash Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response trashNote(Long id, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    isNotePresent.get().setTrash(true);
                    noteServiceRepository.save(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                }
                throw new UserException(400, "No note found with this UserId");
            }
            throw new UserException(400, "No Note Found With this Id");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of restore note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response restoreNote(Long id, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    if (isNotePresent.get().isTrash()) {
                        isNotePresent.get().setTrash(false);
                        noteServiceRepository.save(isNotePresent.get());
                        return new Response(200, "Success", isNotePresent.get());
                    }
                    throw new UserException(400, "No Note found in trash with this id");
                }
                throw new UserException(400, "No Note Found With this UserId");
            }
            throw new UserException(400, "No Note Found With this Id");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of archive note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response archiveNote(Long id, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    if (!isNotePresent.get().isTrash()) {
                        isNotePresent.get().setArchive(true);
                        noteServiceRepository.save(isNotePresent.get());
                        return new Response(200, "Success", isNotePresent.get());
                    }
                    throw new UserException(400, " Note found in trash, Not possible to archive");
                }
                throw new UserException(400, "No Note found  with this UserId");
            }
            throw new UserException(400, "No Note found  with this id");
        }
        throw new UserException(400, "Token Wrong");
    }


    /*
     * Purpose : Implement the Logic of pin note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response pinNote(Long id, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    if (!isNotePresent.get().isTrash()) {
                        isNotePresent.get().setPin(true);
                        noteServiceRepository.save(isNotePresent.get());
                        return new Response(200, "Success", isNotePresent.get());
                    }
                    throw new UserException(400, " Note found in trash, Not possible to pin");
                }
                throw new UserException(400, "No Note found  with this UserId");
            }
            throw new UserException(400, "No Note found  with this id");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of change  colour
     * @author : Aviligonda Sreenivasulu
     * @Param :  id,colour
     * */
    @Override
    public Response changeColourNote(Long id, String colour, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    if (!isNotePresent.get().isTrash()) {
                        isNotePresent.get().setColor(colour);
                        noteServiceRepository.save(isNotePresent.get());
                        return new Response(200, "Success", isNotePresent.get());
                    }
                    throw new UserException(400, " Note found in trash, Not possible to change colour");
                }
                throw new UserException(400, "No Note found  with this UserId");
            }
            throw new UserException(400, "No Note found  with this id");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of set remainder
     * @author : Aviligonda Sreenivasulu
     * @Param :  id,localDateTime
     * */
    @Override
    public Response setRemainder(Long id, String remainder, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    if (!isNotePresent.get().isTrash()) {
                        isNotePresent.get().setReminderTime(remainder);
                        noteServiceRepository.save(isNotePresent.get());
                        return new Response(200, "Success", isNotePresent.get());
                    }
                    throw new UserException(400, " Note found in trash, Not possible to Set Remainder");
                }
                throw new UserException(400, "No Note found  with this UserId");
            }
            throw new UserException(400, "No Note found  with this id");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of UnPin note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response unPinNote(Long id, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    if (!isNotePresent.get().isTrash()) {
                        isNotePresent.get().setPin(false);
                        noteServiceRepository.save(isNotePresent.get());
                        return new Response(200, "Success", isNotePresent.get());
                    }
                    throw new UserException(400, " Note found in trash, Not possible to UnPin");
                }
                throw new UserException(400, "No Note found  with this UserId");
            }
            throw new UserException(400, "No Note found  with this id");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of UnArchive note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response unArchiveNote(Long id, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    if (!isNotePresent.get().isTrash()) {
                        isNotePresent.get().setArchive(false);
                        noteServiceRepository.save(isNotePresent.get());
                        return new Response(200, "Success", isNotePresent.get());
                    }
                    throw new UserException(400, " Note found in trash, Not possible to archive");
                }
                throw new UserException(400, "No Note found  with this Userid");
            }
            throw new UserException(400, "No Note found  with this id");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Get All Note Details in Trash
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @Override
    public List<NoteServiceModel> getAllNotesInTrash(String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            List<NoteServiceModel> isNotePresent = noteServiceRepository.findAllByTrash(userId);
            if (isNotePresent.size() > 0) {
                return isNotePresent;
            }
            throw new UserException(400, "No notes Found in Trash With this UserId");
        }
        throw new UserException(400, "Token is Wrong");
    }

    /*
     * Purpose : Implement the Logic of Get All Note Details in Pin
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @Override
    public List<NoteServiceModel> getAllNotesInPin(String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            List<NoteServiceModel> isNotePresent = noteServiceRepository.findAllByPin(userId);
            if (isNotePresent.size() > 0) {
                return isNotePresent;
            }
            throw new UserException(400, "No notes Found in Pin With this UserId");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Get All Note Details in Archive
     * @author : Aviligonda Sreenivasulu
     * @Param :token
     * */
    @Override
    public List<NoteServiceModel> getAllNotesInArchive(String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            List<NoteServiceModel> isNotePresent = noteServiceRepository.findAllByArchive(userId);
            if (isNotePresent.size() > 0) {
                return isNotePresent;
            }
            throw new UserException(400, "No notes Found in Archive With this UserId");
        }
        throw new UserException(400, "Token Wrong");
    }

    /*
     * Purpose : Implement the Logic of Add Label List Details
     * @author : Aviligonda Sreenivasulu
     * @Param :labelId,noteId,token
     * */
    @Override
    public Response addLabels(Long labelId, Long noteId, String token) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            List<LabelModel> labelModelList = new ArrayList<>();
            List<NoteServiceModel> noteServiceModelList = new ArrayList<>();
            Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
            if (isLabelPresent.isPresent()) {
                if (isLabelPresent.get().getUserId() == userId) {
                    labelModelList.add(isLabelPresent.get());
                } else {
                    throw new UserException(400, "No Label Found With This User Id");
                }
            }
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(noteId);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    noteServiceModelList.add(isNotePresent.get());
                    isNotePresent.get().setLabelList(labelModelList);
                    isLabelPresent.get().setNoteList(noteServiceModelList);
                    noteServiceRepository.save(isNotePresent.get());
                    labelRepository.save(isLabelPresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                }
                throw new UserException(400, "No Note Found With This UserId");
            }
            throw new UserException(400, "No Note Found With This Id");
        }
        throw new UserException(400, "Token is Wrong");
    }

    /*
     * Purpose : Implement the Logic of Add Collaborators
     * @author : Aviligonda Sreenivasulu
     * @Param : collaborator,noteId,token
     * */
    @Override
    public Response addCollaborator(String token, Long noteId, String collaborator, Long collbId) {
        Response isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Response.class);
        if (isUserPresent.getStatusCode() == 200) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(noteId);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().getUserId() == userId) {
                    List<String> collaboratorsList = new ArrayList<>();
                    Response isCollabEmail = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/emailValidation/" + collaborator, Response.class);
                    if (isCollabEmail.getStatusCode() == 200) {
                        collaboratorsList.add(collaborator);
                    }
//                    isNotePresent.get().setEmail();
                    isNotePresent.get().setCollaborator(collaboratorsList);
                    noteServiceRepository.save(isNotePresent.get());
                    List<String> collabList = new ArrayList<>();
                    collabList.add(isNotePresent.get().getEmail());
                    NoteServiceModel noteServiceModel = new NoteServiceModel();
                    noteServiceModel.setUserId(collbId);
                    noteServiceModel.setTitle(isNotePresent.get().getTitle());
                    noteServiceModel.setDescription(isNotePresent.get().getDescription());
                    noteServiceModel.setEmail(collaborator);
                    noteServiceModel.setCollaborator(collabList);
                    noteServiceRepository.save(noteServiceModel);

                    return new Response(200, "Success", isNotePresent.get());
                }
                throw new UserException(400, "Note Id Not Found With This UserId");
            }
            throw new UserException(400, "Note Id Not Found");
        }
        throw new UserException(400, "Token is Wrong");
    }

}

