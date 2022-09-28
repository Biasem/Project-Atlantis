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
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;


    @JoinColumn(name = "id_hotel")
    private Integer id_hotel;


    @JoinColumn(name = "id_cliente")
    private Integer id_cliente;


    @JoinColumn(name = "id_regimen")
    private Integer id_regimen;

    @JoinColumn(name = "num_clientes")
    private Integer num_clientes;

    @JoinColumn(name = "fecha_entrada")
    private Date fecha_entrada;

    @JoinColumn(name = "fecha_salida")
    private Date fecha_salida;

    @JoinColumn(name = "precio_total")
    private Double precio_total;



}
