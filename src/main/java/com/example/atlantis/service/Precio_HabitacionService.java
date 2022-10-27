package com.example.atlantis.service;

import com.example.atlantis.model.Precio_Hab;
import com.example.atlantis.repository.Precio_HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Precio_HabitacionService {

    @Autowired
    private Precio_HabitacionRepository precio_habitacionRepository;

    public List<Precio_Hab> getAll(){
        return precio_habitacionRepository.findAll();
    }
    private Precio_Hab getById(int id){
        return precio_habitacionRepository.findById(id).orElse(null);
    }

    private List<Precio_Hab> filtrarHabitacion (Integer id){
        List<Precio_Hab> completa = precio_habitacionRepository.findAll();
        List<Precio_Hab> filtrado = new ArrayList<>();
        for (Precio_Hab x: completa){
            if (x.getId_hab().getId().equals(id)){
                filtrado.add(x);
            }
        }
        return filtrado;
    }

}
