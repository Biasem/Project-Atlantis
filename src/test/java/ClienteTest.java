import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Login;
import com.example.atlantis.model.Rol;
import com.example.atlantis.repository.ClienteRepository;
import com.example.atlantis.service.ClienteService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class ClienteTest {
    @InjectMocks
    ClienteService clienteService;
    @Mock
    ClienteRepository clienteRepository;


    private static Faker faker = new Faker();
    private Rol rol;
@Test
    public Cliente crearCliente(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Login login = new Login();
        Cliente cliente = new Cliente();
        login.setEmail(faker.internet().emailAddress());
        login.setPassword(passwordEncoder.encode("1234"));
        login.setRol(rol.CLIENTE);
        cliente.setEmail(login);
        cliente.setApellidos(faker.name().lastName());
        cliente.setNombre(faker.name().name());
        cliente.setPais(faker.country().name());
        cliente.setDni(""+faker.phoneNumber().subscriberNumber(8)+
                faker.letterify("?"));
        cliente.setTelefono(faker.phoneNumber().subscriberNumber(9));
        return cliente;
    }

    @Test
    public void guardarClienteBBDD(){


        Cliente clienteEsperado = crearCliente();

        Cliente clienteObtenido =clienteRepository.save(clienteEsperado);

        assertNotNull("No se ha guardado en BBDD", clienteObtenido);
        assertEquals("Los productos no coinciden",clienteEsperado.getDni(),clienteObtenido.getDni());
    }


}
