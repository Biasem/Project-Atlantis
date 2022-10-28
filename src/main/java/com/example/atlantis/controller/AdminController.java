package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.repository.HabitacionesRepository;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private HabitacionesRepository habitacionesRepository;

    @Autowired
    private Precio_HabitacionService precio_habitacionService;

    @GetMapping("/admin")
    public ModelAndView admin(HttpSession session) {
        ModelAndView model = new ModelAndView("adminTest");
        // Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idCliente = 0;
        Integer idHotel = 0;
        if (correo != null){
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
            System.out.println(idCliente);
            System.out.println(idHotel);
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        // Gestión sesión
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

        List<Habitaciones> habitaciones = habitacionesService.getAll();
        Habitaciones habitacion = new Habitaciones();
        habitacion = habitacionesService.conseguirHabitacion(id, habitaciones);
        List<Precio_Hab> precios = precio_habitacionService.getAll();
        precio_habitacionService.borrarLista(precios, habitacion);
        habitacionesService.borrarHabitacion(id);
        ModelAndView model = new ModelAndView("adminHecho");
        return model;
    }

    @RequestMapping("/admin/habitaciones/editar/{item}")
    public @ResponseBody ModelAndView editarHabitacion(@PathVariable(value="item") String numerito,
                                                       @RequestParam(value = "id") Integer id) {

        ModelAndView model = new ModelAndView("adminHabitacionEditar");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idCliente = 0;
        Integer idHotel = 0;

        if (correo != null){
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
            System.out.println(idCliente);
            System.out.println(idHotel);
        }

        Integer puede = habitacionesService.puedeEntrar(idHotel,id);
        if (puede == 0){
            ModelAndView error = new ModelAndView("error/403");
            return error;
        }

        else{
            List<Habitaciones> habitacion = new ArrayList<>();
            List<Precio_Hab> precios = precio_habitacionService.filtrarHabitacion(id);
            habitacion.add(habitacionesService.getById(id));
            List<TipoHab> tipohab = habitacionesService.todoHab();

            model.addObject("tipohab", tipohab);
            model.addObject("habitacion", habitacion);
            model.addObject("habitaciones", new Habitaciones());
            model.addObject("precios",precios);
            model.addObject("nuevoprecio", new Precio_Hab());
            model.addObject("idHotel", idHotel);
            return model;
        }
    }

    @RequestMapping("/admin/habitaciones/editar/hecho/{item}")
    public @ResponseBody ModelAndView editarHabitacionhecho(@PathVariable(value="item") String numerito,
                                                       @RequestParam(value = "id") Integer id,
                                                       @ModelAttribute Habitaciones habitaciones) {
        habitacionesService.editarHabitacion(id, habitaciones);
        ModelAndView model = new ModelAndView("adminHecho");
        return model;
    }

    @RequestMapping("/admin/habitaciones/editar/precio/hecho/{item}")
    public @ResponseBody ModelAndView crearPrecio(@PathVariable(value="item") String numerito,
                                                       @RequestParam(value = "id") Integer id,
                                                       @ModelAttribute Precio_Hab nuevoprecio,
                                                        @RequestParam("fechainicio") String fechainicio,
                                                        @RequestParam("fechafin") String fechafin,
                                                        @RequestParam("idhotel") Integer idhotel) {

        ModelAndView model = new ModelAndView("adminHecho");
        nuevoprecio.setFecha_inicio(LocalDate.parse(fechainicio));
        nuevoprecio.setFecha_fin(LocalDate.parse(fechafin));
        Hotel hotel = precio_habitacionService.conseguirIDHotelprecio(idhotel);
        Habitaciones habitacion = precio_habitacionService.conseguirIDHabitacionprecio(id);
        nuevoprecio.setId_hotel(hotel);
        nuevoprecio.setId_hab(habitacion);
        precio_habitacionService.guardarPrecio(nuevoprecio);
        return model;
    }

    @RequestMapping("/admin/habitaciones/precio/borrar/{item}")
    public @ResponseBody ModelAndView borrarPrecio(@PathVariable(value="item") String numerito,
                                                  @RequestParam(value = "id") Integer id) {
        ModelAndView model = new ModelAndView("adminHecho");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idCliente = 0;
        Integer idHotel = 0;

        if (correo != null){
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
            System.out.println(idCliente);
            System.out.println(idHotel);
        }

        Integer preciofinal = precio_habitacionService.conseguirPrecioHabitacion(id);

        if (preciofinal == idHotel){
            precio_habitacionService.borrarPrecio(id);
            return model;
        }

        else{
            ModelAndView error = new ModelAndView("error/403");
            return error;
        }
    }
}
