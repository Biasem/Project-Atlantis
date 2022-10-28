package com.example.atlantis.controller;


import com.example.atlantis.model.*;
import com.example.atlantis.service.Habitacion_Reserva_HotelService;
import com.example.atlantis.service.LoginService;
import com.example.atlantis.service.RegimenService;
import com.example.atlantis.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ClienteHistorialController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private LoginService loginService;

    @Autowired
    private RegimenService regimenService;

    @Autowired
    private Habitacion_Reserva_HotelService habitacion_reserva_hotelService;


    //Todas las reservas
    @GetMapping("/historialReservaCliente")
    public ModelAndView historial(@ModelAttribute Cliente cliente) {
        ModelAndView model = new ModelAndView("historialReservaCliente");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        //Obtención de listas para metodo
        Cliente cliente1 = loginService.copiartodoclienteconsession(correo);
        List<Reserva> todas = reservaService.todasReservas(cliente1.getId());
        List<Hab_Reserva_Hotel> todasReservaporHab = habitacion_reserva_hotelService.getAll();
        List<Regimen> todosregimen = regimenService.getAll();
        //Lista que se devuelve con método de búsqueda
        List<HistorialReservaClientes> todasmodelohistorial = reservaService.cambiomodelohistorial(todas, todasReservaporHab, todosregimen);
        model.addObject("todas", todasmodelohistorial);

        return model;

    }


    //Reservas Vigentes
    @GetMapping("/historialReservaClienteVigentes")
    public ModelAndView historialVigente(@ModelAttribute Cliente cliente) {
        ModelAndView model = new ModelAndView("historialReservaCliente");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Cliente cliente1 = loginService.copiartodoclienteconsession(correo);
        //Obtención de listas para metodo
        List<Reserva> todas = reservaService.todasReservas(cliente1.getId());
        List<Hab_Reserva_Hotel> todasReservaporHab = habitacion_reserva_hotelService.getAll();
        List<Regimen> todosregimen = regimenService.getAll();
        //Lista que se devuelve con método de búsqueda
        List<HistorialReservaClientes> todasmodelohistorial = reservaService.cambiomodelohistorialvigente(todas, todasReservaporHab, todosregimen);
        model.addObject("todas", todasmodelohistorial);

        return model;

    }



}
