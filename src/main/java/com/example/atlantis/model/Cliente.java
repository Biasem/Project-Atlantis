package com.example.atlantis.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@EqualsAndHashCode
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "nombre")
    private String nombre;

    @JoinColumn(name = "apellidos")
    private String apellidos;

    @JoinColumn(name = "dni")
    private String dni;

    @JoinColumn(name = "pais")
    private String pais;

    @JoinColumn(name = "telefono")
    private String telefono;

    @JoinColumn(name = "email")
    private String email;

}
