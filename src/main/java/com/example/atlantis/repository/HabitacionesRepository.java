package com.example.atlantis.repository;

import com.example.atlantis.model.Habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HabitacionesRepository extends JpaRepository<Habitaciones, Integer> {

    List<Habitaciones> findAll();
    List<Habitaciones> getHabitacionesById(Integer id);

    @Query(value = " select id from habitaciones h  where id_hotel= :id_hotel  order by id desc limit 1  " , nativeQuery = true)
    Integer ultimoIdHab(@Param("id_hotel") Integer id_hotel);

}
