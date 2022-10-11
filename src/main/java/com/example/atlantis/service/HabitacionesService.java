package com.example.atlantis.service;


import com.example.atlantis.model.BuscadorID;
import com.example.atlantis.model.Habitaciones;
import com.example.atlantis.model.Hotel;
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

    public List<Habitaciones> conseguir(BuscadorID numero, List<Habitaciones> lista){
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

}
