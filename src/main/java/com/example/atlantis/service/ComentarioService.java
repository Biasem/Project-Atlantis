package com.example.atlantis.service;
import com.example.atlantis.model.*;
import com.example.atlantis.repository.ClienteRepository;
import com.example.atlantis.repository.ComentarioRepository;
import com.example.atlantis.repository.HotelRepository;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ComentarioService {
    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    HotelRepository hotelRepository;

    public List<Comentario> conseguirComentarios (Integer id){
        List<Comentario> lista = comentarioRepository.findAll();
        List<Comentario> fin = new ArrayList<>();
        for (Comentario x: lista){
            if (x.getId_hotel().getId() == id){
                fin.add(x);
            }
        }
        return fin;
    }

    public void guardarComentario (Comentario comentario){
        comentarioRepository.save(comentario);
    }

    public Comentario comentarioID (Integer idhotel, Integer idcliente, Comentario comentario){

        List<Hotel> hoteles = hotelRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();

        Hotel hotel = new Hotel();
        Cliente cliente = new Cliente();

        for (Hotel x: hoteles){
            if(x.getId()==idhotel){
                hotel = x;
            }
        }
        for (Cliente x: clientes){
            if(x.getId()==idcliente){
                cliente = x;
            }
        }

        comentario.setId_cliente(cliente);
        comentario.setId_hotel(hotel);
        return comentario;
    }

}
