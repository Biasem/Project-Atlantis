package com.example.atlantis.service;
import com.example.atlantis.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusquedaService {



    public List<Hotel> AccionBuscar(String cadenaABuscar, List<Hotel> listaHoteles){
        List<Hotel> coincidencias = new ArrayList<>();
        cadenaABuscar = cadenaABuscar.toLowerCase();

        for( Hotel hotel:listaHoteles){
            if((hotel.getLocalidad().toLowerCase().contains(cadenaABuscar))||
                    (hotel.getNombre().toLowerCase().contains(cadenaABuscar))){
                coincidencias.add(hotel);
            }
        }

       //coincidencias.forEach(hotel -> System.out.println(hotel.getNombre()));
        return coincidencias;
    }

}
