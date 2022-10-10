package com.example.atlantis.service;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public void guardarCliente(Cliente cliente){

        clienteRepository.save(cliente);
    }



}