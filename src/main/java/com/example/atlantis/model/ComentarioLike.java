package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comentariolike")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class ComentarioLike{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "puntuacion")
    private Integer puntuacion;

    @ManyToOne()
    @JoinColumn(name = "id_cliente")
    private Cliente id_cliente;

    @JoinColumn(name = "id_comentario")
    @ManyToOne()
    private Comentario id_comentario;

    @JoinColumn(name = "id_hotel")
    @ManyToOne()
    private Hotel id_hotel;

}
