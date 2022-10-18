package com.example.atlantis.controller;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Rol;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.HotelService;
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
   private ClienteService clienteService;


    @GetMapping("/register")
    public String registerForm(Model model, @ModelAttribute Cliente cliente) {

        Cliente cliente1 = cliente;

        model.addAttribute("cliente", cliente1);

        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);

        return "registrocliente";
    }

    @PostMapping("/registrocliente")
    public String registerForm(@ModelAttribute("cliente") Cliente cliente) {
        if(cliente.getNombre() != null && cliente.getApellidos() != null
                && cliente.getEmail().getEmail() != null &&
        cliente.getEmail().getPassword() != null && cliente.getDni() != null
                && clienteService.validarDNI(cliente.getDni()) != false) {

            cliente.getEmail().setRol(Rol.CLIENTE);
            clienteService.guardarCliente(cliente);
            System.out.println(cliente);

            return "redirect:/main";

        }else{


            return "redirect:/register";
        }
   }





}
