package com.example.atlantis.controller;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class MainController{

    @Autowired
    private HotelService hotelService;

    @RequestMapping("/main")
    public ModelAndView listaHotel(@ModelAttribute Busqueda busqueda){
        List<Hotel> listaprimera= hotelService.getAll();
        Collections.shuffle(listaprimera);
        List<Hotel> listaHotel = listaprimera.subList(0, 3);
        ModelAndView model = new ModelAndView("main");
        model.addObject("listaHotel", listaHotel);

        model.addObject("busqueda", new Busqueda());
        model.addObject("busqueda", busqueda);

        //System.out.println(busqueda.getHotelBuscar());
        //System.out.println(busqueda.getFechaInicial());
        //System.out.println(busqueda.getFechaFinal());
        //System.out.println(busqueda.getNumHuespedes());




        return model ;
    }

    @GetMapping("/login")
    public String login(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "sesion";
    }
}
