package com.example.atlantis.service;


import com.example.atlantis.model.Habitaciones;
import com.example.atlantis.repository.HabitacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionesService {


    @Autowired
    private HabitacionesRepository habitacionesRepository;

    public List<Habitaciones> getAll(){
        return habitacionesRepository.findAll();
    }

    public Habitaciones getById(int id){
        return habitacionesRepository.findById(id).orElse(null);
    }



}
