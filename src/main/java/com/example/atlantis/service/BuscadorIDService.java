package com.example.atlantis.service;
import com.example.atlantis.model.BuscadorID;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Service
public class BuscadorIDService {
    public Hotel Comparar (BuscadorID numero, List<Hotel> lista){
        Hotel resultado = new Hotel();
        for (Hotel x:lista){
            if (x.getId() == numero.getNumID()){
                resultado = x;
            }
            else{
            }
        }
        return resultado;
    }
}
