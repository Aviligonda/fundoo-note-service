package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.NoteServiceDTO;
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
import java.util.Date;
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
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            NoteServiceModel noteServiceModel = new NoteServiceModel(noteServiceDTO);
            noteServiceModel.setRegisterDate(LocalDateTime.now());
            noteServiceModel.setTrash(false);
            noteServiceModel.setPin(false);
            noteServiceModel.setArchive(false);
            noteServiceModel.setColor("White");
            noteServiceRepository.save(noteServiceModel);
            return new Response(200, "Success", noteServiceModel);
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Updating Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id,noteServiceDTO
     * */
    @Override
    public Response updateNote(Long id, NoteServiceDTO noteServiceDTO, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                isNotePresent.get().setTitle(noteServiceDTO.getTitle());
                isNotePresent.get().setDescription(noteServiceDTO.getDescription());
                isNotePresent.get().setEmail(noteServiceDTO.getEmail());
                isNotePresent.get().setUpdateDate(LocalDateTime.now());
                noteServiceRepository.save(isNotePresent.get());
                return new Response(200, "Success", isNotePresent.get());
            } else {
                throw new UserException(400, "No Note Found With this Id");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Get All Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @Override
    public List<NoteServiceModel> getAllNote(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<NoteServiceModel> isNotePresent = noteServiceRepository.findAll();
            if (isNotePresent.size() > 0) {
                return isNotePresent;
            } else {
                throw new UserException(400, "No notes Found");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Get Note Details By id
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response getNoteById(Long id, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                return new Response(200, "Success", isNotePresent.get());
            } else {
                throw new UserException(400, "No Note Found With this Id");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Permanent Delete Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response permanentDelete(Long id, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().isTrash()) {
                    noteServiceRepository.delete(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                } else {
                    throw new UserException(400, "No Note found in trash with this id");
                }
            }
            throw new UserException(400, "No Note Found With this Id");
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Trash Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response trashNote(Long id, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                isNotePresent.get().setTrash(true);
                noteServiceRepository.save(isNotePresent.get());
                return new Response(200, "Success", isNotePresent.get());
            } else {
                throw new UserException(400, "No note found with this id");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of restore note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response restoreNote(Long id, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (isNotePresent.get().isTrash()) {
                    isNotePresent.get().setTrash(false);
                    noteServiceRepository.save(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                } else {
                    throw new UserException(400, "No Note found in trash with this id");
                }
            }
            throw new UserException(400, "No Note Found With this Id");
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of archive note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response archiveNote(Long id, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (!isNotePresent.get().isTrash()) {
                    isNotePresent.get().setArchive(true);
                    noteServiceRepository.save(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                } else {
                    throw new UserException(400, " Note found in trash, Not possible to archive");
                }
            }
            throw new UserException(400, "No Note found  with this id");
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of pin note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response pinNote(Long id, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (!isNotePresent.get().isTrash()) {
                    isNotePresent.get().setPin(true);
                    noteServiceRepository.save(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                } else {
                    throw new UserException(400, " Note found in trash, Not possible to pin");
                }
            }
            throw new UserException(400, "No Note found  with this id");
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of change  colour
     * @author : Aviligonda Sreenivasulu
     * @Param :  id,colour
     * */
    @Override
    public Response changeColourNote(Long id, String colour, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (!isNotePresent.get().isTrash()) {
                    isNotePresent.get().setColor(colour);
                    noteServiceRepository.save(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                } else {
                    throw new UserException(400, " Note found in trash, Not possible to change colour");
                }
            }
            throw new UserException(400, "No Note found  with this id");
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of set remainder
     * @author : Aviligonda Sreenivasulu
     * @Param :  id,localDateTime
     * */
    @Override
    public Response setRemainder(Long id, Date remainder, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (!isNotePresent.get().isTrash()) {
                    isNotePresent.get().setReminderTime(remainder);
                    noteServiceRepository.save(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                } else {
                    throw new UserException(400, " Note found in trash, Not possible to Set Remainder");
                }
            }
            throw new UserException(400, "No Note found  with this id");
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of UnPin note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response unPinNote(Long id, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (!isNotePresent.get().isTrash()) {
                    isNotePresent.get().setPin(false);
                    noteServiceRepository.save(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                } else {
                    throw new UserException(400, " Note found in trash, Not possible to UnPin");
                }
            }
            throw new UserException(400, "No Note found  with this id");
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of UnArchive note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  id
     * */
    @Override
    public Response unArchiveNote(Long id, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                if (!isNotePresent.get().isTrash()) {
                    isNotePresent.get().setArchive(false);
                    noteServiceRepository.save(isNotePresent.get());
                    return new Response(200, "Success", isNotePresent.get());
                } else {
                    throw new UserException(400, " Note found in trash, Not possible to archive");
                }
            }
            throw new UserException(400, "No Note found  with this id");
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Get All Note Details in Trash
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @Override
    public List<NoteServiceModel> getAllNotesInTrash(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<NoteServiceModel> isNotePresent = noteServiceRepository.findAllByTrash();
            if (isNotePresent.size() > 0) {
                return isNotePresent;
            } else {
                throw new UserException(400, "No notes Found in Trash");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Get All Note Details in Pin
     * @author : Aviligonda Sreenivasulu
     * @Param :
     * */
    @Override
    public List<NoteServiceModel> getAllNotesInPin(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<NoteServiceModel> isNotePresent = noteServiceRepository.findAllByPin();
            if (isNotePresent.size() > 0) {
                return isNotePresent;
            } else {
                throw new UserException(400, "No notes Found in Pin");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Get All Note Details in Archive
     * @author : Aviligonda Sreenivasulu
     * @Param :token
     * */
    @Override
    public List<NoteServiceModel> getAllNotesInArchive(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<NoteServiceModel> isNotePresent = noteServiceRepository.findAllByArchive();
            if (isNotePresent.size() > 0) {
                return isNotePresent;
            } else {
                throw new UserException(400, "No notes Found in Archive");
            }
        } else {
            throw new UserException(400, "Token Wrong");
        }
    }

    /*
     * Purpose : Implement the Logic of Add Label List Details
     * @author : Aviligonda Sreenivasulu
     * @Param :labelId,noteId,token
     * */
    @Override
    public Response addLabels(List<Long> labelId, List<Long> noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            List<LabelModel> labelModelList = new ArrayList<>();
            List<NoteServiceModel> noteServiceModelList = new ArrayList<>();
            labelId.stream().forEach(label -> {
                Optional<LabelModel> isLabelPresent = labelRepository.findById(label);
                if (isLabelPresent.isPresent()) {
                    labelModelList.add(isLabelPresent.get());
                    isLabelPresent.get().setNoteList(noteServiceModelList);
                    labelRepository.save(isLabelPresent.get());
                }
            });
            noteId.stream().forEach(note -> {
                Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(note);
                if (isNotePresent.isPresent()) {
                    noteServiceModelList.add(isNotePresent.get());
                    isNotePresent.get().setLabelList(labelModelList);
                    noteServiceRepository.save(isNotePresent.get());
                }
            });
            return new Response(200, "Success", isUserPresent);
        } else {
            return new Response(400, "Failed", isUserPresent);
        }
    }

    /*
     * Purpose : Implement the Logic of Add Collaborators
     * @author : Aviligonda Sreenivasulu
     * @Param : collaborator,noteId,token
     * */
    @Override
    public Response addCollaborator(String token, Long noteId, List<String> collaborator) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(noteId);
            if (isNotePresent.isPresent()) {
                List<String> collaboratorsList = new ArrayList<>();
                collaborator.stream().forEach(collab -> {
                    boolean isUser = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/emailValidation/" + collab, Boolean.class);
                    if (isUser) {
                        collaboratorsList.add(collab);
                    }
                });
                isNotePresent.get().setCollaborator(collaboratorsList);
                noteServiceRepository.save(isNotePresent.get());
                return new Response(200, "Success", isNotePresent.get());
            } else {
                throw new UserException(400, "Note Id Not Found");
            }
        }
        throw new UserException(400, "Token is Wrong");
    }

}

