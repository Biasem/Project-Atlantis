package com.example.atlantis.controller;


import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Reserva;
import com.example.atlantis.service.LoginService;
import com.example.atlantis.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ClienteHistorialController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private LoginService loginService;

    @GetMapping("/historialReservaCliente")
    public ModelAndView historial(@ModelAttribute Cliente cliente) {
        ModelAndView model = new ModelAndView("historialReservaCliente");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Cliente cliente1 = loginService.copiartodoclienteconsession(correo);
        List<Reserva> todas = reservaService.todasReservas(cliente1.getId());
        model.addObject("todas", todas);
        return model;

    }

    @PostMapping("/historialReservaCliente")
    public ModelAndView historial2(@ModelAttribute Cliente cliente) {

        ModelAndView model = new ModelAndView("hotelWeb");
        List<Reserva> todas = reservaService.todasReservas(cliente.getId());

        model.addObject("todas", todas);
        System.out.println(todas);

        return model;
    }



}
