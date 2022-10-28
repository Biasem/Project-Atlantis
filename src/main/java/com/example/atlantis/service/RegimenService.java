package com.example.atlantis.service;


import com.example.atlantis.model.Habitaciones;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Regimen;
import com.example.atlantis.model.TipoRegimen;
import com.example.atlantis.repository.HotelRepository;
import com.example.atlantis.repository.RegimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegimenService {

    @Autowired
    private RegimenRepository regimenRepository;
    @Autowired
    private HotelRepository hotelRepository;


    public List<Regimen> getAll(){
        return regimenRepository.findAll();
    }

    public Regimen getById(int id){
        return regimenRepository.findById(id).orElse(null);
    }

    public List<Regimen> regimenHotel (Integer id){
        List<Regimen> lista = regimenRepository.findAll();
        List<Regimen> regimenesHotel = new ArrayList<>();
        for (Regimen x: lista){
            if (x.getId_hotel().getId().equals(id)){
                regimenesHotel.add(x);
            }
        }
        return regimenesHotel;
    }

    public List<TipoRegimen> todoRegimen (){
        List<TipoRegimen> regimen = new ArrayList<>();
        regimen.add(TipoRegimen.DESAYUNO);
        regimen.add(TipoRegimen.MEDIA_PENSION);
        regimen.add(TipoRegimen.SIN_PENSION);
        regimen.add(TipoRegimen.TODO_INCLUIDO);
        regimen.add(TipoRegimen.PENSION_COMPLETA);
        return regimen;
    }

    public List<Hotel> conseguirHotel (Integer idHotel){
        List<Hotel> hoteles = hotelRepository.findAll();
        List<Hotel> hotel = new ArrayList<>();
        for (Hotel x: hoteles){
            if(x.getId().equals(idHotel)){
                hotel.add(x);
            }
        }
        return hotel;
    }

    public void guardarRegimen (Regimen regimen){
        regimenRepository.save(regimen);
    }

}
