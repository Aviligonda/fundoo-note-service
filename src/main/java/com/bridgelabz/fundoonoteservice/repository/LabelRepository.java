package com.bridgelabz.fundoonoteservice.repository;

import com.bridgelabz.fundoonoteservice.model.LabelModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<LabelModel,Long> {
}
