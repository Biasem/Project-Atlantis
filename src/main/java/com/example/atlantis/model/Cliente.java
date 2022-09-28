package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;

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
    @ManyToOne
    private Login email;

}
