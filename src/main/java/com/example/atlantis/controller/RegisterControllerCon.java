package com.example.atlantis.controller;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class RegisterControllerCon {

    private RegisterController registerController;


    @GetMapping("/register")
    public String registerForm(Model model, @ModelAttribute Cliente cliente, Login login) {

        Cliente cliente1 = cliente;
        Login login1 = login;

        model.addAttribute("cliente", cliente1);
        model.addAttribute("login", login1);

        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);

        return "registerprueba";
    }

    @PostMapping("/register")
    public String registerForm(@ModelAttribute("cliente") Cliente cliente, @ModelAttribute("login") Login login) {





        registerController.guardarCliente(cliente, login);


        return "main";
    }

}
