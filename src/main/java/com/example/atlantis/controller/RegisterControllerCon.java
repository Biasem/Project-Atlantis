package com.example.atlantis.controller;

import ch.qos.logback.core.net.server.Client;
import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Login;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class RegisterControllerCon {

    @Autowired
   private LoginService loginService;
    @Autowired
   private ClienteService clienteService;


    @GetMapping("/register")
    public String registerForm(Model model, @ModelAttribute Cliente cliente) {

        Cliente cliente1 = cliente;
//        Login login1 = login;

        model.addAttribute("cliente", cliente1);
//        model.addAttribute("login", login1);

        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);

        return "registerprueba";
    }

    @PostMapping("/register")
    public String registerForm(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.guardarCliente(cliente);
        loginService.guardarLogin(cliente.getEmail());
        System.out.println(cliente);

        return "registerfinal";
    }

}
