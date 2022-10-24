package com.example.atlantis.model;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Objeto_Aux_Reserva {
    private List<Integer> num;
    private List<String> id_regimen;
    private String fechainicio;
    private String fechafin;
}
