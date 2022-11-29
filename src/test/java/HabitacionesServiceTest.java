import com.example.atlantis.model.Habitaciones;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.repository.Habitacion_Reserva_HotelRepository;
import com.example.atlantis.repository.HabitacionesRepository;
import com.example.atlantis.service.Habitacion_Reserva_HotelService;
import com.example.atlantis.service.HabitacionesService;
import com.example.atlantis.service.HotelService;
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
public class HabitacionesServiceTest {
    Habitaciones habitaciones;
    Hotel hotel;

    @Mock
    HabitacionesRepository habitacionesRepository;
    @InjectMocks
    HabitacionesService habitacionesService;
    @InjectMocks
    HotelService hotelService;

    @Before
    public void setHabitacionReservaHotelService(){
        hotel = hotelService.crearHotel();
        habitaciones = habitacionesService.crearHabitacion(hotel);
    }
    @Test
    public void guardarHabitacionReservaHotelTest(){
        Mockito.when(habitacionesRepository.save(any())).thenReturn(habitaciones);
        Habitaciones habitacionGuardada = habitacionesService.guardarHabitacion(habitaciones);
        assertNotNull(habReservaHotelGuardada);
        assertEquals(habReservaHotelGuardada,habReservaHotel);
    }
}
