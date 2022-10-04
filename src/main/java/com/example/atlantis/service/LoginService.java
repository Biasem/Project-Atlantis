package com.example.atlantis.service;

import com.example.atlantis.model.Login;
import com.example.atlantis.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;


    public Login buscarPorUsername(String username){

        return loginRepository.findAll().indexOf(username).orElse(null);
    }

    public void UsuarioLogin(String username)



}
