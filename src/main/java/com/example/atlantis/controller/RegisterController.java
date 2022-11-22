//package com.example.atlantis.controller;
//
//import com.example.atlantis.model.Cliente;
//import com.example.atlantis.model.Hotel;
//import com.example.atlantis.model.Login;
//import com.example.atlantis.service.ClienteService;
//import com.example.atlantis.service.HotelService;
//import com.example.atlantis.service.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//@RequestMapping("/login")
//public class RegisterController {
//
//    @Autowired
//    private ClienteService clienteService;
//    @Autowired
//    private LoginService loginService;
//
//    @Autowired
//    private HotelService hotelService;
//
//
//    @PostMapping("/guardar")
//    public String guardarCliente(@RequestBody Cliente cliente){
//        clienteService.guardarCliente(cliente);
//        loginService.guardarLogin(cliente.getEmail());
//        return "Datos guardados correctamente";
//    }
//
//    @PostMapping("/guardarhotel")
//    public String guardarHotel(@RequestBody Hotel hotel){
//        hotelService.guardarHotel(hotel);
//        loginService.guardarLogin(hotel.getEmail());
//        return "Datos guardados correctamente";
//    }
//
//}
