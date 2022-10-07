package com.example.atlantis.repository;

import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoginRepository extends JpaRepository<Login, Integer> {

    @Query(value = "select email, password from login ", nativeQuery = true)
    List<Cliente> obtenerCliente();


}
