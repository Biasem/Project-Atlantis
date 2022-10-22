package com.example.atlantis.service;
import com.example.atlantis.model.BuscadorID;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Comentario;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.repository.ComentarioRepository;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ComentarioService {
    @Autowired ComentarioRepository comentarioRepository;

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
}
