package com.example.atlantis.repository;

import com.example.atlantis.model.Habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitacionesRepository extends JpaRepository<Habitaciones, Integer> {

    List<Habitaciones> findAll();


}
