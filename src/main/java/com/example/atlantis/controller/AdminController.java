package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.BusquedaService;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.HabitacionesService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController{

    @Autowired
    private HotelService hotelService;

    @Autowired
    private BusquedaService busquedaService;

    @Autowired
    private HabitacionesService habitacionesService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/admin")
    public ModelAndView admin(HttpSession session) {
        // Gestión sesión
        Login usuario = new Login();
        usuario = (Login) session.getAttribute("user");
        Integer idCliente = 0;
        Integer idHotel = 0;
        if (usuario != null){
            idCliente = clienteService.conseguirId(usuario);
            idHotel = hotelService.conseguirId(usuario);
            System.out.println(idCliente);
        }
        ModelAndView model = new ModelAndView("adminTest");
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        model.addObject("usuario", usuario);
        List<TipoHab> tipohab = habitacionesService.todoHab();
        model.addObject("tipohab",tipohab);
        model.addObject("habitaciones", new Habitaciones());
        List<Habitaciones> listaHabitaciones = habitacionesService.getAll();
        model.addObject("listaHabitaciones", habitacionesService.conseguir(idHotel,listaHabitaciones));
        return model;

    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ModelAndView habitaciones(@ModelAttribute Habitaciones habitaciones,
                                     @RequestParam("idhotel") Integer idhotel) {
        habitaciones.setHab_ocupadas(0);
        habitacionesService.conseguirIDHotel(idhotel,habitaciones);
        habitacionesService.guardarHabitacion(habitaciones);
        ModelAndView model = new ModelAndView("adminHecho");
        return model;
    }

    @RequestMapping("/admin/habitaciones/borrar/{item}")
    public @ResponseBody ModelAndView borrarHabitacion(@PathVariable(value="item") String numerito,
                                                     @RequestParam(value = "id") Integer id) {
        habitacionesService.borrarHabitacion(id);
        ModelAndView model = new ModelAndView("adminHecho");
        return model;
    }

    @RequestMapping("/admin/habitaciones/editar/{item}")
    public @ResponseBody ModelAndView editarHabitacion(@PathVariable(value="item") String numerito,
                                                       @RequestParam(value = "id") Integer id) {
        List<Habitaciones> habitacion = new ArrayList<>();
        habitacion.add(habitacionesService.getById(id));
        List<TipoHab> tipohab = habitacionesService.todoHab();
        ModelAndView model = new ModelAndView("adminHabitacionEditar");
        model.addObject("tipohab", tipohab);
        model.addObject("habitacion", habitacion);
        model.addObject("habitaciones", new Habitaciones());
        return model;
    }

    @RequestMapping("/admin/habitaciones/editar/hecho/{item}")
    public @ResponseBody ModelAndView editarHabitacionhecho(@PathVariable(value="item") String numerito,
                                                       @RequestParam(value = "id") Integer id,
                                                       @ModelAttribute Habitaciones habitaciones) {
        habitacionesService.editarHabitacion(id, habitaciones);
        ModelAndView model = new ModelAndView("adminHecho");
        return model;
    }

}
