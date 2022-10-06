package com.example.atlantis.controller;


import com.example.atlantis.model.Login;
import com.example.atlantis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
public class LoginController {

    private LoginService loginService;

//    @GetMapping("/login")
//    public ModelAndView listaHotel(@ModelAttribute Login login) {
//        List<Login> listaprimera = loginService.getAll();
//        Collections.shuffle(listaprimera);
//        ModelAndView model = new ModelAndView("main");
//        return model;
//    }


    @PostMapping("/login")
    public String submitForm(@ModelAttribute("login") Login login) {
        List<Login> listaprimera = loginService.getAll();
        ModelAndView model = new ModelAndView("main");
        model.addObject("login", login);
        boolean cierto = loginService.Buscar(login);
        if(cierto = true){
            return "Bienvenido";
        }else {
            return "login";

        }
    }

}
