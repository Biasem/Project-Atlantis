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

    @SchemaMapping(typeName = "Mutation", value = "registerForm")
    public String registerForm(@RequestBody @Argument(name = "input") GraphqlInput.ClienteInput input) {

        try {
            //If para verificar que los datos introducidos sean tal cual se necesite
            if (input.getNombre() != null && input.getApellidos() != null
                    && input.getEmail().getEmail() != null &&
                    input.getEmail().getPassword() != null && input.getDni() != null
                    && clienteService.validarDNI(input.getDni()) != false) {

                //Selección de Rol Cliente para el nuevo cliente
                input.getEmail().setRol(GraphqlInput.RolInput.CLIENTE);
                input.getEmail().setPassword(bCryptPasswordEncoder.encode(input.getEmail().getPassword()));

                Cliente cliente = new Cliente();
                cliente.setApellidos(input.getApellidos());
                cliente.setNombre(input.getNombre());
                cliente.setDni(input.getDni());
                cliente.setPais(input.getPais());
                cliente.setTelefono(input.getTelefono());
                Login login = new Login();
                cliente.setEmail(login);
                cliente.getEmail().setEmail(input.getEmail().getEmail());
                cliente.getEmail().setPassword(input.getEmail().getPassword());
                cliente.getEmail().setRol(Rol.CLIENTE);


                //Guardado del cliente en base de datos
                clienteService.guardarCliente(cliente);
                System.out.println(input);

                return "redirect:/main";

            } else {
                return "redirect:/register";
            }
        }catch (Exception e){
            return "redirect:/register";
        }
    }




}
