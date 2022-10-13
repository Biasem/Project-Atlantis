package com.example.atlantis.service;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.TipoRegimen;
import com.example.atlantis.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<TipoRegimen> todoregimen(){
        List<TipoRegimen> regimen = new ArrayList<>();
        regimen.add(TipoRegimen.DESAYUNO);
        regimen.add(TipoRegimen.MEDIA_PENSION);
        regimen.add(TipoRegimen.SIN_PENSION);
        regimen.add(TipoRegimen.PENSION_COMPLETA);
        regimen.add(TipoRegimen.TODO_INCLUIDO);
        return regimen;
    }

}