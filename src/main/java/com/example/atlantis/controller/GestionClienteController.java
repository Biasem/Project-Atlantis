package com.example.atlantis.controller;

import com.example.atlantis.model.*;
import com.example.atlantis.repository.ComentarioHotelRepository;
import com.example.atlantis.repository.ComentarioLikeRepository;
import com.example.atlantis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;


@Controller
public class GestionClienteController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private LoginService loginService;

    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ComentarioLikeRepository comentarioLikeRepository;
    @Autowired
    private ComentarioHotelRepository comentarioHotelRepository;



    @GetMapping("/todos")
    @QueryMapping
    public List<Cliente> getAll() {
        return clienteService.getAll();
    }


    @GetMapping("/borrarcliente")
    public String deleteCliente(Model model,@ModelAttribute Cliente cliente) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Cliente cliente1 = loginService.copiartodoclienteconsession(correo);
        model.addAttribute("cliente", cliente1);
        return "borrarcliente";
    }

    @PostMapping("/borrarcliente")
    @SchemaMapping(typeName = "Mutation", value = "deleteCliente2")

    public String deleteCliente2(@RequestBody @Argument(name = "input") GraphqlInput.ClienteInput input) {
        Cliente cliente2 = new Cliente();
        //Encriptado y recogida de datos de al sesión apra copiar todo el modelo
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(input.getEmail().getEmail()!=null){
            cliente2 = clienteService.copiartodocliente(input);

        }else {
            //Encriptado y recogida de datos de al sesión para copiar todo el modelo
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();

             cliente2 = loginService.copiartodoclienteconsession(correo);
        }

        //If para saber si los datos estan correctos y pueden borrar o no
        if (cliente2.getEmail().getEmail().equals(input.getEmail().getEmail()) && encoder.matches(input.getEmail().getPassword(), cliente2.getEmail().getPassword())) {
            clienteService.borrarCliente(input);

            return "redirect:/logout";
        }
        else {
            return "redirect:/borrarcliente";
        }
    }


    @GetMapping("/editarcliente")
    public String editarCliente(Model model, @ModelAttribute Cliente cliente) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Cliente cliente1 = loginService.copiartodoclienteconsession(correo);
        model.addAttribute("cliente", cliente1);


        //Listas para introducir en el html los paises que queremos que salgan
        List<String> listpais = Arrays.asList("España", "Francia", "Alemania");
        model.addAttribute("listpais", listpais);


        return "editarcliente";
    }


    @PostMapping("/editarcliente")
    @SchemaMapping(typeName = "Mutation", value = "editarCliente2")
    public String editarCliente2(@RequestBody @Argument(name = "input") GraphqlInput.ClienteInput input) {
        //If para verificar que los datos introducidos sean tal cual se necesite
        if(input.getNombre() != null && input.getApellidos() != null
                && clienteService.validarDNI(input.getDni()) != false) {

            if(input.getEmail().getEmail()!=null){
                input.setId(null);
                input.getEmail().setRol(GraphqlInput.RolInput.CLIENTE);

            }else {

                //Recogida datos de sesión e insertarlos en el modelo
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String correo = auth.getName();
                Cliente datos = loginService.cogeridcliente(correo);
                input.setId(datos.getId());
 //               input.setEmail(new Login());
                input.getEmail().setPassword(datos.getEmail().getPassword());
                input.getEmail().setRol(GraphqlInput.RolInput.CLIENTE);
                input.getEmail().setEmail(datos.getEmail().getEmail());
            }


            //Introduccion de datos a Service para meter en ddbb
        clienteService.editarCliente(input);
            return "redirect:/main";

        }else{

            return "redirect:/editarcliente";
        }
    }


}
