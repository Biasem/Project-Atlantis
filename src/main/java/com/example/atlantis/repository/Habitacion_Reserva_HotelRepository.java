package com.example.atlantis.repository;

import com.example.atlantis.model.Hab_Reserva_Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Habitacion_Reserva_HotelRepository extends JpaRepository<Hab_Reserva_Hotel,Integer> {
    //Query find all heredada, solo poner otras querys necesarias
    // @Query(value = "select id, id_reserva,id_regimen,id_hab from hab_reserva_regimen ", nativeQuery = true)
    List<Hab_Reserva_Hotel> findAll();
}
