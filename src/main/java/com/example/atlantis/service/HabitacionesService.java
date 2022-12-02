package com.example.atlantis.service;

import com.example.atlantis.model.*;
import com.example.atlantis.repository.HabitacionesRepository;
import com.example.atlantis.repository.HotelRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitacionesService {
    private static Faker faker = new Faker();

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
        }

        return habitauwu;
    }

    public Habitaciones conseguirHabitacion (Integer numero, List<Habitaciones> lista){

        Habitaciones habitauwu = new Habitaciones();

        for (Habitaciones x: lista){
            if (x.getId().equals(numero)){
                habitauwu = x;
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

    public Habitaciones guardarHabitacion(Habitaciones habitacion){
        return habitacionesRepository.save(habitacion);
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


    public Integer puedeEntrar (Integer idhotel, Integer idhabitacion){

        List<Habitaciones> habitacion = habitacionesRepository.getHabitacionesById(idhabitacion);
        Integer puede = 0;

        for (Habitaciones x: habitacion){
            if(x.getId_hotel().getId().equals(idhotel)){
                puede = 1;
            }
        }

        return puede;
    }

    public Habitaciones crearHabitacion(Hotel hotel){

        Habitaciones habitacion = new Habitaciones();
        habitacion.setId_hotel(hotel);
        habitacion.setHab_ocupadas(faker.number().numberBetween(1,201));
        habitacion.setNum_hab(faker.number().numberBetween(1,201));

        while(habitacion.getHab_ocupadas()>habitacion.getNum_hab()){
            habitacion.setHab_ocupadas(faker.number().numberBetween(1,201));
            habitacion.setNum_hab(faker.number().numberBetween(1,201));
        }

        habitacion.setTipo_hab(Arrays.stream(TipoHab.values()).collect(Collectors.toList()).get(faker.number().numberBetween(0,4)));

       return habitacion;
    }
}
