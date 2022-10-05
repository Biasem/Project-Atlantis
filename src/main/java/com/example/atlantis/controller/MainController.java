package com.example.atlantis.controller;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.service.BusquedaService;
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

    @Autowired
    private BusquedaService busquedaService;

    @GetMapping("/main")
    public ModelAndView listaHotel(@ModelAttribute Busqueda busqueda) {
        List<Hotel> listaprimera = hotelService.getAll();
        Collections.shuffle(listaprimera);
        List<Hotel> listaHotel = listaprimera.subList(0, 3);
        ModelAndView model = new ModelAndView("main");
        model.addObject("listaHotel", listaHotel);
    return model ;
    }

    @PostMapping("/main")
    public ModelAndView listaHotelPost(@ModelAttribute Busqueda busqueda) {
        List<Hotel> listaHoteles = hotelService.getAll();
        ModelAndView model = new ModelAndView("main");
        model.addObject("busqueda", busqueda);
        busquedaService.AccionBuscar(busqueda,listaHoteles);
        return model ;
    }

}
