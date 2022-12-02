package com.example.atlantis.controller;


import com.example.atlantis.model.*;
import com.example.atlantis.service.*;
import com.example.atlantis.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClienteHistorialController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private LoginService loginService;

    @Autowired
    private RegimenService regimenService;

    @Autowired
    private Habitacion_Reserva_HotelService habitacion_reserva_hotelService;
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private HotelService hotelService;


    //Todas las reservas
    @GetMapping("/historialReservaCliente")
    public ModelAndView historial(@ModelAttribute Cliente cliente) {

        //Gestión sesión
        ModelAndView model = new ModelAndView("historialReservaCliente");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idCliente = 0;
        Integer idHotel = 0;

        if (correo != null){
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
            System.out.println(idCliente);
            System.out.println(idHotel);
        }

        //Obtención de historial completo
        Integer finalIdCliente = idCliente;
        List<Reserva> reservas = reservaRepository.findAll().stream().filter(x-> x.getId_cliente().getId().equals(finalIdCliente)).collect(Collectors.toList());

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

        //Gestión sesión
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
