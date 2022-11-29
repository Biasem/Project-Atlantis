package com.example.atlantis.repository;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(value = "select id, nombre, apellidos, dni, pais, telefono, email, from cliente ", nativeQuery = true)
    List<Cliente> obtenerCliente();

    List<Cliente> findAll();

    @Modifying
    @Transactional
    @Query("update Cliente c set c.nombre = :nombre, c.apellidos = :apellidos, c.dni = :dni, c.pais = :pais," +
            "c.telefono = :telefono where c.email.email = :email")
     void editarCliente(@Param("nombre") String nombre, @Param("apellidos") String apellidos,
                                   @Param("dni") String dni, @Param("pais") String pais,
                                   @Param("telefono") String telefono, @Param("email") String email);


    @Query("select c from Cliente c where c.email.email = :email")
    Cliente buscarConSession(
            @Param("email") String email);
    };




