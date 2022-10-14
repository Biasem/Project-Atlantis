package com.example.atlantis.controller;

import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Rol;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);

        List<String> listlocalidad = Arrays.asList("Sevilla", "Madrid", "Granada");
        model.addAttribute("listlocalidad", listlocalidad);

        List<String> listtiphotel = Arrays.asList("HOSTAL", "HOTEL", "APARTAHOTEL", "APARTAMENTO");
        model.addAttribute("listtiphotel", listtiphotel);

        List<String> listestrellas = Arrays.asList("1", "2", "3", "4", "5");
        model.addAttribute("listestrellas", listestrellas);


        return "registrohotel";
    }


    @PostMapping("/registrohotel")
    public String registerhotelForm(@ModelAttribute("hotel") Hotel hotel) {
        if (hotel.getNombre() != null && hotel.getDireccion() != null && hotel.getPais() != null && hotel.getLocalidad() != null
                //&& hotel.getFecha_apertura() != null && hotel.getFecha_cierre() != null
                && hotel.getTipo_hotel() != null
                && hotel.getEmail() != null && hotel.getEmail().getPassword() != null) {

            hotel.getEmail().setRol(Rol.HOTEL);
            hotelService.guardarHotel(hotel);
            System.out.println(hotel);

            return "registerhotelfinal";
        } else {

            return "redirect:/registrohotel";
        }


    }
}
