import com.example.atlantis.model.Cliente;
import com.example.atlantis.repository.ClienteRepository;
import com.example.atlantis.service.ClienteService;
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
public class ClienteServiceTest {
    Cliente cliente;

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteService clienteService;

    @Before
    public void setCliente(){
        cliente = clienteService.crearCliente();
    }

    @Test
    public void guardarClienteTest(){
        //when
        Mockito.when(clienteRepository.save(any())).thenReturn(cliente);
        //then
        Cliente clienteGuardado = clienteService.guardarCliente(cliente);
        //expected
        assertNotNull(clienteGuardado);
        assertEquals(clienteGuardado,cliente);


    }




}
