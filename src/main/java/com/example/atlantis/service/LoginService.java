package com.example.atlantis.service;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Login;
import com.example.atlantis.model.RegisHotFech;
import com.example.atlantis.repository.ClienteRepository;
import com.example.atlantis.repository.HotelRepository;
import com.example.atlantis.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private HotelRepository hotelRepository;

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

    public Cliente copiartodoclienteconsession(String email){
        List<Cliente> todos = clienteRepository.findAll();
        Cliente cliente1 = new Cliente();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().getEmail().equals(email)){
                cliente1 = todos.get(i);
            }
        }
        return cliente1;
    }


    public RegisHotFech copiartodohotelconsession(String email){
        List<Hotel> todos = hotelRepository.findAll();
        RegisHotFech hotel1 = new RegisHotFech();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().getEmail().equals(email)){
                hotel1.setFecha_apertura(todos.get(i).getFecha_apertura().toString());
                hotel1.setFecha_cierre(todos.get(i).getFecha_cierre().toString());
                hotel1.setNombre(todos.get(i).getNombre());
                hotel1.setPais(todos.get(i).getPais());
                hotel1.setLocalidad(todos.get(i).getLocalidad());
                hotel1.setDireccion(todos.get(i).getDireccion());
                hotel1.setNum_estrellas(todos.get(i).getNum_estrellas());
                hotel1.setTelefono(todos.get(i).getTelefono());
                hotel1.setTipo_hotel(todos.get(i).getTipo_hotel());
                hotel1.setUrl_icono(todos.get(i).getUrl_icono());
                hotel1.setUrl_imagen_general(todos.get(i).getUrl_imagen_general());
                hotel1.setEmail(new Login());
                hotel1.getEmail().setEmail(todos.get(i).getEmail().getEmail());
                hotel1.getEmail().setPassword(todos.get(i).getEmail().getPassword());
                hotel1.setId(todos.get(i).getId());
            }
        }
        return hotel1;
    }


    public Hotel cogerid(String email){
        List<Hotel> todos = hotelRepository.findAll();
        Hotel hotel = new Hotel();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().getEmail().equals(email)){
                hotel.setId(todos.get(i).getId());
                hotel.setEmail(new Login());
                hotel.getEmail().setPassword(todos.get(i).getEmail().getPassword());
                hotel.getEmail().setRol(todos.get(i).getEmail().getRol());
                hotel.getEmail().setEmail(todos.get(i).getEmail().getEmail());
            }
        }
        return hotel;
    }

    public Cliente cogeridcliente(String email){
        List<Cliente> todos = clienteRepository.findAll();
        Cliente cliente = new Cliente();

        for(int i = 0; i < todos.size(); i++ ){
            if(todos.get(i).getEmail().getEmail().equals(email)){
                cliente.setId(todos.get(i).getId());
                cliente.setEmail(new Login());
                cliente.getEmail().setPassword(todos.get(i).getEmail().getPassword());
                cliente.getEmail().setRol(todos.get(i).getEmail().getRol());
                cliente.getEmail().setEmail(todos.get(i).getEmail().getEmail());
            }
        }
        return cliente;
    }


}
