package com.example.atlantis.controller;

import com.example.atlantis.model.*;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
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
    @SchemaMapping(typeName = "Mutation", value = "registerhotelForm")
    public String registerhotelForm(@RequestBody @Argument(name = "input") GraphqlInput.RegisHotFechInput input) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        try {
        //Primer if para que tenga los datos que sean obligatorios y las fechas no sean raras
        if (input.getNombre() != null && input.getDireccion() != null && input.getPais() != null
                && input.getLocalidad() != null && input.getFecha_apertura() != null
                && input.getFecha_cierre() != null && input.getTipo_hotel() != null
//                && LocalDate.parse(input.getFecha_cierre()).isAfter(LocalDate.parse(input.getFecha_apertura()))
                && input.getEmail() != null && input.getEmail().getPassword() != null) {

            //If para mirar si el Hotel es apartamento y tenga las estrellas a 0
            if(hotelService.siEsApartaHotel(input) != true){
                input.setNum_estrellas(0);
            }

            //Funcion que guarda hotel
            hotelService.guardarHotel(hotelService.convertirAHotel(input));
            System.out.println(hotelService.convertirAHotel(input));

            return "redirect:/main";
        } else {

            return "redirect:/registrohotel";
        }
        }catch (Exception e){
            return "redirect:/registrohotel";
        }

    }
}
