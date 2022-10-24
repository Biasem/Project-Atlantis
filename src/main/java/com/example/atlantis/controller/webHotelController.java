package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.*;
import com.example.atlantis.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class webHotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private BuscadorIDService buscadorService;

    @Autowired
    private HabitacionesService habitacionesService;

    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private ClienteService clienteService;


    @RequestMapping("/hoteles/{item}")
    public @ResponseBody ModelAndView resultadoHotel(@PathVariable(value="item") String numerito,
                                                     @RequestParam(value = "id") Integer id, HttpSession session) {
        List<Hotel> listaHoteles = hotelService.getAll();
        List<Hotel> hotelfinal = new ArrayList<>();
        List<Habitaciones> listaHabitaciones = habitacionesService.getAll();
        List<Comentario> comentarios = new ArrayList<>();
        BuscadorID numero = new BuscadorID(id);
        Hotel definitivo = buscadorService.Comparar(numero,listaHoteles);
        hotelfinal.add(definitivo);
        Login usuario = new Login();
        usuario = (Login) session.getAttribute("user");
        Integer idCliente = 0;
        idCliente = clienteService.conseguirId(usuario);
        System.out.println(idCliente);
        List<TipoRegimen> regimen = hotelService.todoregimen();
        ModelAndView model = new ModelAndView("hotelWeb");
        Integer estrellas = definitivo.getNum_estrellas();
        model.addObject("hotelfinal", hotelfinal);
        model.addObject("regimen", regimen);
        model.addObject("listaHabitaciones", habitacionesService.conseguir(id,listaHabitaciones));
        model.addObject("estrellas",estrellas);
        model.addObject("comentarios",comentarioService.conseguirComentarios(id));
        model.addObject("idCliente", idCliente);
        return model;
    }


}
