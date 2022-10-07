package com.example.atlantis.controller;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Login;
import com.example.atlantis.service.ClienteService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/login")
public class RegisterController {


    private ClienteService clienteService;


    @GetMapping("/register")
    public ModelAndView submitForm(@ModelAttribute Cliente cliente) {
        ModelAndView model = new ModelAndView("sesion");
        model.addObject("cliente", cliente);

        return model;
    }

    @PostMapping("/guardar")
    public String guardarIngrediente(@RequestBody Cliente cliente){
        clienteService.guardarCliente(cliente);
        return "Datos guardados correctamente";
    }


}
