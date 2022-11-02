package com.example.atlantis.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Reserva_Para_BBDD {
List<Integer> listIdRegimen;
List<Habitaciones> listHabitacion;
Integer idHotel;
Integer idCliente;
Integer numClientes;
LocalDate fechaEntrada;
LocalDate fechasalida;
Double precioTotal;

}
