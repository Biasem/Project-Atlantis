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
public class MainController{

    @GetMapping("/main")
    public String main(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greetings";
    }

    @Autowired
    private ClienteService clienteService;

    @RequestMapping("/clientes")
    public List<Cliente> getCliente(){
        return clienteService.getAll() ;
    }
    @RequestMapping("/listaClientes")
    public ModelAndView listclientes(){
        List<Cliente> listClientes= clienteService.getAll();
        ModelAndView model = new ModelAndView("greeting");
        model.addObject("greeting", listClientes);
        return model ;
    }

    @PostMapping("/clientes/save")
    public void guardarCliente(@RequestBody Cliente humano){
        Cliente clientefinal = clienteService.getById(humano.getId());

    }

}
