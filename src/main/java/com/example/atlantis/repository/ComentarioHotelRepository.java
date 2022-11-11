package com.example.atlantis.repository;

import com.example.atlantis.model.Comentario;
import com.example.atlantis.model.ComentarioHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioHotelRepository extends JpaRepository<ComentarioHotel, Integer> {
    List<ComentarioHotel> findAll();
}
