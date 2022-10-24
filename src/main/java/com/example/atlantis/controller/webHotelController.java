package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;

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


    @RequestMapping(value = "/hoteles/{item}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView resultadoHotel(@PathVariable(value="item") String numerito,
                                                     @RequestParam(value = "id") Integer id,
                                                     HttpSession session) {

        ModelAndView model = new ModelAndView("hotelWeb");
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
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        model.addObject("usuario", usuario);
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

        Objeto_Aux_Reserva objetoInteger = new Objeto_Aux_Reserva();
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
    public String reservarHab (@RequestBody @ModelAttribute("objeto_integer") Objeto_Aux_Reserva objeto_aux_reserva,
                               @RequestParam("idhotel") Integer id){

        List<Regimen> regimenList =regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(id)).collect(Collectors.toList());
        List<Regimen> regimenAux = new ArrayList<>();
        List<Double> sumaPreciosRegimenHab = new ArrayList<>();
        List<Double> sumaPreciosFechaHab = new ArrayList<>();
        for(int i=0;i<objeto_aux_reserva.getNum().size();i++){
            for (Regimen r : regimenList){
                if(TipoRegimen.valueOf(objeto_aux_reserva.getId_regimen().get(i)).equals(r.getCategoria())){
                    sumaPreciosRegimenHab.add(r.getPrecio()*objeto_aux_reserva.getNum().get(i));
                }
            }
        }

        System.out.println("num habitaciones");
        System.out.println(objeto_aux_reserva.getNum());
        System.out.println("tipo regimen");
        System.out.println(objeto_aux_reserva.getId_regimen());
        System.out.println("id hotel: " + id);
        System.out.println("suma precios regimen con num habitaciones");
        System.out.println(sumaPreciosRegimenHab);
        System.out.println("suma precios habitaciones por fechas");
        System.out.println(objeto_aux_reserva.getFechainicio());
        System.out.println(objeto_aux_reserva.getFechafin());




        return "redirect:/main";
    }




}
