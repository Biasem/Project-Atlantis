package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Regimen")
@Getter @Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Regimen {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "id_hotel")
    @OneToOne
    private Hotel id_hotel;

    @JoinColumn(name = "categoria")
    private Integer categoria;

    @JoinColumn(name = "precio")
    private Double precio;


}
