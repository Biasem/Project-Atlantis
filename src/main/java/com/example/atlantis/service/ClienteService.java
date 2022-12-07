package com.example.atlantis.service;

import com.example.atlantis.model.*;
import com.example.atlantis.repository.ClienteRepository;
import com.example.atlantis.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.List;
import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;
import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class ClienteService {
    private static Faker faker = new Faker();
    private Rol rol;


    @Autowired
    private ClienteRepository clienteRepository;



    public List<Cliente> getAll(){
        return clienteRepository.findAll();
    }

    public Cliente getById(int id){

        return clienteRepository.findById(id).orElse(null);

    }


    private final String dniChars="TRWAGMYFPDXBNJZSQVHLCKE";

    public boolean validarDNI(String itDNI) {

        String intPartDNI = itDNI.trim().replaceAll(" ", "").substring(0, 7);
        char ltrDNI = itDNI.charAt(8);
        int valNumDni = Integer.parseInt(intPartDNI) % 23;

        if (itDNI.length()!= 9 && isNumeric(intPartDNI) == false && dniChars.charAt(valNumDni)!= ltrDNI) {

            return false;
        } else {

            return true;
        }

    }

    public Cliente guardarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }


    public Cliente copiartodoclienteApi(String cliente){

        List<Cliente> todos = clienteRepository.findAll();
        Cliente cliente1 = new Cliente();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().getEmail().equals(cliente)){
                cliente1 = todos.get(i);
            }
        }

        return cliente1;
    }

    public Cliente copiartodocliente(Cliente cliente){

        List<Cliente> todos = clienteRepository.findAll();
        Cliente cliente1 = new Cliente();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().getEmail().equals(cliente.getEmail().getEmail())){
                cliente1 = todos.get(i);
            }
        }

        return cliente1;
    }

    public void borrarClienteApi(Cliente cliente){

        List<Cliente> todos = clienteRepository.findAll();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().equals(cliente.getEmail())){
                clienteRepository.delete(cliente);
            }
        }
    }

    public void borrarCliente(Cliente cliente){

        List<Cliente> todos = clienteRepository.findAll();
        Cliente cliente1 = copiartodocliente(cliente);

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().equals(cliente1.getEmail())){
                clienteRepository.delete(cliente1);
            }
        }
    }

    public void editarClienteApi(@RequestBody GraphqlInput.ClienteInput input){

        clienteRepository.editarCliente(input.getNombre(),input.getApellidos(),input.getDni(), input.getPais(),
               input.getTelefono(), input.getEmail().getEmail());

    }

    public void editarCliente(Cliente cliente){

        clienteRepository.editarCliente(cliente.getNombre(),cliente.getApellidos(),cliente.getDni(), cliente.getPais(),
                cliente.getTelefono(), cliente.getEmail().getEmail());

    }

    public Integer conseguirId(String correo){

        List<Cliente> clientes = clienteRepository.findAll();
        Integer id = 0;

        for (Cliente x: clientes){
            if (x.getEmail().getEmail().equals(correo)){
                id = x.getId();
            }
        }

        return id;
    }

    public Cliente crearCliente(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Login login = new Login();
        Cliente cliente = new Cliente();

        login.setEmail(faker.internet().emailAddress());
        login.setPassword(passwordEncoder.encode("1234"));
        login.setRol(rol.CLIENTE);
        cliente.setEmail(login);

        cliente.setApellidos(faker.name().lastName());
        cliente.setNombre(faker.name().name());
        cliente.setPais(faker.country().name());
        cliente.setDni(""+faker.phoneNumber().subscriberNumber(8)+
                faker.letterify("?"));
        cliente.setTelefono(faker.phoneNumber().subscriberNumber(9));
        return cliente;
    }

}