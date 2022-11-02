package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;

import java.security.Principal;
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
    private ComentarioService comentarioService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ReservaService reservaService;


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
            System.out.println(idCliente);
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        // Gestión sesión

        List<Hotel> listaHoteles = hotelService.getAll();
        List<Hotel> hotelfinal = new ArrayList<>();
        List<Habitaciones> listaHabitaciones = habitacionesService.getAll();
        List<Comentario> comentarios = new ArrayList<>();
        BuscadorID numero = new BuscadorID(id);
        Hotel definitivo = buscadorService.Comparar(numero,listaHoteles);
        hotelfinal.add(definitivo);
        List<TipoRegimen> regimen = regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(id)).collect(Collectors.toList()).stream().map(Regimen::getCategoria).collect(Collectors.toList());

        Integer estrellas = definitivo.getNum_estrellas();
        model.addObject("idCliente", idCliente);
        model.addObject("texto", new Comentario());
        model.addObject("hotelfinal", hotelfinal);
        model.addObject("regimen", regimen);
        model.addObject("listaHabitaciones", habitacionesService.conseguir(id,listaHabitaciones).stream().sorted(Comparator.comparing(Habitaciones::getTipo_hab)).collect(Collectors.toList()));
        model.addObject("estrellas",estrellas);
        model.addObject("fechamin", LocalDate.now());

        Objeto_Aux_Reserva_html objetoInteger = new Objeto_Aux_Reserva_html();
        model.addObject("objeto_integer",objetoInteger);

        model.addObject("comentarios",comentarioService.conseguirComentarios(id));
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
        comentarioService.comentarioID(idhotel,idcliente,comentario);
        System.out.println(comentario);
        comentarioService.guardarComentario(comentario);
        ModelAndView model = new ModelAndView("comentarioHecho");
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
       Reserva_Para_BBDD reserva = reservaService.precioHabReservada(id, objeto_aux_reservaHtml);

        return "redirect:/main";
    }




}
