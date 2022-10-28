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
    public Integer precioHabReservada(Integer idHotel, Objeto_Aux_Reserva objeto_aux_reserva){

        List<Regimen> regimenList =regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(idHotel)).collect(Collectors.toList());
        List<Regimen> regimenAux = new ArrayList<>();
        List<Double> listaPreciosRegimenHab = new ArrayList<>();
        List<Double> sumaPreciosFechaHab = new ArrayList<>();
        for(int i=0;i<objeto_aux_reserva.getCantidadHabitaciones().size();i++){
            for (Regimen r : regimenList){
                if(TipoRegimen.valueOf(objeto_aux_reserva.getId_regimen().get(i)).equals(r.getCategoria())){
                    listaPreciosRegimenHab.add(r.getPrecio()*objeto_aux_reserva.getCantidadHabitaciones().get(i)*
                            DAYS.between(LocalDate.parse(objeto_aux_reserva.getFechainicio()),LocalDate.parse(objeto_aux_reserva.getFechafin())));
                }
            }
        }

        System.out.println("cantidad habitaciones");
        System.out.println(objeto_aux_reserva.getCantidadHabitaciones());
        System.out.println("tipo regimen");
        System.out.println(objeto_aux_reserva.getId_regimen());
        System.out.println("id hotel: " + idHotel);
        System.out.println("suma precios regimen con num habitaciones");
        System.out.println(listaPreciosRegimenHab);


        List<Habitaciones> listaHabitaciones = habitacionesService.getAll().stream().filter(h -> h.getId_hotel().getId().equals(idHotel)).collect(Collectors.toList());
        List<Double> precioporhab = new ArrayList<>();
        for(Habitaciones h:listaHabitaciones){
            precioporhab.add(
            preciosPorHab(precioHabitacionService.getAll().stream().filter(ph-> ph.getId_hotel().getId().equals(h.getId_hotel().getId())).collect(Collectors.toList()).
                    stream().filter(ph-> ph.getId_hab().getId().equals(h.getId())).collect(Collectors.toList()), objeto_aux_reserva));
        }
        List<Double> precioPorHabFinal = new ArrayList<>();
        for(int i=0;i<objeto_aux_reserva.getCantidadHabitaciones().size();i++){
            precioPorHabFinal.add(objeto_aux_reserva.getCantidadHabitaciones().get(i)*precioporhab.get(i)+listaPreciosRegimenHab.get(i));
        }
        precioPorHabFinal.stream().forEach(ph-> System.out.println(ph.doubleValue()));
        return 1;
    }

    private Double preciosPorHab (List<Precio_Hab> listapreciohab,Objeto_Aux_Reserva objeto_aux_reserva){
        Double totalprecio =0.0;
        for (Precio_Hab ph:listapreciohab){
            //reserva en una hab-precio
            if((LocalDate.parse(objeto_aux_reserva.getFechainicio()).isAfter(ph.getFecha_inicio())||
                    (LocalDate.parse(objeto_aux_reserva.getFechainicio()).equals(ph.getFecha_inicio())))&&(
                    (LocalDate.parse(objeto_aux_reserva.getFechafin()).isBefore(ph.getFecha_fin()))||
                            (LocalDate.parse(objeto_aux_reserva.getFechafin()).equals(ph.getFecha_fin())))){
                totalprecio = totalprecio + ph.getPrecio()*DAYS.between(LocalDate.parse(objeto_aux_reserva.getFechainicio()),LocalDate.parse(objeto_aux_reserva.getFechafin()));
            }
            //coger el incio
            if ((LocalDate.parse(objeto_aux_reserva.getFechainicio()).isAfter(ph.getFecha_inicio())&&
                    (LocalDate.parse(objeto_aux_reserva.getFechainicio()).isBefore(ph.getFecha_fin()))&&
                    (LocalDate.parse(objeto_aux_reserva.getFechafin()).isAfter(ph.getFecha_fin())))){
                totalprecio = totalprecio + ph.getPrecio()* DAYS.between(LocalDate.parse(objeto_aux_reserva.getFechainicio()),ph.getFecha_fin());

            }//coger el fin
            if (LocalDate.parse(objeto_aux_reserva.getFechainicio()).isBefore(ph.getFecha_inicio())&&
                    (LocalDate.parse(objeto_aux_reserva.getFechafin()).isAfter(ph.getFecha_inicio())||
                            LocalDate.parse(objeto_aux_reserva.getFechafin()).isEqual(ph.getFecha_inicio()))&&
                    (LocalDate.parse(objeto_aux_reserva.getFechafin()).isBefore(ph.getFecha_fin())||
                            (LocalDate.parse(objeto_aux_reserva.getFechafin()).isEqual(ph.getFecha_fin())))){
                totalprecio = totalprecio + ph.getPrecio()*(DAYS.between(ph.getFecha_inicio(),LocalDate.parse(objeto_aux_reserva.getFechafin()))+1);

            }
            //fechas de hab-precio entre otras hab-precio
            if(LocalDate.parse(objeto_aux_reserva.getFechainicio()).isBefore(ph.getFecha_inicio())&&
                    LocalDate.parse(objeto_aux_reserva.getFechafin()).isAfter(ph.getFecha_fin())){
                totalprecio = totalprecio + ph.getPrecio()* (DAYS.between(ph.getFecha_inicio(), ph.getFecha_fin())+1);

            }
        }

        return totalprecio;
    }


}
