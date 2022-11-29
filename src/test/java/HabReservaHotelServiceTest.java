import com.example.atlantis.model.*;
import com.example.atlantis.repository.Habitacion_Reserva_HotelRepository;
import com.example.atlantis.repository.HotelRepository;
import com.example.atlantis.service.*;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class HabReservaHotelServiceTest {

    Hab_Reserva_Hotel habReservaHotel;
    Hotel hotel;
    Cliente cliente;
    Reserva reserva;
    Regimen regimen;
    Habitaciones habitaciones;
    @Mock
    Habitacion_Reserva_HotelRepository habitacion_reserva_hotelRepository;
    @InjectMocks
    Habitacion_Reserva_HotelService habitacionReservaHotelService;
    @InjectMocks
    RegimenService regimenService;
    @InjectMocks
    ReservaService reservaService;
    @InjectMocks
    HotelService hotelService;
    @InjectMocks
    ClienteService clienteService;
    @InjectMocks
    HabitacionesService habitacionesService;

    @Before
    public void setHabitacionReservaHotelService(){
        hotel = hotelService.crearHotel();
        cliente = clienteService.crearCliente();
        reserva = reservaService.crearReserva(cliente,hotel);
        regimen = regimenService.crearRegimen(hotel);
        habitaciones = habitacionesService.crearHabitacion(hotel);
        habReservaHotel = habitacionReservaHotelService.crearHab_Reserva_Hotel(hotel,habitaciones,reserva,regimen);
    }
    @Test
    public void guardarHabitacionReservaHotelTest(){
        Mockito.when(habitacion_reserva_hotelRepository.save(any())).thenReturn(habReservaHotel);
        Hab_Reserva_Hotel habReservaHotelGuardada = habitacionReservaHotelService.guardarHabReservaHotel(habReservaHotel);
        assertNotNull(habReservaHotelGuardada);
        assertEquals(habReservaHotelGuardada,habReservaHotel);
    }

}
