package com.example.atlantis.model;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Objeto_Aux_Reserva_html {
    private List<Integer> cantidadHabitaciones;
    private List<String> tipo_regimen;
    private String fechainicio;
    private String fechafin;
}
