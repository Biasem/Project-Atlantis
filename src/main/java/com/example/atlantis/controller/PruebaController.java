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
public class PruebaController{

    @RestController
    public class HamburguesaController {
        @Autowired
        private ClienteService clienteService;

        @Autowired
        private HotelService hotelService;

        @RequestMapping("/clientes")
        public List<Hotel> getCliente(){
            return hotelService.getAll() ;
        }
        @RequestMapping("/greeting")
        public ModelAndView listClientes(){
            List<Cliente> listClientes= clienteService.getAll();
            ModelAndView model = new ModelAndView("greeting");
            model.addObject("listClientes", listClientes);
            return model ;
        }


    }

}