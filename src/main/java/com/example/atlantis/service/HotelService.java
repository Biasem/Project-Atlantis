package com.example.atlantis.service;

import com.example.atlantis.model.*;
import com.example.atlantis.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;


    public List<Hotel> getAll(){
        return hotelRepository.findAll();
    }

    public Hotel getById(int id){
        return hotelRepository.findById(id).orElse(null);

    }


    //Convertir el Hotel de un modelo de obtención de datos  al modelo real para meter en bbdd
    public Hotel convertirAHotel(RegisHotFech hotel){

        //Selección de ROL Hotel para el nuevo Hotel
        hotel.getEmail().setRol(Rol.HOTEL);

        Hotel hotel1 = new Hotel();

        //Introducción  de datos en el modelo real para insertar en bbdd
        hotel1.setNombre(hotel.getNombre());
        hotel1.setPais(hotel.getPais());
        hotel1.setLocalidad(hotel.getLocalidad());
        hotel1.setDireccion(hotel.getDireccion());
        hotel1.setFecha_apertura(LocalDate.parse(hotel.getFecha_apertura()));
        hotel1.setFecha_cierre(LocalDate.parse(hotel.getFecha_cierre()));
        hotel1.setNum_estrellas(hotel.getNum_estrellas());
        hotel1.setTelefono(hotel.getTelefono());
        hotel1.setTipo_hotel(hotel.getTipo_hotel());
        hotel1.setUrl_icono(hotel.getUrl_icono());
        hotel1.setUrl_imagen_general(hotel.getUrl_imagen_general());
        hotel1.setEmail(hotel.getEmail());

        return hotel1;
    }


    //Función para verificar si el Hotel es Apartamento o no, devuelve un boolean según sea
    public boolean siEsApartaHotel(RegisHotFech hotel){

        boolean i = false;

        if(hotel.getTipo_hotel().equals(TipoHotel.APARTAMENTO)){
            i = false;
        }else{
            i = true;
        }
        return i;
    }


    public void guardarHotel(Hotel hotel){
        hotelRepository.save(hotel);
    }

    public List<TipoRegimen> todoregimen(){
        List<TipoRegimen> regimen = new ArrayList<>();
        regimen.add(TipoRegimen.DESAYUNO);
        regimen.add(TipoRegimen.MEDIA_PENSION);
        regimen.add(TipoRegimen.SIN_PENSION);
        regimen.add(TipoRegimen.PENSION_COMPLETA);
        regimen.add(TipoRegimen.TODO_INCLUIDO);
        return regimen;
    }


    public void borrarHotel(Hotel hotel){
       List<Hotel> todos = hotelRepository.findAll();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().equals(hotel.getEmail())){

                hotelRepository.delete(hotel);
            }
        }
    }

    public Hotel copiartodohotel(Hotel hotel){
        List<Hotel> todos = hotelRepository.findAll();
        Hotel hotel1 = new Hotel();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().getEmail().equals(hotel.getEmail().getEmail())){
                hotel1 = todos.get(i);
            }
        }
        return hotel1;
    }


}