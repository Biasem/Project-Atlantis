package com.example.atlantis.controller;
import com.example.atlantis.model.*;
import com.example.atlantis.repository.ComentarioHotelRepository;
import com.example.atlantis.repository.ComentarioLikeRepository;
import com.example.atlantis.repository.ComentarioRepository;
import com.example.atlantis.service.ComentarioService;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.atlantis.service.ClienteService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class perfilController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private HotelService hotelService;
    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private ComentarioLikeRepository comentarioLikeRepository;
    @Autowired
    private ComentarioHotelRepository comentarioHotelRepository;

    @RequestMapping("/perfilcliente")
    public ModelAndView perfil(HttpSession session){
        ModelAndView model = new ModelAndView("perfilCliente");
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
        Cliente cliente = clienteService.getById(idCliente);
        List<Comentario> comentarios = new ArrayList<>();
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        model.addObject("cliente", cliente);
        model.addObject("correo", correo);
        model.addObject("comentarios",comentarioService.conseguirComentariosCliente(idCliente));
        return model;
    }
    @RequestMapping("/perfilhotel")
    public ModelAndView perfilhotel(HttpSession session){
        ModelAndView model = new ModelAndView("perfilHotel");
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

        Hotel hotel = hotelService.getById(idHotel);
        List<Comentario> comentarios = new ArrayList<>();
        model.addObject("hotel", hotel);
        model.addObject("correo", correo);
        model.addObject("comentarios",comentarioService.conseguirComentariosHotel(idHotel));
        model.addObject("media", comentarioService.mediaPuntuacion(idHotel));
        model.addObject("estrellas", hotel.getNum_estrellas());
        return model;
    }

    @RequestMapping("/perfilcliente/borrarcomentario")
    public ModelAndView borrarComentario(@RequestParam("idcomentario") Integer id){
        ModelAndView model = new ModelAndView("comentarioHecho");
        model.addObject("idcomentario", id);
        List<ComentarioLike> likes = comentarioLikeRepository.findAll().stream().filter(x-> x.getId_comentario().getId().equals(id)).collect(Collectors.toList());
        List<ComentarioHotel> comentarioHoteles = comentarioHotelRepository.findAll().stream().filter(x-> x.getComentario().getId().equals(id)).collect(Collectors.toList());
        comentarioService.trituradoraLikes(likes);
        comentarioService.trituradoraComentariosHotel(comentarioHoteles);
        Comentario borrar = comentarioService.getById(id);
        comentarioRepository.delete(borrar);
        return model;
    }

}