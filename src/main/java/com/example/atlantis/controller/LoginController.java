package com.example.atlantis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "error", required = false) Boolean error) {
        if (error != null) model.addAttribute("error", true);
        return "sesion";
    }


}
