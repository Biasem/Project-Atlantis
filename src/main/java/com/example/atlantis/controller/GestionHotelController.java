package com.example.atlantis.controller;

import com.example.atlantis.model.*;
import com.example.atlantis.service.HotelService;
import com.example.atlantis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
@Controller
public class GestionHotelController {


    @Autowired
    private HotelService hotelService;

    @Autowired
    private LoginService loginService;



    @GetMapping("/borrarhotel")
    public String deleteHotel(Model model, @ModelAttribute Hotel hotel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        RegisHotFech hotel1 = loginService.copiartodohotelconsession(correo);

        model.addAttribute("hotel", hotel1);
        return "borrarhotel";
    }

    @PostMapping("/borrarhotel")
    @SchemaMapping(typeName = "Mutation", value = "deleteHotel2")
    public String deleteHotel2(@RequestBody @Argument(name = "input") GraphqlInput.HotelInput hotel) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();


        Hotel hotel1 = hotelService.copiartodohotel(hotel);

        if(hotel1.getEmail().getEmail().equals(hotel.getEmail().getEmail()) && encoder.matches(hotel.getEmail().getPassword(), hotel1.getEmail().getPassword())){
            hotelService.borrarHotel(hotel1);
            return "redirect:/logout";
        }
        else{
            return "redirect:/borrarhotel";
        }

    }


    @GetMapping("/editarhotel")
        public String editarHotel(Model model, @ModelAttribute Hotel hotel) {

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
    @SchemaMapping(typeName = "Mutation", value = "editarHotel2")
    public String editarhotel2(@RequestBody @Argument(name = "input") GraphqlInput.RegisHotFechInput input) {
        //Primer if para que tenga los datos que sean obligatorios y las fechas no sean raras
        if (input.getNombre() != null && input.getDireccion() != null && input.getPais() != null
                && input.getLocalidad() != null && input.getFecha_apertura() != null
                && input.getFecha_cierre() != null && input.getTipo_hotel() != null
//                && LocalDate.parse(input.getFecha_cierre()).isAfter(LocalDate.parse(input.getFecha_apertura()))
                ) {


            if(input.getEmail().getEmail()!= null){
                input.setId(null);
                input.getEmail().setRol(GraphqlInput.RolInput.HOTEL);
            }
            else {
                //Recogida de datos con sesión y copia del modelo entero
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String correo = auth.getName();
                Hotel datos = loginService.cogerid(correo);


                input.setId(datos.getId());
//            input.setEmail(new GraphqlInput.LoginInput());
                input.getEmail().setPassword(datos.getEmail().getPassword());
                input.getEmail().setRol(GraphqlInput.RolInput.HOTEL);
                input.getEmail().setEmail(datos.getEmail().getEmail());
            }
        //Método para meter el hotel ya convertido en el modelo para ddbb
            hotelService.editarHotel(hotelService.convertirAHotel(input));
            return "redirect:/main";
        } else {

            return "redirect:/editarhotel";
        }
    }



}
