package com.example.atlantis.service;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Reserva;
import com.example.atlantis.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.ReverbType;
import java.util.ArrayList;
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

    public List<Reserva> todasReservas(int id){
       List<Reserva> todas = reservaRepository.findAll();
       List<Reserva> buscadas = new ArrayList<>();

        for(int i = 0; i < todas.size(); i++ ){
            if(todas.get(i).getId_cliente().getId().equals(id)){
                buscadas.add(todas.get(i));
            }
        }
        return buscadas;


    }



}
