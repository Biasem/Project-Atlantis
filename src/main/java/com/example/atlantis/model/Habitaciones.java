package com.example.atlantis.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="habitaciones")
@Getter @Setter
@EqualsAndHashCode

public class Habitaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "id_hotel")
    private Integer id_hotel;

    @JoinColumn(name = "tipo_hab")
    private Integer tipo_hab;

    @JoinColumn(name = "num_hab")
    private Integer num_hab;

    @JoinColumn(name = "hab_ocupadas")
    private Integer hab_ocupadas;

    @JoinColumn(name = "max_cliente")
    private Integer max_cliente;


}
