package com.example.atlantis.controller;

import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.atlantis.service.ClienteService;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PruebaController{

        @Autowired
        private ClienteService clienteService;

        @Autowired
        private HotelService hotelService;


        @RequestMapping("/equipo")
        public ModelAndView equipo(){

            ModelAndView model = new ModelAndView("equipo");

            // Gestión sesión
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();
            Integer idCliente = 0;
            Integer idHotel = 0;

            if (correo != null){
                idCliente = clienteService.conseguirId(correo);
                idHotel = hotelService.conseguirId(correo);
            }

            model.addObject("idHotel", idHotel);
            model.addObject("idCliente", idCliente);

            return model ;
        }
}