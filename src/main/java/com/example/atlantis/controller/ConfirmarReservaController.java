package com.example.atlantis.controller;

import com.example.atlantis.model.Objeto_Aux_Reserva_html;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ConfirmarReservaController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private HotelService hotelService;

    @GetMapping("/confirmarreserva")
    public ModelAndView confirmarReserva(){
        ModelAndView model = new ModelAndView("pagarReserva");
        // Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idCliente = 0;
        Integer idHotel = 0;
        if (correo != null){
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        // Gestión sesión
        System.out.println(1);

        return model;
    }


}
