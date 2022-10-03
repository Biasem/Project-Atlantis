package com.example.atlantis.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Integer id;

    @JoinColumn(name = "id_hotel")
    @OneToOne
    private Hotel id_hotel;

    @JoinColumn(name = "id_cliente")
    @ManyToOne
    private Cliente id_cliente;


    @JoinColumn(name = "id_regimen")
    @ManyToOne
    private Regimen id_regimen;

    @JoinColumn(name = "num_clientes")
    private Integer num_clientes;

    @JoinColumn(name = "fecha_entrada")
    private Date fecha_entrada;

    @JoinColumn(name = "fecha_salida")
    private Date fecha_salida;

    @JoinColumn(name = "precio_total")
    private Double precio_total;

}
