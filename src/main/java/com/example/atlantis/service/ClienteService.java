package com.example.atlantis.service;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Login;
import com.example.atlantis.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;

@Service
public class ClienteService {

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

    public void guardarCliente(Cliente cliente){
        clienteRepository.save(cliente);
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

    public void borrarCliente(Cliente cliente){
        List<Cliente> todos = clienteRepository.findAll();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().equals(cliente.getEmail())){

                clienteRepository.delete(cliente);
            }
        }
    }

    public void editarCliente(Cliente cliente){

        clienteRepository.editarCliente(cliente.getNombre(),cliente.getApellidos(),cliente.getDni(), cliente.getPais(),
                cliente.getTelefono(), cliente.getEmail().getEmail());

    }

    public Integer conseguirId(Login usuario){
        List<Cliente> clientes = clienteRepository.findAll();
        Integer id = 0;
        for (Cliente x: clientes){
            if (x.getEmail().getEmail().equals(usuario.getEmail())){
                id = x.getId();
            }
            else {

            }
        }
        return id;
    }

    public Cliente obtenerCliDeSesion(Login login){
        Cliente cliente = clienteRepository.buscarConSession(login.getEmail());


        return cliente;
    }

    


}