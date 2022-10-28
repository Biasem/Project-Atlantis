package com.example.atlantis.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

public class HistorialReservaClientes {

    private Integer id;

    private String nombreHotel;

    private Integer num_clientes;

    private TipoRegimen categoria;

    private LocalDate fecha_entrada;

    private LocalDate fecha_salida;

    private Double precio_total;


}
