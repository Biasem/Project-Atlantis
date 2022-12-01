package com.example.atlantis;

import com.example.atlantis.controller.MainController;
import com.example.atlantis.service.BusquedaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BusquedaService busquedaService;

    @Test
    void controllerMainTest() throws Exception {
//      when....

        mockMvc.perform(get("/main").contentType(MediaType.TEXT_HTML));
    }

}
