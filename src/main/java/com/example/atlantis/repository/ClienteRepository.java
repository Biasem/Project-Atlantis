package com.example.atlantis.repository;

import com.example.atlantis.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(value = "select id, nombre, apellidos, dni, pais, telefono, email, from cliente ", nativeQuery = true)
    List<Cliente> obtenerCliente();
}