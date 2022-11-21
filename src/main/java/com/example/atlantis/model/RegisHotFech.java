package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RegisHotFech {


    private Integer id;

    private String nombre;

    private String pais;

    private String localidad;

    private String direccion;

    private String fecha_apertura;

    private String fecha_cierre;

    private int num_estrellas;

    private TipoHotel tipo_hotel;

    private Double latitud;

    private Double longitud;

    private String telefono;

    @JoinColumn(name = "email")
    @ManyToOne(cascade = CascadeType.ALL)
    private Login email;

    private String url_icono;

    private String url_imagen_general;


}
