package com.example.atlantis.service;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BusquedaService {



    public List<Hotel> AccionBuscar(Busqueda busqueda, List<Hotel> listaHoteles){
        String strBusqueda;
        List<Hotel> coincidencias = new ArrayList<>();
        strBusqueda = busqueda.getHotelBuscar().toLowerCase();

        for( Hotel hotel:listaHoteles){
            if(estaAbierto(hotel,busqueda)){
                if((hotel.getLocalidad().toLowerCase().contains(strBusqueda))||
                        (hotel.getNombre().toLowerCase().contains(strBusqueda))){
                    coincidencias.add(hotel);
                }
            }

        }


       coincidencias.forEach(hotel -> System.out.println(estaAbierto(hotel,busqueda)));
        return coincidencias;
    }
    private boolean estaAbierto(Hotel hotel, Busqueda busqueda ){
        if((hotel.getFecha_apertura().isBefore(LocalDate.parse(busqueda.getFechaInicial()))&&
                (hotel.getFecha_cierre().isAfter(LocalDate.parse(busqueda.getFechaFinal()))))){
            return true;
        }
        return false;
    }
//    private int obtenerMes(Date fecha){
//
//        return Integer.valueOf(fecha.toString().substring(5,7));}
//    private int obtenerMes(String fecha){
//
//        return Integer.valueOf(fecha.toString().substring(5,7));}
//    private int obtenerDia(Date fecha){
//
//        return Integer.valueOf(fecha.toString().substring(8,10));}
//    private int obtenerDia(String fecha){
//
//        return Integer.valueOf(fecha.toString().substring(8,10));}

}
