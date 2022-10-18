package com.example.atlantis.controller;


import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClienteHistorial {

    @GetMapping("/historialReservaCliente")
    public ModelAndView historial(@ModelAttribute Cliente cliente) {
        ModelAndView model = new ModelAndView("historialReservaCliente");
        model.addObject("cliente", cliente);
        return model;

    }


}
