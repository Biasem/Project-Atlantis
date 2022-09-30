package com.example.atlantis.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.atlantis.model.Cliente;
import com.example.atlantis.service.ClienteService;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PruebaController{

    @RestController
    public class HamburguesaController {
        @Autowired
        private ClienteService clienteService;

        @RequestMapping("/clientes")
        public List<Cliente> getCliente(){
            return clienteService.getAll() ;
        }
        @RequestMapping("/listaClientes")
        public ModelAndView listClientes(){
            List<Cliente> listClientes= clienteService.getAll();
            ModelAndView model = new ModelAndView("listClientes");
            model.addObject("listClientes", listClientes);
            return model ;
        }

        @PostMapping("/clientes/save")
        public void guardarCliente(@RequestBody Cliente humano){
            Cliente clientefinal = clienteService.getById(humano.getId());

        }
    }

}
