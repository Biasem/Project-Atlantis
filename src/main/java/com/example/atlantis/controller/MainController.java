package com.example.atlantis.controller;

import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController{

    @Autowired
    private HotelService hotelService;


    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("busqueda", new Busqueda());

        return "main";
    }
    @PostMapping("/main")
    public String mainSubmit(@ModelAttribute Busqueda busqueda , Model model) {
        model.addAttribute("busqueda", busqueda);
        System.out.println(busqueda.getHotelBuscar());
        return "main";
    }

}
