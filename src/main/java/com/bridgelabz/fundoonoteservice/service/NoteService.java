package com.bridgelabz.fundoonoteservice.service;

import com.bridgelabz.fundoonoteservice.dto.NoteServiceDTO;
import com.bridgelabz.fundoonoteservice.exception.UserException;
import com.bridgelabz.fundoonoteservice.model.NoteServiceModel;
import com.bridgelabz.fundoonoteservice.repository.NoteServiceRepository;
import com.bridgelabz.fundoonoteservice.util.Response;
import com.bridgelabz.fundoonoteservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
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

    /*
     * Purpose : Implement the Logic of Creating Note Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  noteServiceDTO
     * */
    @Override
    public Response createNote(NoteServiceDTO noteServiceDTO, List<String> collaborator, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            NoteServiceModel noteServiceModel = new NoteServiceModel(noteServiceDTO);
            noteServiceModel.setRegisterDate(LocalDateTime.now());
            noteServiceModel.setTrash(false);
            noteServiceModel.setPin(false);
            noteServiceModel.setArchive(false);
            noteServiceModel.setColor("White");
            noteServiceModel.setCollaborator(collaborator);
            noteServiceRepository.save(noteServiceModel);
            String body = "Note Added Successfully with id is :" + noteServiceModel.getId();
            String subject = "Note Registration Successfully";
            mailService.send(noteServiceModel.getEmailId(), body, subject);
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
    public Response updateNote(Long id, NoteServiceDTO noteServiceDTO, List<String> collaborator, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://FUNDOO-USER-SERVICE:8080/userService/validate/" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NoteServiceModel> isNotePresent = noteServiceRepository.findById(id);
            if (isNotePresent.isPresent()) {
                isNotePresent.get().setTitle(noteServiceDTO.getTitle());
                isNotePresent.get().setDescription(noteServiceDTO.getDescription());
                isNotePresent.get().setEmailId(noteServiceDTO.getEmailId());
                isNotePresent.get().setCollaborator(collaborator);
                isNotePresent.get().setUpdateDate(LocalDateTime.now());
                noteServiceRepository.save(isNotePresent.get());
                String body = "Note Updated Successfully with id is :" + isNotePresent.get().getId();
                String subject = "Note Updated Successfully";
                mailService.send(isNotePresent.get().getEmailId(), body, subject);
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
                    String body = "Note Deleted Successfully with id is :" + isNotePresent.get().getId();
                    String subject = "Note Registration Successfully";
                    mailService.send(isNotePresent.get().getEmailId(), body, subject);
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
    public Response setRemainder(Long id, String remainder, String token) {
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
     * @Param :
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

}
