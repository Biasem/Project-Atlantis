package com.example.atlantis;
import com.example.atlantis.model.Busqueda;
import com.example.atlantis.model.Habitaciones;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.TipoHab;
import com.example.atlantis.service.BusquedaService;
import com.example.atlantis.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BusquedaServiceTest {

    @Autowired
    ClienteService clienteService;
    @Autowired
    BusquedaService busquedaService;

    @Test
    void busquedaTest() {
        List<Hotel> resultadoHotelesbuscados = new ArrayList<>();
        List<Hotel> hotelesABuscar = new ArrayList<>();
///////////////////////////primer hotel
        Habitaciones habitacion1 = new Habitaciones();
        habitacion1.setNum_hab(80);
        habitacion1.setHab_ocupadas(10);
        habitacion1.setMax_cliente(3);
        habitacion1.setTipo_hab(TipoHab.SIMPLE);

        Hotel hotel1 = new Hotel();
        hotel1.setNombre("Gran Hotel Sevilla");
        hotel1.setLocalidad("Madrid");
        hotel1.setFecha_apertura(LocalDate.of(2020,1,1));
        hotel1.setFecha_cierre(LocalDate.of(2025,1,1));
        List<Habitaciones> hotel1hab = new ArrayList<>();
        hotel1hab.add(habitacion1);
        hotel1.setHabitaciones(hotel1hab);
/////////////////////segundo hotel
        Habitaciones habitacion2 = new Habitaciones();
        habitacion2.setNum_hab(80);
        habitacion2.setHab_ocupadas(80);
        habitacion2.setMax_cliente(3);
        habitacion2.setTipo_hab(TipoHab.SIMPLE);

        Hotel hotel2 = new Hotel();
        hotel2.setNombre("Hotel El Descanso del Viajero");
        hotel2.setLocalidad("Sevilla");
        hotel2.setFecha_apertura(LocalDate.of(2020,1,1));
        hotel2.setFecha_cierre(LocalDate.of(2025,1,1));
        List<Habitaciones> hotel2hab = new ArrayList<>();
        hotel2hab.add(habitacion2);
        hotel2.setHabitaciones(hotel2hab);
///////////////////////////////////////
        hotelesABuscar.add(hotel1);
        hotelesABuscar.add(hotel2);
        ///////////////////////////////////////
        resultadoHotelesbuscados.add(hotel1);
        //////////////////////////busqueda
        Busqueda busqueda = new Busqueda();
        busqueda.setHotelBuscar("sevilla");
        busqueda.setFechaInicial("2022-01-01");
        busqueda.setFechaFinal("2022-01-03");
        busqueda.setNumHuespedes(3);

        //////////////////////////////asserts
        List<Hotel> resultadoBusqueda = busquedaService.AccionBuscar(busqueda,hotelesABuscar);
        System.out.println(resultadoBusqueda.size());
        assertNotNull(resultadoBusqueda);
        assertEquals(resultadoHotelesbuscados,resultadoBusqueda);
    }

}
