import com.example.atlantis.model.*;
import com.example.atlantis.repository.ClienteRepository;
import com.example.atlantis.service.ClienteService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


public class ClienteRepositoryTest {
    @Autowired
    ClienteService clienteService;
    @Autowired
    ClienteRepository clienteRepository;


    private static Faker faker = new Faker();
    private Rol rol;
    private TipoHotel tipoHotel;


//    public Cliente crearCliente(){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        Login login = new Login();
//        Cliente cliente = new Cliente();
//        login.setEmail(faker.internet().emailAddress());
//        login.setPassword(passwordEncoder.encode("1234"));
//        login.setRol(rol.CLIENTE);
//        cliente.setEmail(login);
//        cliente.setApellidos(faker.name().lastName());
//        cliente.setNombre(faker.name().name());
//        cliente.setPais(faker.country().name());
//        cliente.setDni(""+faker.phoneNumber().subscriberNumber(8)+
//                faker.letterify("?"));
//        cliente.setTelefono(faker.phoneNumber().subscriberNumber(9));
//        return cliente;
//    }

    @Test
    public void guardarClienteBBDD(){


        Cliente clienteEsperado = clienteService.crearCliente();

        Cliente clienteObtenido =clienteRepository.save(clienteEsperado);

        assertNotNull("No se ha guardado en BBDD", clienteObtenido);
        assertEquals("Los productos no coinciden",clienteEsperado.getDni(),clienteObtenido.getDni());
    }

//    @Test
//    public void metodoPruebaTest(){
//        System.out.println(Double.valueOf(faker.address().latitude().replace(",",".")));
//    }
    



}
