package com.example.atlantis.service;

import com.example.atlantis.model.Reserva;
import com.example.atlantis.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;


    public List<Reserva> getAll(){
        return reservaRepository.findAll();
    }

    public Reserva getById(int id){
        return reservaRepository.findById(id).orElse(null);

    }

}
