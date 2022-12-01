package com.example.atlantis.controller;

import com.example.atlantis.model.*;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class RegisterControllerHotel {

    @Autowired
    private HotelService hotelService;


    @GetMapping("/registrohotel")
    public String registerhotelForm(Model model, @ModelAttribute Hotel hotel) {

        Hotel hotel1 = hotel;
        model.addAttribute("hotel", hotel1);

        //Listas para insertar en html las opciones que queramos
        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);

        List<String> listlocalidad = Arrays.asList("Sevilla", "Madrid", "Granada");
        model.addAttribute("listlocalidad", listlocalidad);

        List<String> listtiphotel = Arrays.asList("HOSTAL", "HOTEL", "APARTAHOTEL", "APARTAMENTO");
        model.addAttribute("listtiphotel", listtiphotel);

        List<String> listestrellas = Arrays.asList("0","1", "2", "3", "4", "5");
        model.addAttribute("listestrellas", listestrellas);

        return "registrohotel";
    }


    @PostMapping("/registrohotel")
    public String registerhotelForm(@ModelAttribute("hotel") RegisHotFech hotel) {

        try {
            //Primer if para que tenga los datos que sean obligatorios y las fechas no sean raras
            if (hotel.getNombre() != null && hotel.getDireccion() != null && hotel.getPais() != null
                    && hotel.getLocalidad() != null && hotel.getFecha_apertura() != null
                    && hotel.getFecha_cierre() != null && hotel.getTipo_hotel() != null
                    && LocalDate.parse(hotel.getFecha_cierre()).isAfter(LocalDate.parse(hotel.getFecha_apertura()))
                    && hotel.getEmail() != null && hotel.getEmail().getPassword() != null && hotel.getLatitud() != null
                    && hotel.getLongitud() != null) {

                //If para mirar si el Hotel es apartamento y tenga las estrellas a 0
                if(hotelService.siEsApartaHotel(hotel) != true){
                    hotel.setNum_estrellas(0);
                }

                //Funcion que guarda hotel
                hotelService.guardarHotel(hotelService.convertirAHotel(hotel));
                System.out.println(hotelService.convertirAHotel(hotel));

                return "redirect:/main";

            } else {

                return "redirect:/registrohotel";

            }
        }catch (Exception e){

            return "redirect:/registrohotel";

        }

    }
}
