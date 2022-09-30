package com.example.atlantis.controller;

import com.example.atlantis.model.Busqueda;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
public class MainController{

    @GetMapping("/main")
    public String greeting(Model model) {
        model.addAttribute("busqueda", new Busqueda());
        return "main";
    }
    @PostMapping("/main")
    public String greetingSubmit(@ModelAttribute Busqueda busqueda, Model model) {
        model.addAttribute("busqueda", busqueda);
        System.out.println(busqueda.getHotelBuscar());
        System.out.println(busqueda.getFechaInicial());
        System.out.println(busqueda.getFechaFinal());
        System.out.println(busqueda.getNumHuespedes());
        System.out.println(new Date().toString());
        return "main";
    }



}
