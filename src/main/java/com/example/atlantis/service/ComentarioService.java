package com.example.atlantis.service;
import com.example.atlantis.model.*;
import com.example.atlantis.repository.*;
import com.example.atlantis.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComentarioService {
    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    ComentarioLikeRepository comentarioLikeRepository;

    @Autowired
    ComentarioHotelRepository comentarioHotelRepository;

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

    public Comentario getById(Integer id){
        return comentarioRepository.findById(id).orElse(null);
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
            if (x.getHotel().getId() == id){
                fin.add(x);
            }
        }
        return fin;
    }
    public Integer mediaPuntuacion (Integer id){
        List<Comentario> lista = comentarioRepository.findAll().stream().filter(x -> x.getHotel().getId().equals(id)).collect(Collectors.toList());
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
    public void guardarComentarioHotel (ComentarioHotel comentario){
        comentarioHotelRepository.save(comentario);
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
    public ComentarioHotel comentarioIDHotel (Integer idhotel, Integer idcliente, Integer idcomentario, ComentarioHotel comentario){

        List<Hotel> hoteles = hotelRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        Comentario comentarioID = comentarioRepository.findAll().stream().filter(x-> x.getId().equals(idcomentario)).collect(Collectors.toList()).get(0);



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
        comentario.setComentario(comentarioID);
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

    public void likedislike (Integer idcliente, Integer idcomentario, Integer idhotel, Integer like, Integer dislike){
        List<Comentario> comentario = comentarioRepository.findAll().stream().filter(x -> x.getId().equals(idcomentario)).collect(Collectors.toList());
        Comentario comentariofinal = new Comentario();

        for (Comentario x:comentario){
            comentariofinal = x;
        }

        List<Cliente> cliente = clienteRepository.findAll().stream().filter(x-> x.getId().equals(idcliente)).collect(Collectors.toList());
        Cliente clientefinal = new Cliente();

        for (Cliente x: cliente){
            clientefinal = x;
        }

        List<Hotel> hoteles = hotelRepository.findAll().stream().filter(x-> x.getId().equals(idhotel)).collect(Collectors.toList());
        Hotel hotelfinal = new Hotel();

        for (Hotel x: hoteles){
            hotelfinal = x;
        }

        ComentarioLike likes = new ComentarioLike();
        Integer numerolike = like - dislike;
        likes.setId_cliente(clientefinal);
        likes.setId_comentario(comentariofinal);
        likes.setPuntuacion(numerolike);
        likes.setId_hotel(hotelfinal);

        comentarioLikeRepository.save(likes);
    }

    public void sumalikes (Integer idcomentario){
        List<ComentarioLike> listaComentarios = comentarioLikeRepository.findAll().stream().filter(x-> x.getId_comentario().getId().equals(idcomentario)).collect(Collectors.toList());


        Integer numero = 0;

        for (ComentarioLike x: listaComentarios){
            numero = numero + x.getPuntuacion();
        }

        List<Comentario> comentarios = comentarioRepository.findAll().stream().filter(x -> x.getId().equals(idcomentario)).collect(Collectors.toList());
        Comentario comentario = new Comentario();

        for (Comentario x: comentarios){
            comentario = x;
        }

        comentario.setLikes(numero);
        comentarioRepository.save(comentario);

    }

    public void trituradoraLikes (List<ComentarioLike> comentarioLikes){
        for (ComentarioLike x: comentarioLikes){
            comentarioLikeRepository.delete(x);
        }
    }

    public void trituradoraComentariosHotel (List<ComentarioHotel> comentarioHoteles){
        for (ComentarioHotel x: comentarioHoteles){
            comentarioHotelRepository.delete(x);
        }
    }

}
