package com.example.atlantis.service;
import com.example.atlantis.model.*;
import com.example.atlantis.repository.ReservaRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservaService {
    private static Faker faker = new Faker();
    private HotelService hotelService;

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

    public List<Reserva> todasReservasHotel(int id) {

        List<Reserva> todas = reservaRepository.findAll();
        List<Reserva> buscadas = new ArrayList<>();

        for (int i = 0; i < todas.size(); i++) {
            if (todas.get(i).getId_hotel().getId().equals(id)) {
                buscadas.add(todas.get(i));
            }
        }

        return buscadas;
    }


    public Reserva_Para_BBDD precioHabReservada(Integer idHotel, Objeto_Aux_Reserva_html objeto_aux_reservaHtml){

        Reserva_Para_BBDD reserva_para_bbdd = new Reserva_Para_BBDD();
        List<Regimen> regimenList = new ArrayList<>();

        for(Regimen regimen:regimenService.getAll()){
            if(regimen.getId_hotel().getId().equals(idHotel))regimenList.add(regimen);
        }

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
        List<Double> auxreservabbddpreciohab = new ArrayList<>();

        for(int i = 0; i< objeto_aux_reservaHtml.getCantidadHabitaciones().size(); i++){
            auxreservabbddpreciohab.add(objeto_aux_reservaHtml.getCantidadHabitaciones().get(i)*precioporhab.get(i));
            precioPorHabFinal.add(objeto_aux_reservaHtml.getCantidadHabitaciones().get(i)*precioporhab.get(i)+listaPreciosRegimenHab.get(i));
        }

        reserva_para_bbdd.setPreciohab(auxreservabbddpreciohab);
        Double precioTotal = 0.0;

        for(Double p:precioPorHabFinal){
            precioTotal = precioTotal+ p;
        }

        //preparamos el objeto para la confirmacion de reserva del html
        List<Regimen> regimenList1 = new ArrayList<>();

        for(Integer i:listaIdRegimen){
            regimenList1.add(regimenService.getById(i));
        }

        //Seteo de datos y return
        reserva_para_bbdd.setListIdRegimen(regimenList1);
        reserva_para_bbdd.setListHabitacion(listaHabitaciones);
        reserva_para_bbdd.setIdHotel(idHotel);
        reserva_para_bbdd.setFechaEntrada(LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()));
        reserva_para_bbdd.setFechasalida(LocalDate.parse(objeto_aux_reservaHtml.getFechafin()));
        reserva_para_bbdd.setPrecioTotal(precioTotal);
        reserva_para_bbdd.setNumhab(objeto_aux_reservaHtml.getCantidadHabitaciones());

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

            }

            //coger el fin
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

    public List<HistorialReservaClientes> cambiomodelohistorial(List<Reserva> listareserva, List<Hab_Reserva_Hotel> listahabre, List<Regimen> listaregimen){

        List<HistorialReservaClientes> cambiados = new ArrayList<>();

        for(int i = 0; i < listareserva.size(); i++ ){

            for(int a = 0; a < listahabre.size(); a++ ){
                if(listahabre.get(a).getReserva().getId().equals(listareserva.get(i).getId())) {
                    HistorialReservaClientes reservas = new HistorialReservaClientes();
                    reservas.setId(listareserva.get(i).getId());
                    reservas.setFecha_entrada(listareserva.get(i).getFecha_entrada());
                    reservas.setFecha_salida(listareserva.get(i).getFecha_salida());
                    reservas.setPrecio_total(listareserva.get(i).getPrecio_total());
                    reservas.setCategoria(listahabre.get(a).getId_regimen().getCategoria());
                    reservas.setNombreHotel(listahabre.get(a).getReserva().getId_hotel());
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
                        reservas.setPrecio_total(listareserva.get(i).getPrecio_total());
                        reservas.setCategoria(listahabre.get(a).getId_regimen().getCategoria());
                        reservas.setNombreHotel(listahabre.get(a).getReserva().getId_hotel());
                        cambiados.add(reservas);
                    }
                }
            }
        }

        return cambiados;
    }

    public Reserva guardarReserva(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    public Reserva obtenerUltima(){
        Reserva reserva = reservaRepository.obtenerUltima();
        return reserva;
    }

    public List<Ob_mostrar_reserva> obtenerlistareserva(Reserva_Para_BBDD reserva_para_bbdd){

        List<Ob_mostrar_reserva> lista_reserva_mostrar = new ArrayList<>();

        //Obtención de datos para seteo del modelo
        for(int i =0;i<reserva_para_bbdd.getListHabitacion().size();i++){
            Ob_mostrar_reserva ob_mostrar_reserva= new Ob_mostrar_reserva();
            ob_mostrar_reserva.setHabitaciones(reserva_para_bbdd.getListHabitacion().get(i));
            ob_mostrar_reserva.setRegimen(reserva_para_bbdd.getListIdRegimen().get(i));
            ob_mostrar_reserva.setCantHab(reserva_para_bbdd.getNumhab().get(i));
            ob_mostrar_reserva.setPrecioHab(reserva_para_bbdd.getPreciohab().get(i));
            lista_reserva_mostrar.add(ob_mostrar_reserva);
        }

        return lista_reserva_mostrar;
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

        for(int i = 0; i < listareserva.size(); i++ ){

            if (listareserva.get(i).getFecha_salida().isAfter(LocalDate.now())) {

                for (int a = 0; a < listahabre.size(); a++) {
                    if (listahabre.get(a).getReserva().getId().equals(listareserva.get(i).getId())) {
                        HistorialReservaHotel reservas = new HistorialReservaHotel();
                        reservas.setId(listareserva.get(i).getId());
                        reservas.setNombreCliente(listareserva.get(i).getId_cliente().getNombre());
                        reservas.setApellidosCliente(listareserva.get(i).getId_cliente().getApellidos());
                        reservas.setEmailCliente(listareserva.get(i).getId_cliente().getEmail().getEmail());
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

    public Reserva crearReservaSinPrecio(Cliente cliente,Hotel hotel){
        Reserva reserva = new Reserva();
        LocalDate fecha1 = fechaAzar2022();
        LocalDate fecha2 = fechaAzar2022();
        while (fecha1.equals(fecha2)){
            fecha1 = fechaAzar2022();
            fecha2 = fechaAzar2022();
        }
        reserva.setId_cliente(cliente);
        reserva.setId_hotel(hotel);
        if(fecha1.isBefore(fecha2)){
            reserva.setFecha_entrada(fecha1);
            reserva.setFecha_salida(fecha2);
        }else{
            reserva.setFecha_entrada(fecha2);
            reserva.setFecha_salida(fecha1);
        }
        return reserva;
    }
    public Reserva crearReserva(Cliente cliente,Hotel hotel){
        Reserva reserva = new Reserva();
        LocalDate fecha1 = fechaAzar2022();
        LocalDate fecha2 = fechaAzar2022();
        while (fecha1.equals(fecha2)){
            fecha1 = fechaAzar2022();
            fecha2 = fechaAzar2022();
        }
        reserva.setId_cliente(cliente);
        reserva.setId_hotel(hotel);
        if(fecha1.isBefore(fecha2)){
            reserva.setFecha_entrada(fecha1);
            reserva.setFecha_salida(fecha2);
        }else{
            reserva.setFecha_entrada(fecha2);
            reserva.setFecha_salida(fecha1);
        }
        reserva.setPrecio_total(faker.number().randomDouble(2,1,5000));
        return reserva;
    }

    private LocalDate fechaAzar2022(){
        LocalDate fecha ;
        int anyo = 2022;
        int mes = faker.number().numberBetween(1,13);
        int dia = 1;
        if (mes ==2) dia = faker.number().numberBetween(1,28);
        else if (mes ==1||mes==3||mes==5||mes==7||mes==8||mes==10||mes==12) dia = faker.number().numberBetween(1,32);
        else if (mes==4||mes==6||mes==9||mes==11) dia = faker.number().numberBetween(1,31);
        fecha = LocalDate.of(anyo,mes,dia);
        return fecha;
    }


}
