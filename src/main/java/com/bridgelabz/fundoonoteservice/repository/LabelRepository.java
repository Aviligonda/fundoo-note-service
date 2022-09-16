package com.bridgelabz.fundoonoteservice.repository;

import com.bridgelabz.fundoonoteservice.model.LabelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/*
 * Purpose : LabelRepository Are Used to Store the Data into Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */

public interface LabelRepository extends JpaRepository<LabelModel, Long> {
    @Query(value = "select * from label where user_id =:userId  ", nativeQuery = true)
    List<LabelModel> findAllByUserId(Long userId);

}
