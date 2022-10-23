package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comentario")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "comentario")
    private String sentencia;

    @JoinColumn(name = "fecha")
    private LocalDate fecha;

    @JoinColumn(name = "puntuacion")
    private Integer puntuacion;

    @JoinColumn(name = "id_cliente")
    @ManyToOne(cascade = CascadeType.ALL)
    private Cliente id_cliente;

    @JoinColumn(name = "id_hotel")
    @ManyToOne(cascade = CascadeType.ALL)
    private Hotel id_hotel;


}
