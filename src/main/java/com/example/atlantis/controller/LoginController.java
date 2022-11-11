package com.example.atlantis.controller;


import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Login;
import com.example.atlantis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;




    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "error", required = false) Boolean error) {
        if (error != null) model.addAttribute("error", true);
        return "sesion";
    }


}
