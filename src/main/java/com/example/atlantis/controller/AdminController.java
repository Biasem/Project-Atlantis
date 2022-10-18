package com.example.atlantis.controller;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Habitaciones;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.TipoHab;
import com.example.atlantis.service.BusquedaService;
import com.example.atlantis.service.HabitacionesService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class AdminController{

    @Autowired
    private HotelService hotelService;

    @Autowired
    private BusquedaService busquedaService;

    @Autowired
    private HabitacionesService habitacionesService;

    @GetMapping("/admin")
    public ModelAndView admin() {
        List<TipoHab> tipohab = habitacionesService.todoHab();
        ModelAndView model = new ModelAndView("adminTest");
        model.addObject("tipohab",tipohab);
        model.addObject("habitaciones", new Habitaciones());
        return model;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ModelAndView habitaciones(@ModelAttribute Habitaciones habitaciones) {
        habitaciones.setHab_ocupadas(0);
        habitacionesService.guardarHabitacion(habitaciones);
        ModelAndView model = new ModelAndView("adminHecho");
        return model;
    }

    @RequestMapping(value = "/admin/habitaciones", method = RequestMethod.POST)
    public ModelAndView hotel(@RequestParam(value = "numero") Integer id) {
        List<Habitaciones> listaHabitaciones = habitacionesService.getAll();
        ModelAndView model = new ModelAndView("adminHabitaciones");
        model.addObject("listaHabitaciones", habitacionesService.conseguir(id,listaHabitaciones));
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
