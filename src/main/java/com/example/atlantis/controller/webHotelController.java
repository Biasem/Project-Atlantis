package com.example.atlantis.controller;
import com.example.atlantis.model.BuscadorID;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.service.BusquedaService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.atlantis.service.BuscadorIDService;

import java.util.Collections;
import java.util.List;

@Controller
public class webHotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private BuscadorIDService buscadorService;

    @RequestMapping(value="hoteles", method = RequestMethod.GET)
    public @ResponseBody ModelAndView resultadoHotel(@RequestParam("item") String itemid) {
        List<Hotel> listaHoteles = hotelService.getAll();
        BuscadorID numero = new BuscadorID(Integer.valueOf(itemid));
        Hotel definitivo = buscadorService.Comparar(numero,listaHoteles);
        ModelAndView model = new ModelAndView("hotelWeb");
        model.addObject("definitivo", definitivo);
        return model;
    }
}
