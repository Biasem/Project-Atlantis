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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
public class SesionController{
    @Autowired
    private HotelService hotelService;

    @Autowired
    private BusquedaService busquedaService;

    @GetMapping("/login")
    public ModelAndView listaHotel() {
        ModelAndView model = new ModelAndView("sesion");
        return model;
    }
}
