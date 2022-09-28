package com.example.atlantis.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "hotel")
@Getter
@Setter
@EqualsAndHashCode
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "nombre")
    private String nombre;

    @JoinColumn(name = "pais")
    private String pais;

    @JoinColumn(name = "locaclidad")
    private String localidad;

    @JoinColumn(name = "direccion")
    private String direccion;

    @JoinColumn(name = "fecha_apertura")
    private Date fecha_apertura;

    @JoinColumn(name = "fecha_cierre")
    private Date fecha_cierre;

    @JoinColumn(name = "num_estrellas")
    private int num_estrella;

    @JoinColumn(name = "tipo_hotel")
    private Integer tipo_hotel;

    @JoinColumn(name = "telefono")
    private String telefono;

    @JoinColumn(name = "email")
    private String email;

}
