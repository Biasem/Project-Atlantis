package com.example.atlantis.controller;
import com.example.atlantis.model.BuscadorID;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.service.BusquedaService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
public class webHotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private BusquedaService busquedaService;

    @GetMapping("/hotel")
    public ModelAndView resultadoHotel(@ModelAttribute BuscadorID numero) {
        Hotel resultado = new Hotel();
        resultado = hotelService.getById(numero.getNumID());
        ModelAndView model = new ModelAndView("webHotel");
        model.addObject("resultado", resultado);
        return model;
    }

    @PostMapping("/resultado")
    public ModelAndView listaHotelPost(@ModelAttribute Busqueda busqueda) {
        List<Hotel> listaHoteles = hotelService.getAll();
        ModelAndView model = new ModelAndView("resultado");
        model.addObject("busqueda", busqueda);
        busquedaService.AccionBuscar(busqueda,listaHoteles);
        return model ;
    }
}
