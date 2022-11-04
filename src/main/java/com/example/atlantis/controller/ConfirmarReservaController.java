package com.example.atlantis.controller;

import com.example.atlantis.model.Hab_Reserva_Hotel;
import com.example.atlantis.model.Objeto_Aux_Reserva_html;
import com.example.atlantis.model.Reserva;
import com.example.atlantis.model.Reserva_Para_BBDD;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ConfirmarReservaController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private RegimenService regimenService;
    @Autowired
    private Habitacion_Reserva_HotelService habitacionReservaHotelService;

    @PostMapping("/pago")
    public String confirmarReserva(@RequestParam(value = "objeto_para_bbdd") Reserva_Para_BBDD reserva_para_bbdd){
        System.out.println(reserva_para_bbdd.getFechaEntrada());
        ////////////////////////////////////////////////////////////////////////
        //hacemos la query de la reserva
        Reserva reserva = new Reserva();
        reserva.setId_hotel(hotelService.getById(reserva_para_bbdd.getIdHotel()));
        reserva.setId_cliente(clienteService.getById(reserva_para_bbdd.getNumClientes()));
        reserva.setFecha_entrada(reserva_para_bbdd.getFechaEntrada());
        reserva.setFecha_salida(reserva_para_bbdd.getFechasalida());
        reserva.setPrecio_total(reserva_para_bbdd.getPrecioTotal());
        reserva.setNum_clientes(1);
//        reservaService.guardarReserva(reserva);
        //////////////////////////////////////////
        //guardamos los detalles de la reserva sacando el id de la reserva del cliente creada anteriormente
        for (int i =0;i<reserva_para_bbdd.getListHabitacion().size();i++){
            Hab_Reserva_Hotel habReservaHotel = new Hab_Reserva_Hotel();
            habReservaHotel.setId_hab(reserva_para_bbdd.getListHabitacion().get(i));
            habReservaHotel.setId_regimen(reserva_para_bbdd.getListIdRegimen().get(i));
            habReservaHotel.setReserva(reservaService.getById(habitacionReservaHotelService.UltimoIdReservadelCliente(reserva_para_bbdd.getIdCliente())));
//            habitacionReservaHotelService.guardarHabReservaHotel(habReservaHotel);
        }
        ////////////////////////////////////////////////////////////////////////

        return "redirect:/main";
    }


}
