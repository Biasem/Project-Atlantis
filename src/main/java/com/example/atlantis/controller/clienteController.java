package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.ComentarioService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.atlantis.service.ClienteService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class clienteController{

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private HotelService hotelService;
    @Autowired
    private ComentarioService comentarioService;

    @RequestMapping("/perfilcliente")
    public ModelAndView perfil(HttpSession session){
        ModelAndView model = new ModelAndView("perfilCliente");
        // Gestión sesión
        Login usuario = new Login();
        usuario = (Login) session.getAttribute("user");
        Integer idCliente = 0;
        Integer idHotel = 0;
        if (usuario != null){
            idCliente = clienteService.conseguirId(usuario);
            idHotel = hotelService.conseguirId(usuario);
            System.out.println(idCliente);
        }
        // Gestión sesión

        if(idCliente==0){
            if (idHotel>0){
                ModelAndView no = new ModelAndView("noDeberias");
                return no;
            }
            else{
                ModelAndView inicia = new ModelAndView("iniciaSesion");
                return inicia;
            }
        }
        if (idCliente>0){
            Cliente cliente = clienteService.getById(idCliente);
            List<Comentario> comentarios = new ArrayList<>();
            model.addObject("idHotel", idHotel);
            model.addObject("idCliente", idCliente);
            model.addObject("cliente", cliente);
            model.addObject("usuario", usuario);
            model.addObject("comentarios",comentarioService.conseguirComentariosCliente(idCliente));
            return model;
        }


        return new ModelAndView("redirect:/main");
    }


}