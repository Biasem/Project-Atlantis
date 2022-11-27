import com.example.atlantis.repository.HotelRepository;
import com.example.atlantis.service.HotelService;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
public class ReservaServiceTest {
    @Mock
    HotelRepository hotelRepository;

    @InjectMocks
    HotelService hotelService;

    @Before
    public void setReserva(){
        hotel = hotelService.crearHotel();
    }


}
