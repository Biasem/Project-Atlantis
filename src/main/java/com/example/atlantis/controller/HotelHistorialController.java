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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HotelHistorialController {


    @Autowired
    private ReservaService reservaService;
    @Autowired
    private LoginService loginService;

    @Autowired
    private RegimenService regimenService;

    @Autowired
    private Habitacion_Reserva_HotelService habitacion_reserva_hotelService;



    @GetMapping("/historialReservaHotel")
    public ModelAndView historial(@ModelAttribute Hotel hotel) {
        ModelAndView model = new ModelAndView("historialReservaHotel");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Hotel hotel1 = loginService.cogerid(correo);

        List<Reserva> todas = reservaService.todasReservasHotel(hotel1.getId());
        List<Hab_Reserva_Hotel> todasReservaporHab = habitacion_reserva_hotelService.getAll();
        List<Regimen> todosregimen = regimenService.getAll();

        List<HistorialReservaHotel> todasmodelohistorial = reservaService.cambiomodelohistorialhotel(todas, todasReservaporHab, todosregimen);


        model.addObject("todas", todasmodelohistorial);

        return model;

    }

}
