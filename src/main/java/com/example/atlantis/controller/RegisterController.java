package com.example.atlantis.controller;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Login;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/login")
public class RegisterController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private LoginService loginService;



    @PostMapping("/guardar")
    public String guardarCliente(@RequestBody Cliente cliente, Login login){
        clienteService.guardarCliente(cliente);
        loginService.guardarLogin(login);
        return "Datos guardados correctamente";
    }


}
