package com.example.atlantis.service;

import com.example.atlantis.model.Habitaciones;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Precio_Hab;
import com.example.atlantis.repository.HabitacionesRepository;
import com.example.atlantis.repository.HotelRepository;
import com.example.atlantis.repository.Precio_HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Precio_HabitacionService {

    @Autowired
    private Precio_HabitacionRepository precio_habitacionRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HabitacionesRepository habitacionesRepository;

    public List<Precio_Hab> getAll(){
        return precio_habitacionRepository.findAll();
    }
    private Precio_Hab getById(int id){
        return precio_habitacionRepository.findById(id).orElse(null);
    }

    public List<Precio_Hab> filtrarHabitacion (Integer id){
        List<Precio_Hab> completa = precio_habitacionRepository.findAll();
        List<Precio_Hab> filtrado = new ArrayList<>();
        for (Precio_Hab x: completa){
            if (x.getId_hab().getId().equals(id)){
                filtrado.add(x);
            }
        }
        return filtrado;
    }

    public Integer conseguirPrecioHabitacion (Integer id){
        List<Precio_Hab> completa = precio_habitacionRepository.findAll();
        Integer idHotel = 0;
        for (Precio_Hab x: completa){
            if (x.getId().equals(id)){
                idHotel = x.getId_hotel().getId();
            }
        }
        return idHotel;
    }

    public Hotel conseguirIDHotelprecio (Integer idhotel){
        List<Hotel> hoteles = hotelRepository.findAll();
        Hotel hotel = new Hotel();
        for (Hotel x: hoteles){
            if(x.getId()==idhotel){
                hotel = x;
            }
        }

        return hotel;
    }
    public Habitaciones conseguirIDHabitacionprecio (Integer idhabitacion){
        List<Habitaciones> habitaciones = habitacionesRepository.findAll();
        Habitaciones habitacion= new Habitaciones();
        for (Habitaciones x: habitaciones){
            if(x.getId()==idhabitacion){
                habitacion = x;
            }
        }

        return habitacion;
    }

    public void guardarPrecio (Precio_Hab hab){
        precio_habitacionRepository.save(hab);
    }

    public void borrarLista (List<Precio_Hab> lista, Habitaciones habitacion){
        for (Precio_Hab x: lista){
            if(x.getId_hab().getId().equals(habitacion.getId())){
                precio_habitacionRepository.delete(x);
            }
            else{

            }
        }
    }

    public void borrarPrecio (Integer id){
        List<Precio_Hab> lista = precio_habitacionRepository.findAll();
        Precio_Hab precio = new Precio_Hab();
        for (Precio_Hab x: lista){
            if(x.getId().equals(id)){
                precio = x;
            }
            else{

            }
        }
        precio_habitacionRepository.delete(precio);
    }
}
