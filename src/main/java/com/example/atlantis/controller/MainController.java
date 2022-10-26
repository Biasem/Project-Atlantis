package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.BusquedaService;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.HotelService;
import com.example.atlantis.service.RegimenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController{
    @Autowired
    private HotelService hotelService;

    @Autowired
    private BusquedaService busquedaService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/main")
    public ModelAndView listaHotel(@ModelAttribute Busqueda busqueda, HttpSession session) {
        ModelAndView model = new ModelAndView("main");

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

        List<Hotel> listaprimera = hotelService.getAll();
        Collections.shuffle(listaprimera);
        List<Hotel> listaHotel = listaprimera.subList(0, 3);

        model.addObject("listaHotel", listaHotel);
        model.addObject("fechamin", LocalDate.now());
        model.addObject("busqueda", busqueda);
        return model;
    }

    @GetMapping("/hoteleditar")
    public ModelAndView registrarHotel() {
        ModelAndView model = new ModelAndView("registroHotel");
        return model;
    }


    @PostMapping("/main")
    public ModelAndView listaHoteles(@ModelAttribute Busqueda busqueda, HttpSession session) {
        ModelAndView model = new ModelAndView("resultado");

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
        List<Hotel> filtro = busquedaService.AccionBuscar(busqueda,listaHoteles);
        if(LocalDate.parse(busqueda.getFechaInicial()).isAfter(LocalDate.parse(busqueda.getFechaFinal())))
        {
            return new ModelAndView("redirect:main");
        }
        model.addObject("fechamin", LocalDate.now());
        model.addObject("filtro", filtro);
        return model ;
    }

    @GetMapping("/")
    public String irAMain(@ModelAttribute Busqueda busqueda) {
        return "main";
    }
}
