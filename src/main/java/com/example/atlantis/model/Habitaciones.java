package com.example.atlantis.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne()
    @JoinColumn(name = "id_hotel")
    private Hotel id_hotel;

    @JoinColumn(name = "tipo_hab")
    private TipoHab tipo_hab;

    @JoinColumn(name = "num_hab")
    private Integer num_hab;

    @JoinColumn(name = "hab_ocupadas")
    private Integer hab_ocupadas;

    @JoinColumn(name = "max_cliente")
    private Integer max_cliente;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id_hab")
    private List<Precio_Hab> precio_hab;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id_hab")
    private List<Hab_Reserva_Hotel> hab_reserva_hotel;


}
