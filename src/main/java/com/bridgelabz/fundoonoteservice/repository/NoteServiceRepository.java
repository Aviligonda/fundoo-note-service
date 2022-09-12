package com.bridgelabz.fundoonoteservice.repository;

import com.bridgelabz.fundoonoteservice.model.NoteServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
 * Purpose : NoteServiceRepository Are Used to Store the Data into Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
public interface NoteServiceRepository extends JpaRepository<NoteServiceModel, Long> {
    @Query(value = "select * from note_service where trash = true ", nativeQuery = true)
    List<NoteServiceModel> findAllByTrash();

    @Query(value = "select * from note_service where is_archive = true ", nativeQuery = true)
    List<NoteServiceModel> findAllByArchive();

    @Query(value = "select * from note_service where pin = true ", nativeQuery = true)
    List<NoteServiceModel> findAllByPin();
}
