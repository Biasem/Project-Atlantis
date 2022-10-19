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

        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);

        return "editarcliente";
    }


    @PostMapping("/editarcliente")
    public String editarCliente2(@ModelAttribute Cliente cliente) {

        clienteService.editarCliente(cliente);

        return "redirect:/main";
    }



}
