package com.example.atlantis;

import com.example.atlantis.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AtlantisApplicationTests {
    @Autowired
    private ClienteService clienteService;

    @Test
    void contextLoads() {
        System.out.println(clienteService.crearCliente().getNombre());
    }

}
