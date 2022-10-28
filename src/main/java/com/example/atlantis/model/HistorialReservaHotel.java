package com.example.atlantis.model;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class HistorialReservaHotel {

    private Integer id;

    private String nombreCliente;

    private String emailCliente;

    private Integer num_clientes;

    private TipoRegimen categoria;

    private LocalDate fecha_entrada;

    private LocalDate fecha_salida;

    private Double precio_total;


}



