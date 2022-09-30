package com.example.atlantis.service;


import com.example.atlantis.model.Habitaciones;
import com.example.atlantis.model.Regimen;
import com.example.atlantis.repository.RegimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegimenService {

    @Autowired
    private RegimenRepository regimenRepository;


    public List<Regimen> getAll(){
        return regimenRepository.findAll();
    }

    public Regimen getById(int id){
        return regimenRepository.findById(id).orElse(null);
    }

}
