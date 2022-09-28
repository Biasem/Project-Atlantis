package com.example.atlantis.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "precio_hab")
@Getter @Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Precio_Hab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "id_hab")
    private Habitaciones id_hab;


    @ManyToOne
    @JoinColumn(name = "id_hotel")
    private Hotel id_hotel;

    @Column(name = "fecha_inicio")
    private Date fecha_inicio;

    @Column(name = "fecha_fin")
    private Date fecha_fin;

    @Column(name = "precio")
    private Double precio;


}