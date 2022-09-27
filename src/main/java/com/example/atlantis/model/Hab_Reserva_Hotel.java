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


    @Column(name = "id_reserva")
    private Integer id_reserva;


    @Column(name = "id_regimen")
    private Integer id_regimen;


    @Column(name = "id_hab")
    private Integer id_hab;



}
