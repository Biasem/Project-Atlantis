package com.example.atlantis.service;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BusquedaService {



    public List<Hotel> AccionBuscar(Busqueda busqueda, List<Hotel> listaHoteles){
        List<Hotel> coincidencias = new ArrayList<>();
        busqueda.setHotelBuscar(busqueda.getHotelBuscar().toLowerCase());

        for( Hotel hotel:listaHoteles){
            if((hotel.getLocalidad().toLowerCase().contains(busqueda.getHotelBuscar()))||
                    (hotel.getNombre().toLowerCase().contains(busqueda.getHotelBuscar()))){
                coincidencias.add(hotel);
            }
        }

       coincidencias.forEach(hotel -> System.out.println(hotel.getFecha_apertura()));
        return coincidencias;
    }
    private boolean estaAbierto(Hotel hotel, Busqueda busqueda ){



        return false;
    }
    private Integer obtenerDia(Date fecha){

        return Integer.valueOf(fecha.toString().substring(5,7));}
    private Integer obtenerMes(Date fecha){

        return Integer.valueOf(fecha.toString().substring(8,10));}


}
