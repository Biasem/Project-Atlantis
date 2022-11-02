package com.example.atlantis.service;

import com.example.atlantis.model.Hab_Reserva_Hotel;
import com.example.atlantis.repository.Habitacion_Reserva_HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Habitacion_Reserva_HotelService {

    @Autowired
    private Habitacion_Reserva_HotelRepository habitacion_reserva_hotelRepository;

    public List<Hab_Reserva_Hotel> getAll(){
        return habitacion_reserva_hotelRepository.findAll();
    }

    private Hab_Reserva_Hotel getById(int id){
        return habitacion_reserva_hotelRepository.findById(id).orElse(null);
    }

    public Integer checkearReserva(Integer id){
        List<Hab_Reserva_Hotel> lista = habitacion_reserva_hotelRepository.findAll();
        Integer comprobar = 0;
        for (Hab_Reserva_Hotel x: lista){
            if (x.getId_regimen().getId().equals(id)){
                comprobar = 1;
            }
            else{

            }
        }
        return comprobar;
    }

}
