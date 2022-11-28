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
    public Habitaciones conseguirHabitacion (Integer numero, List<Habitaciones> lista){
        Habitaciones habitauwu = new Habitaciones();
        for (Habitaciones x: lista){
            if (x.getId().equals(numero)){
                habitauwu = x;
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

    public void editarHabitacionApi(Integer id, GraphqlInput.HabitacionesInput habitacion){
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

    public GraphqlInput.HabitacionesInput conseguirIDHotelApi (Integer idhotel, GraphqlInput.HabitacionesInput habitacion){
        List<Hotel> hoteles = hotelRepository.findAll();
        Hotel hotel = new Hotel();
        for (Hotel x: hoteles){
            if(x.getId()==idhotel){
                hotel = x;
            }
        }
        Hotel nuevo = new Hotel();

        habitacion.getId_hotel().setId(hotel.getId());
        habitacion.getId_hotel().setNombre(hotel.getNombre());
        habitacion.getId_hotel().setPais(hotel.getPais());
        habitacion.getId_hotel().setLocalidad(hotel.getLocalidad());
        habitacion.getId_hotel().setDireccion(hotel.getDireccion());
        habitacion.getId_hotel().setFecha_apertura(hotel.getFecha_apertura());
        habitacion.getId_hotel().setFecha_cierre(hotel.getFecha_cierre());
        habitacion.getId_hotel().setNum_estrellas(hotel.getNum_estrellas());
        habitacion.getId_hotel().setTelefono(hotel.getTelefono());
        habitacion.getId_hotel().getEmail().setEmail(hotel.getEmail().getEmail());
        habitacion.getId_hotel().getEmail().setPassword(hotel.getEmail().getPassword());
        habitacion.getId_hotel().setUrl_icono(hotel.getUrl_icono());
        habitacion.getId_hotel().setUrl_imagen_general(hotel.getUrl_imagen_general());
        habitacion.getId_hotel().setLatitud(hotel.getLatitud());
        habitacion.getId_hotel().setLongitud(hotel.getLongitud());

        return habitacion;
    }


    public Integer puedeEntrar (Integer idhotel, Integer idhabitacion){
        List<Habitaciones> habitacion = habitacionesRepository.getHabitacionesById(idhabitacion);
        Integer puede = 0;
        for (Habitaciones x: habitacion){
            if(x.getId_hotel().getId().equals(idhotel)){
                puede = 1;
            }
            else{

            }
        }
        return puede;
    }
}
