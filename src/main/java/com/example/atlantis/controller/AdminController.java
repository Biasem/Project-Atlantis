package com.example.atlantis.controller;

import com.example.atlantis.model.*;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
    private HabitacionesService habitacionesService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private Precio_HabitacionService precio_habitacionService;

    @Autowired
    private RegimenService regimenService;
    @Autowired
    private Habitacion_Reserva_HotelService habitacion_reserva_hotelService;

    @Autowired
    private LoginService loginService;

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
        // Obtención de datos necesarios y añadidos al modelo
        List<TipoHab> tipohab = habitacionesService.todoHab();
        List<Regimen> regimenes = regimenService.regimenHotel(idHotel);
        List<TipoRegimen> regimen = regimenService.checkRegimen(regimenes);
        model.addObject("regimen", regimen);
        model.addObject("regimenes", regimenes);
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
    @RequestMapping(value = "/admin/habitaciones/crear/regimen/hecho/", method = RequestMethod.POST)
    public ModelAndView crearRegimen(@ModelAttribute Regimen regimen,
                                     @RequestParam("idhotel") Integer idhotel,
                                     @RequestParam("categoria") TipoRegimen categoria,
                                     @RequestParam("precio") Double precio) {

        //Obteción de datos y creación de modelo
        ModelAndView model = new ModelAndView();
        List<Regimen> regimenes = regimenService.getAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Hotel hotel1 = loginService.cogerid(correo);
        regimen.setId(hotel1.getId());
        boolean bool = true;

        //Bucle para visualización de booleano si es falso por tener el regimen igual o no
        for(Regimen r : regimenes) {

            if (r.getCategoria().equals(regimen.getCategoria()) && r.getId_hotel().getId().equals(regimen.getId())) {

                model = new ModelAndView("adminHecho");
                bool = false;
                break;
            }
        }
               if(bool !=false) {
                   model = new ModelAndView("adminHecho");
                   Regimen nuevoRegimen = new Regimen();
                   nuevoRegimen.setCategoria(categoria);
                   nuevoRegimen.setPrecio(precio);
                   List<Hotel> hotel = regimenService.conseguirHotel(idhotel);
                   Hotel hotelfinal = new Hotel();
                   for (Hotel x : hotel) {
                       if (x.getId().equals(idhotel)) ;
                       hotelfinal = x;
                   }
                   nuevoRegimen.setId_hotel(hotelfinal);
                   regimenService.guardarRegimen(nuevoRegimen);
               }
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
    @RequestMapping("/admin/habitaciones/regimen/borrar/{item}")
    public @ResponseBody ModelAndView borrarRegimen(@PathVariable(value="item") String numerito,
                                                     @RequestParam(value = "id") Integer id) {

        //Gestíón sesión
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

        //Borrado del regimen
        Integer preciofinal = regimenService.conseguirRegimenIDHotel(id);

        if (preciofinal == idHotel){
            Integer comprobar = habitacion_reserva_hotelService.checkearReserva(id);
            if (comprobar.equals(0)){
                regimenService.borrarRegimen(id);
                return model;
            }
            else{
                ModelAndView error = new ModelAndView("errorAdmin");
                return error;
            }

        }

        else{
            ModelAndView error = new ModelAndView("error/403");
            return error;
        }
    }

    @RequestMapping("/admin/habitaciones/editar/{item}")
    public @ResponseBody ModelAndView editarHabitacion(@PathVariable(value="item") String numerito,
                                                       @RequestParam(value = "id") Integer id) {

        //Gestión sesión.
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

        //Error si datos fallan o edición de hotel
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

        //Gestión sesión
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

        //Borrado del precio
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
