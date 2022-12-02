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

    public void guardarHabReservaHotel(Hab_Reserva_Hotel habReservaHotel){

        habitacion_reserva_hotelRepository.save(habReservaHotel);
    }

    public Integer UltimoIdReservadelCliente(Integer idcliente){

        return habitacion_reserva_hotelRepository.UltimoIdReservadelCliente(idcliente);
    }

    public Integer checkearReserva(Integer id){

        List<Hab_Reserva_Hotel> lista = habitacion_reserva_hotelRepository.findAll();
        Integer comprobar = 0;

        //Busqueda de habitación reservada y comprobación del regimen
        for (Hab_Reserva_Hotel x: lista){
            if (x.getId_regimen().getId().equals(id)){
                comprobar = 1;
            }
        }

        return comprobar;
    }

}
