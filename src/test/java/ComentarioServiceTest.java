import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Comentario;
import com.example.atlantis.model.Habitaciones;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.repository.ComentarioRepository;
import com.example.atlantis.service.ClienteService;
import com.example.atlantis.service.ComentarioService;
import com.example.atlantis.service.HotelService;
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
public class ComentarioServiceTest {
    Comentario comentario;
    Hotel hotel;
    Cliente cliente;

    @Mock
    ComentarioRepository comentarioRepository;
    @InjectMocks
    ComentarioService comentarioService;
    @InjectMocks
    ClienteService clienteService;
    @InjectMocks
    HotelService hotelService;

    @Before
    public void setComentarioService(){
    cliente = clienteService.crearCliente();
    hotel = hotelService.crearHotel();
    comentario = comentarioService.crearComentario(hotel,cliente);

    }

    @Test
    public void guardarComentarioTest(){
        Mockito.when(comentarioRepository.save(any())).thenReturn(comentario);
        Comentario comentarioGuardada = comentarioService.guardarComentario(comentario);
        assertNotNull(comentarioGuardada);
        assertEquals(comentarioGuardada,comentario);
    }
}
