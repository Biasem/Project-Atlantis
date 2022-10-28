package com.example.atlantis.service;

import com.example.atlantis.model.*;
import com.example.atlantis.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.ReverbType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;


    public List<Reserva> getAll(){
        return reservaRepository.findAll();
    }

    public Reserva getById(int id){
        return reservaRepository.findById(id).orElse(null);
    }

    public List<Reserva> todasReservas(int id){
       List<Reserva> todas = reservaRepository.findAll();
       List<Reserva> buscadas = new ArrayList<>();

        for(int i = 0; i < todas.size(); i++ ){
            if(todas.get(i).getId_cliente().getId().equals(id)){
                buscadas.add(todas.get(i));
            }
        }
        return buscadas;


    }

    public List<Reserva> todasReservasHotel(int id){
        List<Reserva> todas = reservaRepository.findAll();
        List<Reserva> buscadas = new ArrayList<>();

        for(int i = 0; i < todas.size(); i++ ){
            if(todas.get(i).getId_hotel().getId().equals(id)){
                buscadas.add(todas.get(i));
            }
        }
        return buscadas;


    }


    public List<HistorialReservaClientes> cambiomodelohistorial(List<Reserva> listareserva, List<Hab_Reserva_Hotel> listahabre, List<Regimen> listaregimen){

        List<HistorialReservaClientes> cambiados = new ArrayList<>();

        for(int i = 0; i < listareserva.size(); i++ )
        {
            for(int a = 0; a < listahabre.size(); a++ ){
                if(listahabre.get(a).getReserva().getId().equals(listareserva.get(i).getId())) {
                    HistorialReservaClientes reservas = new HistorialReservaClientes();
                    reservas.setId(listareserva.get(i).getId());
                    reservas.setFecha_entrada(listareserva.get(i).getFecha_entrada());
                    reservas.setFecha_salida(listareserva.get(i).getFecha_salida());
                    reservas.setNum_clientes(listareserva.get(i).getNum_clientes());
                    reservas.setPrecio_total(listareserva.get(i).getPrecio_total());
                    reservas.setCategoria(listahabre.get(a).getId_regimen().getCategoria());
                    reservas.setNombreHotel(listahabre.get(a).getReserva().getId_hotel().getNombre());
                    cambiados.add(reservas);
                }
                        }
                    }
        return cambiados;
    }


    public List<HistorialReservaClientes> cambiomodelohistorialvigente(List<Reserva> listareserva, List<Hab_Reserva_Hotel> listahabre, List<Regimen> listaregimen){

        List<HistorialReservaClientes> cambiados = new ArrayList<>();

        for(int i = 0; i < listareserva.size(); i++ ) {
            if (listareserva.get(i).getFecha_salida().isAfter(LocalDate.now())) {
                for (int a = 0; a < listahabre.size(); a++) {
                    if (listahabre.get(a).getReserva().getId().equals(listareserva.get(i).getId())) {
                        HistorialReservaClientes reservas = new HistorialReservaClientes();
                        reservas.setId(listareserva.get(i).getId());
                        reservas.setFecha_entrada(listareserva.get(i).getFecha_entrada());
                        reservas.setFecha_salida(listareserva.get(i).getFecha_salida());
                        reservas.setNum_clientes(listareserva.get(i).getNum_clientes());
                        reservas.setPrecio_total(listareserva.get(i).getPrecio_total());
                        reservas.setCategoria(listahabre.get(a).getId_regimen().getCategoria());
                        reservas.setNombreHotel(listahabre.get(a).getReserva().getId_hotel().getNombre());
                        cambiados.add(reservas);
                    }
                }
            }
        }
        return cambiados;
    }




    public List<HistorialReservaHotel> cambiomodelohistorialhotel(List<Reserva> listareserva, List<Hab_Reserva_Hotel> listahabre, List<Regimen> listaregimen){

        List<HistorialReservaHotel> cambiados = new ArrayList<>();

        for(int i = 0; i < listareserva.size(); i++ )
        {
            for(int a = 0; a < listahabre.size(); a++ ){
                if(listahabre.get(a).getReserva().getId().equals(listareserva.get(i).getId())) {
                    HistorialReservaHotel reservas = new HistorialReservaHotel();
                    reservas.setId(listareserva.get(i).getId());
                    reservas.setNombreCliente(listareserva.get(i).getId_cliente().getNombre());
                    reservas.setApellidosCliente(listareserva.get(i).getId_cliente().getApellidos());
                    reservas.setEmailCliente(listareserva.get(i).getId_cliente().getEmail().getEmail());
                    reservas.setNum_clientes(listahabre.get(a).getReserva().getNum_clientes());
                    reservas.setFecha_entrada(listahabre.get(a).getReserva().getFecha_entrada());
                    reservas.setFecha_salida(listahabre.get(a).getReserva().getFecha_salida());
                    reservas.setCategoria(listahabre.get(a).getId_regimen().getCategoria());
                    reservas.setPrecio_total(listareserva.get(i).getPrecio_total());
                    cambiados.add(reservas);
                }
            }
        }
        return cambiados;
    }




}
