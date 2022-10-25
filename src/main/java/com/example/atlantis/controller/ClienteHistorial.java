package com.example.atlantis.controller;


import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Reserva;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClienteHistorial {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/historialReservaCliente")
    public ModelAndView historial(@ModelAttribute Cliente cliente) {
        ModelAndView model = new ModelAndView("historialReservaCliente");
        model.addObject("cliente", cliente);
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
