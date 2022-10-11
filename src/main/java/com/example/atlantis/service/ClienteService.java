package com.example.atlantis.service;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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




}