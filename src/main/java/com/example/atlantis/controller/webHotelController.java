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
    private Precio_HabitacionService precioHabitacionService;


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

        Objeto_Aux_Reserva objetoInteger = new Objeto_Aux_Reserva();
        model.addObject("objeto_integer",objetoInteger);

        return model;
    }
    @PostMapping("/reservar")
    public String reservarHab (@RequestBody @ModelAttribute("objeto_integer") Objeto_Aux_Reserva objeto_aux_reserva,
                               @RequestParam("idhotel") Integer id){

        if(LocalDate.parse(objeto_aux_reserva.getFechainicio()).isAfter(LocalDate.parse(objeto_aux_reserva.getFechafin())))
        {
            return "redirect:/hoteles/item?id="+id; //siento esta fechoria xd
        }
        List<Regimen> regimenList =regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(id)).collect(Collectors.toList());
        List<Regimen> regimenAux = new ArrayList<>();
        List<Double> sumaPreciosRegimenHab = new ArrayList<>();
        List<Double> sumaPreciosFechaHab = new ArrayList<>();
        for(int i=0;i<objeto_aux_reserva.getNum().size();i++){
            for (Regimen r : regimenList){
                if(TipoRegimen.valueOf(objeto_aux_reserva.getId_regimen().get(i)).equals(r.getCategoria())){
                    sumaPreciosRegimenHab.add(r.getPrecio()*objeto_aux_reserva.getNum().get(i));
                }
            }
        }

        System.out.println("num habitaciones");
        System.out.println(objeto_aux_reserva.getNum());
        System.out.println("tipo regimen");
        System.out.println(objeto_aux_reserva.getId_regimen());
        System.out.println("id hotel: " + id);
        System.out.println("suma precios regimen con num habitaciones");
        System.out.println(sumaPreciosRegimenHab);
        System.out.println("suma precios habitaciones por fechas");
        System.out.println(objeto_aux_reserva.getFechainicio());
        System.out.println(objeto_aux_reserva.getFechafin());

        System.out.println("precio habitacion por dia");
        List<Habitaciones> listaHabitaciones = habitacionesService.getAll().stream().filter( h -> h.getId_hotel().getId().equals(id)).collect(Collectors.toList());
        List<Precio_Hab> listapreciohab = new ArrayList<>();
        for(Habitaciones h:listaHabitaciones){
                    listapreciohab.addAll(precioHabitacionService.getAll().stream().filter(p-> p.getId_hotel().getId().equals(id)).collect(Collectors.toList())
                    .stream().filter(p-> p.getId_hab().getId().equals(h.getId())).collect(Collectors.toList()));
        }
//        listapreciohab.stream().forEach(p-> System.out.println(p.getId()));

        for (Precio_Hab ph:listapreciohab){
            if((LocalDate.parse(objeto_aux_reserva.getFechainicio()).isBefore(ph.getFecha_fin()))){
                System.out.println(ph.getId());
            }
        }
        return "redirect:/main";
    }


}
