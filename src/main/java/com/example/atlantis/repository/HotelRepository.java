package com.example.atlantis.repository;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.TipoHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findAll();



    @Modifying
    @Transactional
    @Query("update Hotel h set h.nombre = :nombre, h.pais = :pais, h.localidad = :localidad, h.direccion = :direccion," +
            "h.fecha_apertura = :fecha_apertura, h.fecha_cierre = :fecha_cierre, h.num_estrellas = :num_estrellas," +
            "h.tipo_hotel = :tipo_hotel, h.telefono = :telefono, h.url_icono = :url_icono, " +
            "h.url_imagen_general = :url_imagen_general where h.email.email = :email")
    void editarHotel(@Param("nombre") String nombre, @Param("pais") String pais,
                     @Param("localidad") String localidad, @Param("direccion") String direccion,
                     @Param("fecha_apertura") LocalDate fecha_apertura, @Param("fecha_cierre") LocalDate fecha_cierre,
                     @Param("num_estrellas") int num_estrellas, @Param("tipo_hotel") TipoHotel tipo_hotel, @Param("telefono") String telefono,
                     @Param("url_icono") String url_icono, @Param("url_imagen_general") String url_imagen_general,
                     @Param("email") String email);

}