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


    @Column(name = "id_hab")
    private Integer id_hab;


    @Column(name = "id_hotel")
    private Integer id_hotel;

    @Column(name = "fecha_inicio")
    private Date fecha_inicio;

    @Column(name = "fecha_fin")
    private Date fecha_fin;

    @Column(name = "precio")
    private Double precio;


}
