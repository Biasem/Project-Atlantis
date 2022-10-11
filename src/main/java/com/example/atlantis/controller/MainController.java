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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
        model.addObject("fechamin", LocalDate.now());
        model.addObject("busqueda", busqueda);
        return model;
    }

    @PostMapping("/main")
    public ModelAndView listaHoteles(@ModelAttribute Busqueda busqueda) {
        List<Hotel> listaHoteles = hotelService.getAll();
        ModelAndView model = new ModelAndView("resultado");
        List<Hotel> filtro = busquedaService.AccionBuscar(busqueda,listaHoteles);
        if(LocalDate.parse(busqueda.getFechaInicial()).isAfter(LocalDate.parse(busqueda.getFechaFinal())))
        {
            return new ModelAndView("redirect:main");
        }
        model.addObject("fechamin", LocalDate.now());
        model.addObject("filtro", filtro);
        System.out.println(busqueda.getHotelBuscar());
        return model ;
    }

    @GetMapping("/")
    public String irAMain() {
        return "main";
    }
}
