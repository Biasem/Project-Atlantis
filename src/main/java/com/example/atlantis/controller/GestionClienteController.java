package com.example.atlantis.controller;

import com.example.atlantis.model.*;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;


@Controller
public class GestionClienteController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private LoginService loginService;

    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private ReservaService reservaService;


    @GetMapping("/borrarcliente")
    public String deleteCliente(Model model,@ModelAttribute Cliente cliente) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Cliente cliente1 = loginService.copiartodoclienteconsession(correo);
        model.addAttribute("cliente", cliente1);
        return "borrarcliente";
    }

    @PostMapping("/borrarcliente")
    public String deleteCliente2(@ModelAttribute Cliente cliente) {

        //Encriptado y recogida de datos de al sesión apra copiar todo el modelo
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();

        Cliente cliente2 = loginService.copiartodoclienteconsession(correo);

        //If para saber si los datos estan correctos y pueden borrar o no
        if (cliente2.getEmail().getEmail().equals(cliente.getEmail().getEmail()) && encoder.matches(cliente.getEmail().getPassword(), cliente2.getEmail().getPassword())) {
            clienteService.borrarCliente(cliente);

            return "redirect:/logout";
        }
        else {
            return "redirect:/borrarcliente";
        }
    }


    @GetMapping("/editarcliente")
    public String editarCliente(Model model, @ModelAttribute Cliente cliente) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Cliente cliente1 = loginService.copiartodoclienteconsession(correo);
        model.addAttribute("cliente", cliente1);


        //Listas para introducir en el html los paises que queremos que salgan
        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);


        return "editarcliente";
    }


    @PostMapping("/editarcliente")
    public String editarCliente2(@ModelAttribute Cliente cliente) {
        //If para verificar que los datos introducidos sean tal cual se necesite
        if(cliente.getNombre() != null && cliente.getApellidos() != null
                && clienteService.validarDNI(cliente.getDni()) != false) {

            //Recogida datos de sesión e insertarlos en el modelo
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();
            Cliente datos = loginService.cogeridcliente(correo);
            cliente.setId(datos.getId());
            cliente.setEmail(new Login());
            cliente.getEmail().setPassword(datos.getEmail().getPassword());
            cliente.getEmail().setRol(datos.getEmail().getRol());
            cliente.getEmail().setEmail(datos.getEmail().getEmail());



            //Introduccion de datos a Service para meter en ddbb
        clienteService.editarCliente(cliente);
            return "redirect:/main";

        }else{

            return "redirect:/editarcliente";
        }
    }


}
