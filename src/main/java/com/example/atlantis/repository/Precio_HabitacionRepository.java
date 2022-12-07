package com.example.atlantis.repository;

import com.example.atlantis.model.Precio_Hab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Precio_HabitacionRepository extends JpaRepository<Precio_Hab,Integer> {
//Query find all heredada, solo poner otras querys necesarias
   // @Query(value = "select id, id_hab,id_hotel,fecha_inicio,fecha_fin,precio from precio_hab ", nativeQuery = true)
    List<Precio_Hab> findAll();

    @Query(value = " select * from precio_hab ph  where id_hab= :id_hab " , nativeQuery = true)
    List<Precio_Hab> obtenerListPreciohab(@Param("id_hab") Integer id_hab);

}
