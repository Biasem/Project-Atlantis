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
import java.util.stream.Collectors;

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
            if (x.getHotel().getId() == id){
                fin.add(x);
            }
        }
        return fin;
    }
    public List<Comentario> conseguirComentariosCliente (Integer id){
        List<Comentario> lista = comentarioRepository.findAll();
        List<Comentario> fin = new ArrayList<>();
        for (Comentario x: lista){
            if (x.getCliente().getId() == id){
                fin.add(x);
            }
        }
        return fin;
    }
    public List<Comentario> conseguirComentariosHotel (Integer id){
        List<Comentario> lista = comentarioRepository.findAll();
        List<Comentario> fin = new ArrayList<>();
        for (Comentario x: lista){
            if (x.getId_hotel().getId() == id){
                fin.add(x);
            }
        }
        return fin;
    }
    public Integer mediaPuntuacion (Integer id){
        List<Comentario> lista = comentarioRepository.findAll().stream().filter(x -> x.getId_hotel().getId().equals(id)).collect(Collectors.toList());
        Integer numero = 0;
        Integer divisor = 0;
        for (Comentario x: lista){
            numero = numero + x.getPuntuacion();
            divisor = divisor + 1;
        }
        if (numero.equals(0) && divisor.equals(0)){
            return numero;
        }
        else{
            Integer media = numero/divisor;
            return media;
        }
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

        comentario.setCliente(cliente);
        comentario.setHotel(hotel);
        return comentario;
    }

    public List<Comentario> conseguirComentariosClienteId(Cliente cliente){
        List<Comentario> obtenidos = new ArrayList<>();
        List<Comentario> lista = comentarioRepository.findAll();

        for(int i = 0; i < lista.size(); i++ ){
         if (lista.get(i).getCliente().getId().equals(cliente.getId())){
             obtenidos.add(lista.get(i));
         }
        }

        return obtenidos;
    }


    public void borrarcomentarios(Cliente cliente, List<Comentario> comentarios){

        for(int i = 0; i < comentarios.size(); i++ ){
            comentarioRepository.delete(comentarios.get(i));
        }
    }

}
