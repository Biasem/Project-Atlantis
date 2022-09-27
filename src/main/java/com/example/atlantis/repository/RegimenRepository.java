package com.example.atlantis.repository;

import com.example.atlantis.model.Regimen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegimenRepository extends JpaRepository<Regimen, Integer> {

    List<Regimen> findAll();

}
