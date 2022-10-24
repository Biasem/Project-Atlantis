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
        public ModelAndView equipo(HttpSession session){
            ModelAndView model = new ModelAndView("equipo");
            // Gestión sesión
            Login usuario = new Login();
            usuario = (Login) session.getAttribute("user");
            Integer idCliente = 0;
            Integer idHotel = 0;
            if (usuario != null){
                idCliente = clienteService.conseguirId(usuario);
                idHotel = hotelService.conseguirId(usuario);
                System.out.println(idCliente);
            }
            model.addObject("idHotel", idHotel);
            model.addObject("idCliente", idCliente);
            model.addObject("usuario", usuario);
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