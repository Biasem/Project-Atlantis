package com.example.atlantis.controller;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;


@Controller
public class GestionClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping("/borrarcliente")
    public String deleteCliente(@ModelAttribute Cliente cliente) {
        ModelAndView model = new ModelAndView("cliente");
        model.addObject("cliente", cliente);
        return "borrarcliente";
    }

    @PostMapping("/borrarcliente")
    public String deleteCliente2(@ModelAttribute Cliente cliente) {

        Cliente cliente1 = clienteService.copiartodocliente(cliente);
        clienteService.borrarCliente(cliente1);

        return "redirect:/main";
    }


    @GetMapping("/editarcliente")
    public String editarCliente(Model model,@ModelAttribute Cliente cliente) {


        model.addAttribute("cliente", cliente);

        //Listas para introducir en el html los paises que queremos que salgan
        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);
        return "editarcliente";
    }


    @PostMapping("/editarcliente")
    public String editarCliente2(@ModelAttribute Cliente cliente) {
        //If para verificar que los datos introducidos sean tal cual se necesite
        if(cliente.getNombre() != null && cliente.getApellidos() != null
                && cliente.getEmail().getEmail() != null &&
                cliente.getEmail().getPassword() != null && cliente.getDni() != null
                && clienteService.validarDNI(cliente.getDni()) != false) {

            //Introduccion de datos a Service para meter en ddbb
        clienteService.editarCliente(cliente);
            return "redirect:/main";

        }else{

            return "redirect:/editarcliente";
        }
    }


}
