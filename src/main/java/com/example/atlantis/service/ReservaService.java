package com.example.atlantis.service;

import com.example.atlantis.model.*;
import com.example.atlantis.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.sound.sampled.ReverbType;
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

    public List<Reserva> todasReservas(int id){
       List<Reserva> todas = reservaRepository.findAll();
       List<Reserva> buscadas = new ArrayList<>();

        for(int i = 0; i < todas.size(); i++ ){
            if(todas.get(i).getId_cliente().getId().equals(id)){
                buscadas.add(todas.get(i));
            }
        }
        return buscadas;


    }

    public List<Reserva> todasReservasHotel(int id){
        List<Reserva> todas = reservaRepository.findAll();
        List<Reserva> buscadas = new ArrayList<>();

        for(int i = 0; i < todas.size(); i++ ){
            if(todas.get(i).getId_hotel().getId().equals(id)){
                buscadas.add(todas.get(i));
            }
        }
        return buscadas;


    }
    public Reserva_Para_BBDD precioHabReservada(Integer idHotel, GraphqlInput.Objeto_Aux_Reserva_htmlInput objeto_aux_reservaHtml){

        List<Regimen> regimenList = regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(idHotel)).collect(Collectors.toList());
        List<Double> listaPreciosRegimenHab = new ArrayList<>();
        List<Integer> listaIdRegimen= new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate fechap =  LocalDate.parse(objeto_aux_reservaHtml.getFechainicio(), formatter);
        LocalDate fechap1 =  LocalDate.parse(objeto_aux_reservaHtml.getFechafin(), formatter);


        //precios para los regimenes por dia y cantidad de habitaciones
        for(int i = 0; i< objeto_aux_reservaHtml.getCantidadHabitaciones().size(); i++){
            for (Regimen r : regimenList){
                if(TipoRegimen.valueOf(objeto_aux_reservaHtml.getTipo_regimen().get(i)).equals(r.getCategoria())){
                    listaPreciosRegimenHab.add(r.getPrecio()* objeto_aux_reservaHtml.getCantidadHabitaciones().get(i)*
                            DAYS.between(fechap,fechap1));
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
        reserva_para_bbdd.setFechaEntrada(fechap);
        reserva_para_bbdd.setFechasalida(fechap1);
        reserva_para_bbdd.setPrecioTotal(precioTotal);
        return reserva_para_bbdd;


    }

    private Double preciosPorHab (List<Precio_Hab> listapreciohab, GraphqlInput.Objeto_Aux_Reserva_htmlInput objeto_aux_reservaHtml){
        Double totalprecio =0.0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate fechap =  LocalDate.parse(objeto_aux_reservaHtml.getFechainicio(), formatter);
        LocalDate fechap1 =  LocalDate.parse(objeto_aux_reservaHtml.getFechafin(), formatter);



        for (Precio_Hab ph:listapreciohab){
            //reserva en una hab-precio
            if((fechap1.isAfter(fechap)||
                    (fechap.equals(ph.getFecha_inicio())))&&(
                    (fechap1.isBefore(ph.getFecha_fin()))||
                            (fechap1.equals(ph.getFecha_fin())))){
                totalprecio = totalprecio + ph.getPrecio()*DAYS.between(fechap,fechap1);
            }
            //coger el incio
            if ((fechap.isAfter(ph.getFecha_inicio())&&
                    (fechap.isBefore(ph.getFecha_fin()))&&
                    (fechap1.isAfter(ph.getFecha_fin())))){
                totalprecio = totalprecio + ph.getPrecio()* DAYS.between(fechap,ph.getFecha_fin());

            }//coger el fin
            if (fechap.isBefore(ph.getFecha_inicio())&&
                    (fechap1.isAfter(ph.getFecha_inicio())||
                            fechap1.isEqual(ph.getFecha_inicio()))&&
                    (fechap1.isBefore(ph.getFecha_fin())||
                            (fechap1.isEqual(ph.getFecha_fin())))){
                totalprecio = totalprecio + ph.getPrecio()*(DAYS.between(ph.getFecha_inicio(),fechap1)+1);

            }
            //fechas de hab-precio entre otras hab-precio
            if(fechap.isBefore(ph.getFecha_inicio())&&
                    fechap1.isAfter(ph.getFecha_fin())){
                totalprecio = totalprecio + ph.getPrecio()* (DAYS.between(ph.getFecha_inicio(), ph.getFecha_fin())+1);

            }
        }
        return totalprecio;
    }
    public List<HistorialReservaClientes> cambiomodelohistorial(List<Reserva> listareserva, List<Hab_Reserva_Hotel> listahabre, List<Regimen> listaregimen){

        List<HistorialReservaClientes> cambiados = new ArrayList<>();

        for(int i = 0; i < listareserva.size(); i++ )
        {
            for(int a = 0; a < listahabre.size(); a++ ){
                if(listahabre.get(a).getReserva().getId().equals(listareserva.get(i).getId())) {
                    HistorialReservaClientes reservas = new HistorialReservaClientes();
                    reservas.setId(listareserva.get(i).getId());
                    reservas.setFecha_entrada(listareserva.get(i).getFecha_entrada());
                    reservas.setFecha_salida(listareserva.get(i).getFecha_salida());
                    reservas.setNum_clientes(listareserva.get(i).getNum_clientes());
                    reservas.setPrecio_total(listareserva.get(i).getPrecio_total());
                    reservas.setCategoria(listahabre.get(a).getId_regimen().getCategoria());
                    reservas.setNombreHotel(listahabre.get(a).getReserva().getId_hotel().getNombre());
                    cambiados.add(reservas);
                }
            }
        }
        return cambiados;
    }


    public List<HistorialReservaClientes> cambiomodelohistorialvigente(List<Reserva> listareserva, List<Hab_Reserva_Hotel> listahabre, List<Regimen> listaregimen){

        List<HistorialReservaClientes> cambiados = new ArrayList<>();

        for(int i = 0; i < listareserva.size(); i++ ) {
            if (listareserva.get(i).getFecha_salida().isAfter(LocalDate.now())) {
                for (int a = 0; a < listahabre.size(); a++) {
                    if (listahabre.get(a).getReserva().getId().equals(listareserva.get(i).getId())) {
                        HistorialReservaClientes reservas = new HistorialReservaClientes();
                        reservas.setId(listareserva.get(i).getId());
                        reservas.setFecha_entrada(listareserva.get(i).getFecha_entrada());
                        reservas.setFecha_salida(listareserva.get(i).getFecha_salida());
                        reservas.setNum_clientes(listareserva.get(i).getNum_clientes());
                        reservas.setPrecio_total(listareserva.get(i).getPrecio_total());
                        reservas.setCategoria(listahabre.get(a).getId_regimen().getCategoria());
                        reservas.setNombreHotel(listahabre.get(a).getReserva().getId_hotel().getNombre());
                        cambiados.add(reservas);
                    }
                }
            }
        }
        return cambiados;
    }

    public void guardarReserva(Reserva reserva){
        reservaRepository.save(reserva);
    }




    public List<HistorialReservaHotel> cambiomodelohistorialhotel(List<Reserva> listareserva, List<Hab_Reserva_Hotel> listahabre, List<Regimen> listaregimen){

        List<HistorialReservaHotel> cambiados = new ArrayList<>();

        for(int i = 0; i < listareserva.size(); i++ )
        {

            for(int a = 0; a < listahabre.size(); a++ ){
                if(listahabre.get(a).getReserva().getId().equals(listareserva.get(i).getId())) {
                    HistorialReservaHotel reservas = new HistorialReservaHotel();
                    reservas.setId(listareserva.get(i).getId());
                    reservas.setNombreCliente(listareserva.get(i).getId_cliente().getNombre());
                    reservas.setApellidosCliente(listareserva.get(i).getId_cliente().getApellidos());
                    reservas.setEmailCliente(listareserva.get(i).getId_cliente().getEmail().getEmail());
                    reservas.setNum_clientes(listahabre.get(a).getReserva().getNum_clientes());
                    reservas.setFecha_entrada(listahabre.get(a).getReserva().getFecha_entrada());
                    reservas.setFecha_salida(listahabre.get(a).getReserva().getFecha_salida());
                    reservas.setCategoria(listahabre.get(a).getId_regimen().getCategoria());
                    reservas.setPrecio_total(listareserva.get(i).getPrecio_total());
                    cambiados.add(reservas);
                }
            }
        }
        return cambiados;
    }

    public List<HistorialReservaHotel> cambiomodelohistorialhotelvigente(List<Reserva> listareserva, List<Hab_Reserva_Hotel> listahabre, List<Regimen> listaregimen){

        List<HistorialReservaHotel> cambiados = new ArrayList<>();

        for(int i = 0; i < listareserva.size(); i++ )
        {
            if (listareserva.get(i).getFecha_salida().isAfter(LocalDate.now())) {
                for (int a = 0; a < listahabre.size(); a++) {
                    if (listahabre.get(a).getReserva().getId().equals(listareserva.get(i).getId())) {
                        HistorialReservaHotel reservas = new HistorialReservaHotel();
                        reservas.setId(listareserva.get(i).getId());
                        reservas.setNombreCliente(listareserva.get(i).getId_cliente().getNombre());
                        reservas.setApellidosCliente(listareserva.get(i).getId_cliente().getApellidos());
                        reservas.setEmailCliente(listareserva.get(i).getId_cliente().getEmail().getEmail());
                        reservas.setNum_clientes(listahabre.get(a).getReserva().getNum_clientes());
                        reservas.setFecha_entrada(listahabre.get(a).getReserva().getFecha_entrada());
                        reservas.setFecha_salida(listahabre.get(a).getReserva().getFecha_salida());
                        reservas.setCategoria(listahabre.get(a).getId_regimen().getCategoria());
                        reservas.setPrecio_total(listareserva.get(i).getPrecio_total());
                        cambiados.add(reservas);
                    }
                }
            }
        }
        return cambiados;
    }





}
