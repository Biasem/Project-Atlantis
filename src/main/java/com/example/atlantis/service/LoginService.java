package com.example.atlantis.service;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Login;
import com.example.atlantis.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public List<Login> getAll(){
        return loginRepository.findAll();
    }


    public boolean Buscar(Login login){
        //Lista con todos los registros de login
        List<Login> todos = getAll();
        boolean registrado = false;

        //Validación de si existe el email y el password en un determinado registro, devolviendo un boolean
        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().equals(login.getEmail()) && todos.get(i).getPassword().equals(login.getPassword())){
                registrado = true;
                break;
            }
            else {
                registrado = false;
            }
        }

    return registrado;
    }

    public void guardarLogin(Login login){
        loginRepository.save(login);
    }




}
