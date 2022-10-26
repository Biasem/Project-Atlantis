package com.example.atlantis.service;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Login;
import com.example.atlantis.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    public List<Login> getAll() {
        return loginRepository.findAll();
    }


    public boolean Buscar(Login login) {
        //Lista con todos los registros de login
        List<Login> todos = getAll();
        boolean registrado = false;

        //Validación de si existe el email y el password en un determinado registro, devolviendo un boolean
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getEmail().equals(login.getEmail()) && todos.get(i).getPassword().equals(login.getPassword())) {
                registrado = true;
                break;
            } else {
                registrado = false;
            }
        }

        return registrado;
    }

    public void guardarLogin(Login login) {
        loginRepository.save(login);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Recuperates el usuario
        Login login = loginRepository.findTopByEmail(username);

        //Mapeamos los roles
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(login.getRol().toString()));

        //Creamos y devolvemos un UserDetails con los datos del usuario
        return new User(login.getEmail(), login.getPassword(), roles);
    }

    public boolean existe(Login login) {
        List<Login> todos = getAll();
        boolean registrado = false;
        for (int i = 0; i < todos.size(); i++) {
            if (login.getEmail().equals(todos.get(i).getEmail())) {
                registrado = false;
                break;
            } else {
                registrado = true;

            }

        }
        return registrado;


    }
}
