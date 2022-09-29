package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="habitaciones")
@Getter @Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class Habitaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "id_hotel")
    @ManyToOne
    private Hotel id_hotel;

    @JoinColumn(name = "tipo_hab")
    private Integer tipo_hab;

    @JoinColumn(name = "num_hab")
    private Integer num_hab;

    @JoinColumn(name = "hab_ocupadas")
    private Integer hab_ocupadas;

    @JoinColumn(name = "max_cliente")
    private Integer max_cliente;


}