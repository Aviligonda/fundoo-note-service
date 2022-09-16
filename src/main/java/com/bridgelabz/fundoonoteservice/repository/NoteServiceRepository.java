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
    @Query(value = "select * from note_service where trash = true and user_id =:userId", nativeQuery = true)
    List<NoteServiceModel> findAllByTrash(Long userId);

    @Query(value = "select * from note_service where is_archive = true and user_id =:userId", nativeQuery = true)
    List<NoteServiceModel> findAllByArchive(Long userId);

    @Query(value = "select * from note_service where pin = true and user_id =:userId", nativeQuery = true)
    List<NoteServiceModel> findAllByPin(Long userId);
    @Query(value = "select * from note_service where user_id =:userId  ", nativeQuery = true)
    List<NoteServiceModel> findAllByUserId(Long userId);
}
