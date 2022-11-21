package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comentariohotel")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class ComentarioHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "comentario")
    private String sentencia;

    @JoinColumn(name = "fecha")
    private LocalDate fecha;

    @ManyToOne()
    @JoinColumn(name = "id_comentario")
    private Comentario comentario;

    @JoinColumn(name = "id_hotel")
    @ManyToOne()
    private Hotel hotel;

    @JoinColumn(name = "id_cliente")
    @ManyToOne()
    private Cliente cliente;
}
