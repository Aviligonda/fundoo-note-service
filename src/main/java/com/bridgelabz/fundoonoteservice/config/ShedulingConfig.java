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
import java.util.Date;
import java.util.List;

@Component
public class ShedulingConfig {
    static Format formatter = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    MailService mailService;
    @Autowired
    NoteServiceRepository noteServiceRepository;

    @Scheduled(fixedDelay = 11000)
    public void emailShedulingJob() {
        List<NoteServiceModel> noteList = noteServiceRepository.findAll();
        for (NoteServiceModel noteServiceModel : noteList) {
            Date remainderDate = noteServiceModel.getReminderTime();
            String remainderDateFormat = formatter.format(remainderDate);
            String currentDate = formatter.format(new Date());

            if (remainderDateFormat.equals(currentDate)) {
                String body = "Note Remainder with id is :" + noteServiceModel.getId();
                String subject = "Set Remainder Successfully";
                mailService.send(noteServiceModel.getEmail(), body, subject);
            } else {
                throw new UserException(400, "Today Date Not Found");
            }
        }
    }
}
