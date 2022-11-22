package com.example.atlantis.controller;


import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.GraphqlInput;
import com.example.atlantis.model.Login;
import com.example.atlantis.model.Rol;
import com.example.atlantis.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ApiController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private ClienteService clienteService;


    @PostMapping("/registroclienteApi")

    @SchemaMapping(typeName = "Mutation", value = "registroCliente")
    public String registroCliente(@RequestBody @Argument(name = "input") GraphqlInput.ClienteInput input) {

        try {
            //If para verificar que los datos introducidos sean tal cual se necesite
            if (input.getNombre() != null && input.getApellidos() != null
                    && input.getEmail().getEmail() != null &&
                    input.getEmail().getPassword() != null && input.getDni() != null
                    && clienteService.validarDNI(input.getDni()) != false) {

                //Selección de Rol Cliente para el nuevo cliente
                input.getEmail().setRol(GraphqlInput.RolInput.CLIENTE);
                input.getEmail().setPassword(bCryptPasswordEncoder.encode(input.getEmail().getPassword()));

                Cliente cliente = new Cliente();
                cliente.setApellidos(input.getApellidos());
                cliente.setNombre(input.getNombre());
                cliente.setDni(input.getDni());
                cliente.setPais(input.getPais());
                cliente.setTelefono(input.getTelefono());
                Login login = new Login();
                cliente.setEmail(login);
                cliente.getEmail().setEmail(input.getEmail().getEmail());
                cliente.getEmail().setPassword(input.getEmail().getPassword());
                cliente.getEmail().setRol(Rol.CLIENTE);


                //Guardado del cliente en base de datos
                clienteService.guardarCliente(cliente);
                System.out.println(input);

                return "Datos guardados correctamente";

            } else {
                return "Datos incorrectos";
            }
        }catch (Exception e){
            return "Datos incorrectos";
        }
    }


}

