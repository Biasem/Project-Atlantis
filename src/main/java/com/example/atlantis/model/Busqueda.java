package com.example.atlantis.model;

import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Busqueda {

    private String hotelBuscar;
    private Date fechaInicial;
    private Date fechaFinal;
    private Integer numHuespedes;

}
