package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.ComentarioService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class clienteController{

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private HotelService hotelService;
    @Autowired
    private ComentarioService comentarioService;

    @RequestMapping("/perfilcliente")
    public ModelAndView perfil(HttpSession session){
        ModelAndView model = new ModelAndView("perfilCliente");
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
        Cliente cliente = clienteService.getById(idCliente);
        List<Comentario> comentarios = new ArrayList<>();
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        model.addObject("cliente", cliente);
        model.addObject("correo", correo);
        model.addObject("comentarios",comentarioService.conseguirComentariosCliente(idCliente));
        return model;
    }
}