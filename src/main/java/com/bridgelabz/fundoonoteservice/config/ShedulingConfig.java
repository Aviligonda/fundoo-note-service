package com.bridgelabz.fundoonoteservice.config;

import com.bridgelabz.fundoonoteservice.exception.UserException;
import com.bridgelabz.fundoonoteservice.model.NoteServiceModel;
import com.bridgelabz.fundoonoteservice.repository.NoteServiceRepository;
import com.bridgelabz.fundoonoteservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ShedulingConfig {
    static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @Autowired
    MailService mailService;
    @Autowired
    NoteServiceRepository noteServiceRepository;

    @Scheduled(fixedDelay = 1000)
    public void emailShedulingJob() {
        List<NoteServiceModel> noteList = noteServiceRepository.findAll();
        for (NoteServiceModel noteServiceModel : noteList) {
            String remainderDate = noteServiceModel.getReminderTime();
            LocalDateTime currentDate = LocalDateTime.now();
            if (remainderDate.equals(currentDate.format(format))) {
                String body = "Note Remainder with id is :" + noteServiceModel.getId();
                String subject = "Set Remainder Successfully";
                mailService.send(noteServiceModel.getEmail(), body, subject);
            }
        }
    }
}
