import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Rol;
import com.example.atlantis.model.TipoHotel;
import com.example.atlantis.repository.HotelRepository;
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
public class HotelServiceTest {
    private static Faker faker = new Faker();
    private Rol rol;
    private TipoHotel tipoHotel;
    Hotel hotel;

    @Mock
    HotelRepository hotelRepository;

    @InjectMocks
    HotelService hotelService;

    @Before
    public void setHotel(){
        hotel = hotelService.crearHotel();
    }

    @Test
    public void guardarHotelTest(){
        //when
        Mockito.when(hotelRepository.save(any())).thenReturn(hotel);
        //then
        Hotel hotelGuardado = hotelService.guardarHotel(hotel);
        //expected
        assertNotNull(hotelGuardado);
        assertEquals(hotelGuardado,hotel);
    }

}
