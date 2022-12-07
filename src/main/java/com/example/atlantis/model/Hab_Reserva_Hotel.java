package com.example.atlantis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hab_reserva_regimen")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hab_Reserva_Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "id_regimen")
    private Regimen id_regimen;

    @ManyToOne()
    @JoinColumn(name = "id_hab")
    private Habitaciones id_hab;

    @Column(name = "num_hab")
    private Integer numhab;



}
