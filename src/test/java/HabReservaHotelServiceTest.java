import com.example.atlantis.model.Hab_Reserva_Hotel;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Rol;
import com.example.atlantis.model.TipoHotel;
import com.example.atlantis.repository.Habitacion_Reserva_HotelRepository;
import com.example.atlantis.repository.HotelRepository;
import com.example.atlantis.service.Habitacion_Reserva_HotelService;
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
public class HabReservaHotelServiceTest {

    Hab_Reserva_Hotel habReservaHotel;

    @Mock
    Habitacion_Reserva_HotelRepository habitacion_reserva_hotelRepository;
    @InjectMocks
    Habitacion_Reserva_HotelService habitacionReservaHotelService;

//    @Before
//    public void setHabitacionReservaHotelService(){habitacionReservaHotelService = habitacionReservaHotelService.crear}


}
