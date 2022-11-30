package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.repository.ComentarioHotelRepository;
import com.example.atlantis.repository.ComentarioLikeRepository;
import com.example.atlantis.repository.HabitacionesRepository;
import com.example.atlantis.repository.HotelRepository;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static java.time.temporal.ChronoUnit.DAYS;



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
    private ComentarioService comentarioService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private HabitacionesRepository habitacionesRepository;

    @Autowired
    private Habitacion_Reserva_HotelService habitacionReservaHotelService;
    @Autowired
    private ComentarioLikeRepository comentarioLikeRepository;
    @Autowired
    private ComentarioHotelRepository comentarioHotelRepository;
    @Autowired
    private HotelRepository hotelRepository;

    protected static Reserva_Para_BBDD reserva_para_bbdd = null;

    @RequestMapping(value = "/hoteles/{item}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView resultadoHotel(@PathVariable(value="item") String numerito,
                                                     @RequestParam(value = "id") Integer id,
                                                     HttpSession session) {

        ModelAndView model = new ModelAndView("hotelWeb");
        // Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idCliente = 0;
        Integer idHotel = 0;
        if (correo != null){
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
        }
        Integer idHotelreserva = id;

        if (idHotel>0){
            Integer finalIdHotel = idHotel;
            Hotel hotelConectado = hotelRepository.findAll().stream().filter(x-> x.getId().equals(finalIdHotel)).collect(Collectors.toList()).get(0);
            model.addObject("hotelConectado", hotelConectado);
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        model.addObject("idHotelreserva", idHotelreserva);
        // Gestión sesión

        List<Hotel> listaHoteles = hotelService.getAll();
        List<Hotel> hotelfinal = new ArrayList<>();
        List<Habitaciones> listaHabitaciones = habitacionesService.getAll();
        List<ComentarioHotel> listaComentariosHotel = comentarioHotelRepository.findAll().stream().filter(x-> x.getHotel().getId().equals(idHotelreserva)).collect(Collectors.toList());
        BuscadorID numero = new BuscadorID(id);
        Hotel definitivo = buscadorService.Comparar(numero,listaHoteles);
        hotelfinal.add(definitivo);
        List<TipoRegimen> regimen = regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(id)).collect(Collectors.toList()).stream().map(Regimen::getCategoria).collect(Collectors.toList());
        Double latitud = definitivo.getLatitud();
        Double longitud = definitivo.getLongitud();
        Integer estrellas = definitivo.getNum_estrellas();
        model.addObject("latitud", latitud);
        model.addObject("longitud", longitud);
        model.addObject("listaComentariosHotel", listaComentariosHotel);
        model.addObject("idCliente", idCliente);
        model.addObject("texto", new Comentario());
        model.addObject("hotelfinal", hotelfinal);
        model.addObject("regimen", regimen);
        model.addObject("listaHabitaciones", habitacionesService.conseguir(id,listaHabitaciones).stream().filter(h -> h.getHab_ocupadas()<h.getNum_hab()).collect(Collectors.toList()));
        model.addObject("estrellas",estrellas);
        model.addObject("fechamin", LocalDate.now());
        Objeto_Aux_Reserva_html objetoInteger = new Objeto_Aux_Reserva_html();
        if((session.getAttribute("fecha_inicial")!=null)&&(session.getAttribute("fecha_final")!=null)){
            objetoInteger.setFechainicio(session.getAttribute("fecha_inicial").toString());
            objetoInteger.setFechafin(session.getAttribute("fecha_final").toString());
        }
        model.addObject("objeto_integer",objetoInteger);
        model.addObject("comentarios",comentarioService.conseguirComentarios(id));
        Integer comprobante = 0;
        model.addObject("comprobante", comprobante);

        return model;
    }

    @PostMapping("/comentario")
    public @ResponseBody ModelAndView comentarioHotel(HttpSession session,
                                                      @ModelAttribute Comentario comentario,
                                                      @RequestParam("idhotel") Integer idhotel,
                                                      @RequestParam("idcliente") Integer idcliente,
                                                      @RequestParam("texto") String texto){
        comentario.setFecha(LocalDate.now());
        comentario.setSentencia(texto);
        comentario.setPuntuacion(comentario.getPuntuacion());
        comentario.setSentencia(comentario.getSentencia());
        comentario.setLikes(0);
        comentarioService.comentarioID(idhotel,idcliente,comentario);
        comentarioService.guardarComentario(comentario);
        ModelAndView model = new ModelAndView("comentarioHecho");
        return model;
    }
    @PostMapping("/comentario/hotel")
    public @ResponseBody ModelAndView comentariorespuestaHotel(HttpSession session,
                                                      @ModelAttribute ComentarioHotel comentario,
                                                      @RequestParam("idhotel") Integer idhotel,
                                                      @RequestParam("idcliente") Integer idcliente,
                                                      @RequestParam("texto") String texto,
                                                               @RequestParam("idcomentario") Integer idcomentario){
        comentario.setFecha(LocalDate.now());
        comentario.setSentencia(texto);
        comentario.setSentencia(comentario.getSentencia());
        comentarioService.comentarioIDHotel(idhotel,idcliente, idcomentario, comentario);
        comentarioService.guardarComentarioHotel(comentario);
        ModelAndView model = new ModelAndView("comentarioHecho");
        return model;
    }

    @PostMapping("/like")
    public @ResponseBody ModelAndView likeComentario(HttpSession session,
                                                      @ModelAttribute Comentario comentario,
                                                      @ModelAttribute ComentarioLike comentarioLike,
                                                      @RequestParam("idcomentario") Integer idcomentario,
                                                      @RequestParam("idcliente") Integer idcliente,
                                                      @RequestParam("like") Integer like,
                                                     @RequestParam("dislike") Integer dislike,
                                                     @RequestParam("idhotelreserva") Integer idhotelreserva){

        comentarioService.likedislike(idcliente,idcomentario,idhotelreserva,like,dislike);
        comentarioService.sumalikes(idcomentario);
        ModelAndView model = new ModelAndView("comentarioHecho");
        return model;
    }
    @PostMapping("/reservar")
    public ModelAndView reservarHab (@RequestBody @ModelAttribute("objeto_integer") Objeto_Aux_Reserva_html objeto_aux_reservaHtml,
                               @RequestParam("idhotel") Integer idhotel){

        ModelAndView model = new ModelAndView("pagarReserva");
        // Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idCliente = 0;
        Integer idHotel = 0;
        if (correo != null){
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        // Gestión sesión
        if(LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()).isAfter(LocalDate.parse(objeto_aux_reservaHtml.getFechafin()))||
                LocalDate.parse(objeto_aux_reservaHtml.getFechainicio()).equals(LocalDate.parse(objeto_aux_reservaHtml.getFechafin())))
        {
            return new ModelAndView("redirect:/hoteles/item?id="+idhotel); //siento esta fechoria xd
        }

        List<Integer> listaAux = new ArrayList<>();
        for (Integer i:objeto_aux_reservaHtml.getCantidadHabitaciones()){
            if (i==null){
                listaAux.add(0);
//                return new ModelAndView("redirect:/hoteles/item?id="+idhotel);
            }else {
                listaAux.add(i);
            }
        }
        objeto_aux_reservaHtml.setCantidadHabitaciones(listaAux);

        //objeto Reserva_para_bbdd
        reserva_para_bbdd = reservaService.precioHabReservada(idhotel, objeto_aux_reservaHtml);
        reserva_para_bbdd.setIdCliente(idCliente);
        List<Ob_mostrar_reserva> listamostrar = reservaService.obtenerlistareserva(reserva_para_bbdd);
        for (Ob_mostrar_reserva omr :listamostrar){
            if(omr.getCantHab()>omr.getHabitaciones().getNum_hab()-omr.getHabitaciones().getHab_ocupadas()) return new ModelAndView("redirect:/hoteles/item?id="+idhotel);
        }

        Long dias = DAYS.between(reserva_para_bbdd.getFechaEntrada(),reserva_para_bbdd.getFechasalida());

        model.addObject("dias",dias);
        model.addObject("total",reserva_para_bbdd.getPrecioTotal());
        model.addObject("listareserva",listamostrar);

        return model;
    }

    @PostMapping("/pago")
    public String confirmarReserva(){
        ////////////////////////////////////////////////////////////////////////
        //hacemos la query de la reserva
        Reserva reserva = new Reserva();
        reserva.setId_hotel(hotelService.getById(reserva_para_bbdd.getIdHotel()));
        reserva.setId_cliente(clienteService.getById(reserva_para_bbdd.getIdCliente()));
        reserva.setFecha_entrada(reserva_para_bbdd.getFechaEntrada());
        reserva.setFecha_salida(reserva_para_bbdd.getFechasalida());
        reserva.setPrecio_total(reserva_para_bbdd.getPrecioTotal());
        reservaService.guardarReserva(reserva);

        //guardamos los detalles de la reserva sacando el id de la reserva del cliente creada anteriormente
        for (int i =0;i<reserva_para_bbdd.getListHabitacion().size();i++){
            Hab_Reserva_Hotel habReservaHotel = new Hab_Reserva_Hotel();
            habReservaHotel.setId_hab(reserva_para_bbdd.getListHabitacion().get(i));
            habReservaHotel.setId_regimen(reserva_para_bbdd.getListIdRegimen().get(i));
            habReservaHotel.setReserva(reservaService.getById(habitacionReservaHotelService.UltimoIdReservadelCliente(reserva_para_bbdd.getIdCliente())));
            habReservaHotel.setNumhab(reserva_para_bbdd.getNumhab().get(i));
            habitacionReservaHotelService.guardarHabReservaHotel(habReservaHotel);
            Habitaciones hab = reserva_para_bbdd.getListHabitacion().get(i);
            hab.setHab_ocupadas(hab.getHab_ocupadas()+reserva_para_bbdd.getNumhab().get(i));
            habitacionesRepository.save(hab);

        }
        reserva_para_bbdd = null;
        return "redirect:/main";
    }
}
