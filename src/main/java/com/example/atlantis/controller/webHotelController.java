package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class webHotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private BuscadorIDService buscadorService;

    @Autowired
    private HabitacionesService habitacionesService;

    @Autowired
    private RegimenService regimenService;

    @Autowired
    private ReservaService reservaService;

    @RequestMapping("/hoteles/{item}")
    public @ResponseBody ModelAndView resultadoHotel(@PathVariable(value="item") String numerito,
                                                     @RequestParam(value = "id") Integer id) {
        List<Hotel> listaHoteles = hotelService.getAll();
        List<Hotel> hotelfinal = new ArrayList<>();
        List<Habitaciones> listaHabitaciones = habitacionesService.getAll();
        BuscadorID numero = new BuscadorID(id);
        Hotel definitivo = buscadorService.Comparar(numero,listaHoteles);
        hotelfinal.add(definitivo);
        List<TipoRegimen> regimen = regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(id)).collect(Collectors.toList()).stream().map(Regimen::getCategoria).collect(Collectors.toList());
        ModelAndView model = new ModelAndView("hotelWeb");
        Integer estrellas = definitivo.getNum_estrellas();
        model.addObject("hotelfinal", hotelfinal);
        model.addObject("regimen", regimen);
        model.addObject("listaHabitaciones", habitacionesService.conseguir(id,listaHabitaciones).stream().sorted(Comparator.comparing(Habitaciones::getTipo_hab)).collect(Collectors.toList()));
        model.addObject("estrellas",estrellas);
        model.addObject("fechamin", LocalDate.now());

        Objeto_Aux_Reserva_html objetoInteger = new Objeto_Aux_Reserva_html();
        model.addObject("objeto_integer",objetoInteger);

        return model;
    }
    @PostMapping("/reservar")
    public String reservarHab (@RequestBody @ModelAttribute("objeto_integer") Objeto_Aux_Reserva_html objeto_aux_reservaHtml,
                               @RequestParam("idhotel") Integer id){

        if(LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()).isAfter(LocalDate.parse(objeto_aux_reservaHtml.getFechafin())))
        {
            return "redirect:/hoteles/item?id="+id; //siento esta fechoria xd
        }
        //objeto Reserva_para_bbdd
       reservaService.precioHabReservada(id, objeto_aux_reservaHtml);

        return "redirect:/main";
    }


}
