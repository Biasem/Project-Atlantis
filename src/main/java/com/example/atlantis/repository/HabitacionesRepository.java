package com.example.atlantis.repository;

import com.example.atlantis.model.Habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HabitacionesRepository extends JpaRepository<Habitaciones, Integer> {

    List<Habitaciones> findAll();
    List<Habitaciones> getHabitacionesById(Integer id);

    //@Query(name = "SELECT * FROM habitaciones h2 INNER JOIN hotel h ON h.id=h2.id_hotel",nativeQuery = true);

}
