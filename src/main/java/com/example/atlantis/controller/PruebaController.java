package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.ComentarioService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.atlantis.service.ClienteService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PruebaController{

        @Autowired
        private ClienteService clienteService;

        @Autowired
        private HotelService hotelService;
        @Autowired
        private ComentarioService comentarioService;

        @RequestMapping("/equipo")
        public ModelAndView equipo(HttpSession session){
            ModelAndView model = new ModelAndView("equipo");
            // Gestión sesión
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();
            Integer idCliente = 0;
            Integer idHotel = 0;
            if (correo != null){
                idCliente = clienteService.conseguirId(correo);
                idHotel = hotelService.conseguirId(correo);
                System.out.println(idCliente);
            }
            model.addObject("idHotel", idHotel);
            model.addObject("idCliente", idCliente);
            // Gestión sesión
            model.addObject("idHotel", idHotel);
            model.addObject("idCliente", idCliente);
            // Gestión sesión
            return model ;
        }
        @GetMapping("/prueba")
        public ModelAndView prueba(HttpSession session){
            ModelAndView model = new ModelAndView("greeting");
            model.addObject("texto", new Comentario());
            return model ;
        }

}