package com.example.atlantis.controller;
import com.example.atlantis.model.Busqueda;
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
public class PruebaController{

        @Autowired
        private ClienteService clienteService;

        @Autowired
        private HotelService hotelService;

        @GetMapping("/busqueda")
        public ModelAndView getBusqueda(@ModelAttribute List<Hotel> listaHoteles){
            ModelAndView model = new ModelAndView("codigosucio");
            model.addObject("listaHotel", listaHoteles);
            return model;

        }
        @RequestMapping("/greeting")
        public ModelAndView listClientes(){
            List<Cliente> listClientes= clienteService.getAll();
            ModelAndView model = new ModelAndView("greeting");
            model.addObject("listClientes", listClientes);
            return model ;
        }

}