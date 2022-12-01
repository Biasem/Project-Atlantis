package com.example.atlantis.service;

import com.example.atlantis.model.BuscadorID;
import com.example.atlantis.model.Hotel;
import org.springframework.stereotype.Service;
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
