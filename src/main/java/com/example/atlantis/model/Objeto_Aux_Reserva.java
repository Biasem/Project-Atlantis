package com.example.atlantis.model;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Objeto_Aux_Reserva {
    private List<Integer> num;
    private List<String> id_regimen;
}
