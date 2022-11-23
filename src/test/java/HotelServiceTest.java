import com.example.atlantis.model.Hotel;
import com.example.atlantis.model.Login;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import com.github.javafaker.Faker;

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
    public void setCliente(){
        hotel = hotelService.crearHotel();
    }

//    @Test
//    public void guardarClienteTest(){
//        //when
//        Mockito.when(clienteRepository.save(any())).thenReturn(cliente);
//        //then
//        Cliente clienteGuardado = clienteService.guardarCliente(cliente);
//        //expected
//        assertNotNull(clienteGuardado);
//        assertEquals(clienteGuardado,cliente);
//
//
//    }

}
