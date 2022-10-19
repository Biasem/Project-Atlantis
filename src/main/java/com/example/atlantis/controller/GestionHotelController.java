package com.example.atlantis.controller;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Login;
import com.example.atlantis.model.Reserva;
import com.example.atlantis.service.HotelService;
import com.example.atlantis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
public class GestionHotelController {


    @Autowired
    private HotelService hotelService;


    @GetMapping("/borrarhotel")
    public String deleteHotel(@ModelAttribute Hotel hotel) {
        ModelAndView model = new ModelAndView("delete");
        model.addObject("hotel", hotel);
        return "borrarhotel";
    }

    @PostMapping("/borrarhotel")
    public String deleteHotel2(@ModelAttribute Hotel hotel) {

        Hotel hotel1 = hotelService.copiartodohotel(hotel);
        hotelService.borrarHotel(hotel1);

        return "redirect:/main";
    }





}
