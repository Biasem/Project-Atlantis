package com.example.atlantis.service;


import com.example.atlantis.model.*;
import com.example.atlantis.repository.HabitacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Habitaciones> conseguir(Integer numero, List<Habitaciones> lista){
        List<Habitaciones> habitauwu = new ArrayList<>();
        for (Habitaciones x: lista){
            if (x.getId_hotel().getId().equals(numero)){
                habitauwu.add(x);
            }
            else{

            }
        }
        return habitauwu;
    }

    public List<TipoHab> todoHab(){
        List<TipoHab> hab = new ArrayList<>();
        hab.add(TipoHab.SIMPLE);
        hab.add(TipoHab.DOBLE);
        hab.add(TipoHab.TRIPLE);
        hab.add(TipoHab.SUITE);
        return hab;
    }

    public void guardarHabitacion(Habitaciones habitacion){
        habitacionesRepository.save(habitacion);
    }

}
