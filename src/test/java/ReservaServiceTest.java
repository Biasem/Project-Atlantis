import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Reserva;
import com.example.atlantis.repository.ReservaRepository;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.HotelService;
import com.example.atlantis.service.ReservaService;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
public class ReservaServiceTest {
    private Reserva reserva;
    private Hotel hotel;
    private Cliente cliente;
    @Mock
    ReservaRepository reservaRepository;

    @InjectMocks
    ReservaService reservaService;
    @InjectMocks
    HotelService hotelService;
    @InjectMocks
    ClienteService clienteService;


    @Before
    public void setReserva(){
        hotel = hotelService.crearHotel();
        cliente = clienteService.crearCliente();
        reserva = reservaService.crearReserva(cliente,hotel);
    }
    @Test
    public void guardarReservaTest(){
        Mockito.when(reservaRepository.save(any())).thenReturn(reserva);
        Reserva reservaGuardada = reservaService.guardarReserva(reserva);
        assertNotNull(reservaGuardada);
        assertEquals(reservaGuardada,reserva);
    }


}
