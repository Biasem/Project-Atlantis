package com.example.atlantis.service;


import com.example.atlantis.model.*;
import com.example.atlantis.repository.HabitacionesRepository;
import com.example.atlantis.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HabitacionesService {


    @Autowired
    private HabitacionesRepository habitacionesRepository;
    @Autowired
    private HotelRepository hotelRepository;

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
    public void borrarHabitacion(Integer id){
        habitacionesRepository.deleteAllById(Collections.singleton(id));
    }

    public void editarHabitacion(Integer id, Habitaciones habitacion){
       List<Habitaciones> lista = habitacionesRepository.getHabitacionesById(id);
       for (Habitaciones x: lista){
           x.setNum_hab(habitacion.getNum_hab());
           x.setTipo_hab(habitacion.getTipo_hab());
           x.setHab_ocupadas(habitacion.getHab_ocupadas());
           x.setMax_cliente(habitacion.getMax_cliente());
           habitacionesRepository.save(x);
       }
    }

    public Habitaciones conseguirIDHotel (Integer idhotel, Habitaciones habitacion){
        List<Hotel> hoteles = hotelRepository.findAll();
        Hotel hotel = new Hotel();
        for (Hotel x: hoteles){
            if(x.getId()==idhotel){
                hotel = x;
            }
        }
        habitacion.setId_hotel(hotel);

        return habitacion;
    }
}
