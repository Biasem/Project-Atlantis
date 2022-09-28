package com.example.atlantis.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Regimen")
@Getter @Setter
@EqualsAndHashCode
public class Regimen {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "id_hotel")
    private Integer id_hotel;

    @JoinColumn(name = "categoria")
    private Integer categoria;

    @JoinColumn(name = "precio")
    private Double precio;


}
