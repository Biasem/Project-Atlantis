package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
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
    @ManyToOne(cascade = CascadeType.ALL)
    private Login email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Comentario> comentarios;

    public Cliente(String nombre, String apellidos, String dni, String pais, String telefono, Login email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.pais = pais;
        this.telefono = telefono;
        this.email = email;
    }
}
