package com.example.atlantis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.List;

public class GraphqlInput {


    @Data
    @AllArgsConstructor
    public class ClienteInput{
        private Integer id;
        private String nombre;
        private String apellidos;
        private String dni;
        private String pais;
        private String telefono;
        private LoginInput email;
    }


    @Data
    @AllArgsConstructor
    public class LoginInput{
        private String email;
        private String password;
        private RolInput rol;
    }



    @Data
    @AllArgsConstructor
    public class BusquedaInput{
        private String hotelBuscar;
        private String fechaInicial;
        private String fechaFinal;
        private Integer numHuespedes;

    }


    @Data
    @AllArgsConstructor
    public class RegisHotFechInput{
        private Integer id;
        private String nombre;
        private String pais;
        private String localidad;
        private String direccion;
        private String fecha_apertura;
        private String fecha_cierre;
        private Integer num_estrellas;
        private TipoHotel tipo_hotel;
        private String telefono;
        private Double latitud;
        private Double longitud;
        private LoginInput email;
        private String url_icono;
        private String url_imagen_general;
    }

    @Data
    @AllArgsConstructor
    public class HotelInput{
        private Integer id;
        private String nombre;
        private String pais;
        private String localidad;
        private String direccion;
        private LocalDate fecha_apertura;
        private LocalDate fecha_cierre;
        private Integer num_estrellas;
        private TipoHotel tipoHotel;
        private String telefono;
        private Double latitud;
        private Double longitud;
        private LoginInput email;
        private String url_icono;
        private String url_imagen_general;
    }

    @Data
    @AllArgsConstructor
   public class Objeto_Aux_Reserva_htmlInput{
        private List<Integer> cantidadHabitaciones;
        private List<String> tipo_regimen;
        private String fechainicio;
        private String fechafin;

    }

    @Data
    @AllArgsConstructor
    public class HabitacionesInput{
       private Integer id;
       private HotelInput id_hotel;
       private TipoHab tipo_hab;
       private Integer num_hab;
       private Integer max_cliente;
       private Integer hab_ocupadas;
    }


    @Data
    @AllArgsConstructor
    public class RegimenInput{
       private Integer id;
       private HotelInput id_hotel;
       private TipoRegimen categoria;
       private Float precio;

    }

    @Data
    @AllArgsConstructor
    public class Precio_HabInput{
       private HabitacionesInput id_hab;
       private HotelInput id_hotel;
       private String  fecha_inicio;
       private String  fecha_fin;
       private Double precio;
    }




    @AllArgsConstructor
    public enum RolInput{
        ADMIN,CLIENTE,HOTEL
    }

}
