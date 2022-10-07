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
@Autowired
    private LoginService loginService;


    @GetMapping("/login")
    public ModelAndView submitForm(@ModelAttribute Login login) {
        List<Login> listaprimera = loginService.getAll();
        ModelAndView model = new ModelAndView("sesion");
        model.addObject("login", login);
        return model;

    }
    @PostMapping("/login")
    public ModelAndView submitForm1(@ModelAttribute Login login) {
        //List<Login> listaprimera = loginService.getAll();
        ModelAndView model = new ModelAndView("sesion");
        model.addObject("login", login);
        ModelAndView model1 = new ModelAndView("main");
        model1.addObject("login", login);
        boolean cierto = loginService.Buscar(login);
        if(cierto != true){
            return model;
        }else {
            return model1;

        }
    }


}
