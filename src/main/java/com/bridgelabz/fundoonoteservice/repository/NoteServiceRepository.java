package com.bridgelabz.fundoonoteservice.repository;

import com.bridgelabz.fundoonoteservice.model.NoteServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
/*
 * Purpose : NoteServiceRepository Are Used to Store the Data into Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
public interface NoteServiceRepository extends JpaRepository<NoteServiceModel,Long> {
}
