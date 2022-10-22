package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.ComentarioService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.atlantis.service.ClienteService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PruebaController{

        @Autowired
        private ClienteService clienteService;

        @Autowired
        private HotelService hotelService;
        @Autowired
        private ComentarioService comentarioService;

        @RequestMapping("/equipo")
        public ModelAndView equipo(){
            ModelAndView model = new ModelAndView("equipo");
            return model ;
        }
        @GetMapping("/prueba")
        public ModelAndView prueba(HttpSession session){
            ModelAndView model = new ModelAndView("greeting");
            model.addObject("texto", new Comentario());
            return model ;
        }
        @PostMapping( "/hastalapolla")
        public ModelAndView sadge(@ModelAttribute Comentario comentario){
            Cliente dolor = new Cliente();
            Hotel sufrir = new Hotel();
            dolor.setId(2);
            sufrir.setId(2);
            comentario.setFecha(LocalDate.now());
            comentario.setId_cliente(dolor);
            comentario.setId_hotel(sufrir);
            comentarioService.guardarComentario(comentario);
            ModelAndView model = new ModelAndView("comentarioHecho");
            return model;
        }

}