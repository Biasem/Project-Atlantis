import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Regimen;
import com.example.atlantis.model.Reserva;
import com.example.atlantis.repository.RegimenRepository;
import com.example.atlantis.repository.ReservaRepository;
import com.example.atlantis.service.HotelService;
import com.example.atlantis.service.RegimenService;
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
public class RegimenServiceTest {

    private Regimen regimen;
    private Hotel hotel;
    @Mock
    RegimenRepository regimenRepository;

    @InjectMocks
    RegimenService regimenService;
    @InjectMocks
    HotelService hotelService;

    @Before
    public void setRegimen(){
        hotel = hotelService.crearHotel();
        regimen = regimenService.crearRegimen(hotel);
    }
    @Test
    public void guardarRegimenTest(){
        Mockito.when(regimenRepository.save(any())).thenReturn(regimen);
        Regimen regimenGuardada = regimenService.guardarRegimen(regimen);
        assertNotNull(regimenGuardada);
        assertEquals(regimenGuardada,regimen);
    }
}
