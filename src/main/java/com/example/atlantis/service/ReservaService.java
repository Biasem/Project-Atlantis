package com.example.atlantis.service;

import com.example.atlantis.model.*;
import com.example.atlantis.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private RegimenService regimenService;
    @Autowired
    private HabitacionesService habitacionesService;
    @Autowired
    private Precio_HabitacionService precioHabitacionService;


    public List<Reserva> getAll(){
        return reservaRepository.findAll();
    }

    public Reserva getById(int id){
        return reservaRepository.findById(id).orElse(null);

    }
    public Reserva_Para_BBDD precioHabReservada(Integer idHotel, Objeto_Aux_Reserva_html objeto_aux_reservaHtml){

        List<Regimen> regimenList =regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(idHotel)).collect(Collectors.toList());
        List<Double> listaPreciosRegimenHab = new ArrayList<>();
        List<Integer> listaIdRegimen= new ArrayList<>();
        //precios para los regimenes por dia y cantidad de habitaciones
        for(int i = 0; i< objeto_aux_reservaHtml.getCantidadHabitaciones().size(); i++){
            for (Regimen r : regimenList){
                if(TipoRegimen.valueOf(objeto_aux_reservaHtml.getTipo_regimen().get(i)).equals(r.getCategoria())){
                    listaPreciosRegimenHab.add(r.getPrecio()* objeto_aux_reservaHtml.getCantidadHabitaciones().get(i)*
                            DAYS.between(LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()),LocalDate.parse(objeto_aux_reservaHtml.getFechafin())));
                    listaIdRegimen.add(r.getId());
                }
            }
        }
        //filtramos las habitaciones
        List<Habitaciones> listaHabitaciones = habitacionesService.getAll().stream().filter(h -> h.getId_hotel().getId().equals(idHotel)).collect(Collectors.toList());
        List<Double> precioporhab = new ArrayList<>();
        for(Habitaciones h:listaHabitaciones){
            precioporhab.add(
            preciosPorHab(precioHabitacionService.getAll().stream().filter(ph-> ph.getId_hotel().getId().equals(h.getId_hotel().getId())).collect(Collectors.toList()).
                    stream().filter(ph-> ph.getId_hab().getId().equals(h.getId())).collect(Collectors.toList()), objeto_aux_reservaHtml));
        }
        //precio final para cada tipo de habitacion por cantidad de hab y fechas
        List<Double> precioPorHabFinal = new ArrayList<>();
        for(int i = 0; i< objeto_aux_reservaHtml.getCantidadHabitaciones().size(); i++){
            precioPorHabFinal.add(objeto_aux_reservaHtml.getCantidadHabitaciones().get(i)*precioporhab.get(i)+listaPreciosRegimenHab.get(i));
        }
        Double precioTotal = 0.0;
        for(Double p:precioPorHabFinal){
            precioTotal = precioTotal+ p;
        }
        //preparamos el objeto para la confirmacion de reserva del html
        Reserva_Para_BBDD reserva_para_bbdd = new Reserva_Para_BBDD();
        reserva_para_bbdd.setListIdRegimen(listaIdRegimen);
        reserva_para_bbdd.setListHabitacion(listaHabitaciones);
        reserva_para_bbdd.setIdHotel(idHotel);
//        reserva_para_bbdd.setIdHotel(idCliente);
//        reserva_para_bbdd.setNumClientes(numclientes);
        reserva_para_bbdd.setFechaEntrada(LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()));
        reserva_para_bbdd.setFechasalida(LocalDate.parse(objeto_aux_reservaHtml.getFechafin()));
        reserva_para_bbdd.setPrecioTotal(precioTotal);
        return reserva_para_bbdd;
    }

    private Double preciosPorHab (List<Precio_Hab> listapreciohab, Objeto_Aux_Reserva_html objeto_aux_reservaHtml){
        Double totalprecio =0.0;
        for (Precio_Hab ph:listapreciohab){
            //reserva en una hab-precio
            if((LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()).isAfter(ph.getFecha_inicio())||
                    (LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()).equals(ph.getFecha_inicio())))&&(
                    (LocalDate.parse(objeto_aux_reservaHtml.getFechafin()).isBefore(ph.getFecha_fin()))||
                            (LocalDate.parse(objeto_aux_reservaHtml.getFechafin()).equals(ph.getFecha_fin())))){
                totalprecio = totalprecio + ph.getPrecio()*DAYS.between(LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()),LocalDate.parse(objeto_aux_reservaHtml.getFechafin()));
            }
            //coger el incio
            if ((LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()).isAfter(ph.getFecha_inicio())&&
                    (LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()).isBefore(ph.getFecha_fin()))&&
                    (LocalDate.parse(objeto_aux_reservaHtml.getFechafin()).isAfter(ph.getFecha_fin())))){
                totalprecio = totalprecio + ph.getPrecio()* DAYS.between(LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()),ph.getFecha_fin());

            }//coger el fin
            if (LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()).isBefore(ph.getFecha_inicio())&&
                    (LocalDate.parse(objeto_aux_reservaHtml.getFechafin()).isAfter(ph.getFecha_inicio())||
                            LocalDate.parse(objeto_aux_reservaHtml.getFechafin()).isEqual(ph.getFecha_inicio()))&&
                    (LocalDate.parse(objeto_aux_reservaHtml.getFechafin()).isBefore(ph.getFecha_fin())||
                            (LocalDate.parse(objeto_aux_reservaHtml.getFechafin()).isEqual(ph.getFecha_fin())))){
                totalprecio = totalprecio + ph.getPrecio()*(DAYS.between(ph.getFecha_inicio(),LocalDate.parse(objeto_aux_reservaHtml.getFechafin()))+1);

            }
            //fechas de hab-precio entre otras hab-precio
            if(LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()).isBefore(ph.getFecha_inicio())&&
                    LocalDate.parse(objeto_aux_reservaHtml.getFechafin()).isAfter(ph.getFecha_fin())){
                totalprecio = totalprecio + ph.getPrecio()* (DAYS.between(ph.getFecha_inicio(), ph.getFecha_fin())+1);

            }
        }
        return totalprecio;
    }


}
