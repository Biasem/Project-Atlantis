package com.example.atlantis.service;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void guardarHotel(Hotel hotel){
        hotelRepository.save(hotel);
    }

}