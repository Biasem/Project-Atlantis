package com.example.atlantis.controller;

import ch.qos.logback.core.net.server.Client;
import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Login;
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
   private LoginService loginService;
    @Autowired
   private ClienteService clienteService;

    @Autowired
    private HotelService hotelService;



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
        if(cliente.getNombre() != null && cliente.getApellidos() != null && cliente.getEmail().getEmail() != null &&
        cliente.getEmail().getPassword() != null && cliente.getDni() != null && clienteService.validarDNI(cliente.getDni()) != false) {

            clienteService.guardarCliente(cliente);
            System.out.println(cliente);

            return "registerfinal";

        }else{
            return "Datos incorrectos";
        }
   }


    @GetMapping("/registerhotel")
    public String registerhotelForm(Model model, @ModelAttribute Hotel hotel) {

        Hotel hotel1 = hotel;
        model.addAttribute("hotel", hotel1);

        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);

        List<String> listlocalidad = Arrays.asList("Sevilla", "Madrid", "Granada");
        model.addAttribute("listlocalidad", listlocalidad);

        List<String> listestrellas = Arrays.asList("1", "2", "3", "4", "5");
        model.addAttribute("listestrellas", listestrellas);

        List<String> listtiphotel = Arrays.asList("HOSTAL", "HOTEL", "APARTAHOTEL", "APARTAMENTO");
        model.addAttribute("listtiphotel", listtiphotel);

        return "registerprueba";
    }


    @PostMapping("/registerhotel")
    public String registerForm(@ModelAttribute("hotel") Hotel hotel) {

        hotelService.guardarHotel(hotel);
        System.out.println(hotel);

        return "registerfinal";

    }


}
