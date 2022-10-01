package com.example.atlantis.controller;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.atlantis.model.Cliente;
import com.example.atlantis.service.ClienteService;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController{

    @Autowired
    private HotelService hotelService;

    @RequestMapping("/main")
    public ModelAndView listaHotel(){
        Integer numero = new Integer(1);
        List<Hotel> listaprimera= hotelService.getAll();
        List<Hotel> listaHotel = listaprimera.subList(0, 2);
        ModelAndView model = new ModelAndView("main");
        model.addObject("listaHotel", listaHotel);
        return model ;
    }

    @GetMapping("/login")
    public String login(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "sesion";
    }
}
