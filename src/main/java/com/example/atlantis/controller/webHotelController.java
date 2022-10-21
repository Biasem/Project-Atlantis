package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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


        Objeto_Aux_Reserva objetoInteger = new Objeto_Aux_Reserva();
        model.addObject("objeto_integer",objetoInteger);

        return model;
    }
    @PostMapping("/reservar")
    public String reservarHab (@RequestBody @ModelAttribute("objeto_integer") Objeto_Aux_Reserva objeto_aux_reserva,
                               @RequestParam("idhotel") Integer id){


        System.out.println("num habitaciones");
        System.out.println(objeto_aux_reserva.getNum());
        System.out.println("tipo regimen");
        System.out.println(objeto_aux_reserva.getId_regimen());
        System.out.println("id hotel" + id);

        List<Regimen> regimenList =regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(id)).collect(Collectors.toList());
        List<Double> sumaPreciosRegimenHab = new ArrayList<>();
        List<Double> sumaPreciosFechaHab = new ArrayList<>();
        for (int i=0;i<objeto_aux_reserva.getNum().size();i++){
            sumaPreciosRegimenHab.add(regimenList.get(i).getPrecio()*objeto_aux_reserva.getNum().get(i));
        }
        System.out.println("suma precios regimen con num habitaciones");
        System.out.println(sumaPreciosRegimenHab);


        return "redirect:/main";
    }




}
