package com.example.atlantis.controller;

import com.example.atlantis.model.*;
import com.example.atlantis.repository.ComentarioHotelRepository;
import com.example.atlantis.repository.ComentarioLikeRepository;
import com.example.atlantis.repository.ComentarioRepository;
import com.example.atlantis.service.HotelService;
import com.example.atlantis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GestionHotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ComentarioLikeRepository comentarioLikeRepository;

    @Autowired
    private ComentarioHotelRepository comentarioHotelRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;


    @GetMapping("/borrarhotel")
    public String deleteHotel(Model model, @ModelAttribute Hotel hotel) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        RegisHotFech hotel1 = loginService.copiartodohotelconsession(correo);
        model.addAttribute("hotel", hotel1);

        return "borrarhotel";
    }

    @PostMapping("/borrarhotel")
    public String deleteHotel2(@ModelAttribute Hotel hotel) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Hotel hotel1 = hotelService.copiartodohotel(hotel);

        if(hotel1.getEmail().getEmail().equals(hotel.getEmail().getEmail()) && encoder.matches(hotel.getEmail().getPassword(), hotel1.getEmail().getPassword())){
            List<ComentarioLike> comentarioLike = comentarioLikeRepository.findAll().stream().filter(x-> x.getId_hotel().getId().equals(hotel1.getId())).collect(Collectors.toList());
            List<ComentarioHotel> comentarioHotels = comentarioHotelRepository.findAll().stream().filter(x-> x.getHotel().getId().equals(hotel1.getId())).collect(Collectors.toList());
            List<Comentario> comentarios = comentarioRepository.findAll().stream().filter(x-> x.getHotel().getId().equals(hotel1.getId())).collect(Collectors.toList());
            comentarioLike.stream().forEach(x-> comentarioLikeRepository.delete(x));
            comentarioHotels.stream().forEach(x-> comentarioHotelRepository.delete(x));
            comentarios.stream().forEach(x-> comentarioRepository.delete(x));
            hotelService.borrarHotel(hotel1);

            return "redirect:/logout";
        }
        else{
            return "redirect:/borrarhotel";
        }
    }


    @GetMapping("/editarhotel")
        public String editarHotel(Model model, @ModelAttribute Hotel hotel) {

        //Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        RegisHotFech hotel1 = loginService.copiartodohotelconsession(correo);
        model.addAttribute("hotel", hotel1);

        //Listas para insertar en html las opciones que queramos
        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);

        List<String> listlocalidad = Arrays.asList("Sevilla", "Madrid", "Granada");
        model.addAttribute("listlocalidad", listlocalidad);

        List<String> listtiphotel = Arrays.asList("HOSTAL", "HOTEL", "APARTAHOTEL", "APARTAMENTO");
        model.addAttribute("listtiphotel", listtiphotel);

        List<String> listestrellas = Arrays.asList("0","1", "2", "3", "4", "5");
        model.addAttribute("listestrellas", listestrellas);

        return "editarhotel";
    }



    @PostMapping("/editarhotel")
    public String editarhotel2(@ModelAttribute RegisHotFech hotel) {

        //Primer if para que tenga los datos que sean obligatorios y las fechas no sean raras
        if (hotel.getNombre() != null && hotel.getDireccion() != null && hotel.getPais() != null
                && hotel.getLocalidad() != null && hotel.getFecha_apertura() != null
                && hotel.getFecha_cierre() != null && hotel.getTipo_hotel() != null
                && LocalDate.parse(hotel.getFecha_cierre()).isAfter(LocalDate.parse(hotel.getFecha_apertura()))
        ) {

            //Recogida de datos con sesión y copia del modelo entero
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();
            Hotel datos = loginService.cogerid(correo);

            hotel.setId(datos.getId());
            hotel.setEmail(new Login());
            hotel.getEmail().setPassword(datos.getEmail().getPassword());
            hotel.getEmail().setRol(datos.getEmail().getRol());
            hotel.getEmail().setEmail(datos.getEmail().getEmail());

            //Método para meter el hotel ya convertido en el modelo para ddbb
            hotelService.editarHotel(hotelService.convertirAHotel(hotel));

            return "redirect:/main";

        } else {

            return "redirect:/editarhotel";
        }
    }

}
