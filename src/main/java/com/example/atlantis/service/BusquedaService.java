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
        String strBusqueda;
        List<Hotel> coincidencias = new ArrayList<>();
        strBusqueda = busqueda.getHotelBuscar().toLowerCase();

        for( Hotel hotel:listaHoteles){
            if((hotel.getLocalidad().toLowerCase().contains(strBusqueda))||
                    (hotel.getNombre().toLowerCase().contains(strBusqueda))){
                coincidencias.add(hotel);
            }
        }
        System.out.println(coincidencias.size());
       coincidencias.forEach(hotel -> System.out.println(estaAbierto(hotel,busqueda)));
        return coincidencias;
    }
    private boolean estaAbierto(Hotel hotel, Busqueda busqueda ){
        if((obtenerMes(hotel.getFecha_apertura())<obtenerMes(busqueda.getFechaInicial()))&&
                (obtenerMes(hotel.getFecha_cierre())>obtenerMes(busqueda.getFechaFinal()))){
            return true;
        }else if(obtenerMes(hotel.getFecha_apertura())==obtenerMes(busqueda.getFechaInicial())){
            if(obtenerDia(hotel.getFecha_apertura())<=obtenerDia(busqueda.getFechaInicial())) return true;
        }
        return false;
    }
    private int obtenerMes(Date fecha){

        return Integer.valueOf(fecha.toString().substring(5,7));}
    private int obtenerMes(String fecha){

        return Integer.valueOf(fecha.toString().substring(5,7));}
    private int obtenerDia(Date fecha){

        return Integer.valueOf(fecha.toString().substring(8,10));}
    private int obtenerDia(String fecha){

        return Integer.valueOf(fecha.toString().substring(8,10));}


}
