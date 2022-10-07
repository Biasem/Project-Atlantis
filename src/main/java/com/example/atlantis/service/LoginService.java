package com.example.atlantis.service;

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
        List<Login> todos = getAll();
        boolean registrado = false;

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


}
