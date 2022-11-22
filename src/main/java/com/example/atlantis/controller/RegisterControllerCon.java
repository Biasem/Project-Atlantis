package com.example.atlantis.controller;

import com.example.atlantis.model.*;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.HotelService;
import com.example.atlantis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class RegisterControllerCon {


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
   private ClienteService clienteService;





    @GetMapping("/register")
    public String registerForm(Model model, @ModelAttribute Cliente cliente) {


        Cliente cliente1 = cliente;
        model.addAttribute("cliente", cliente1);

        //Listas para introducir en el html los paises que queremos que salgan
        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);

        return "registrocliente";
    }

    @PostMapping("/registrocliente")
    public String registerForm(@ModelAttribute("cliente") Cliente cliente) {

        try {
            //If para verificar que los datos introducidos sean tal cual se necesite
            if (cliente.getNombre() != null && cliente.getApellidos() != null
                    && cliente.getEmail().getEmail() != null &&
                    cliente.getEmail().getPassword() != null && cliente.getDni() != null
                    && clienteService.validarDNI(cliente.getDni()) != false) {

                //Selección de Rol Cliente para el nuevo cliente
                cliente.getEmail().setRol(Rol.CLIENTE);
                cliente.getEmail().setPassword(bCryptPasswordEncoder.encode(cliente.getEmail().getPassword()));



                //Guardado del cliente en base de datos
                clienteService.guardarCliente(cliente);
                System.out.println(cliente);

                return "redirect:/main";

            } else {
                return "redirect:/register";
            }
        }catch (Exception e){
            return "redirect:/register";
        }
    }







}
