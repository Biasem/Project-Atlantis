import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Login;
import com.example.atlantis.model.Rol;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    private static Faker faker = new Faker();
    private Rol rol;

    @Test
    public void guardadoClienteBBDDTest(){

        Login login = new Login();
        Cliente cliente = new Cliente();
        login.setEmail(faker.internet().emailAddress());
        login.setPassword("1234");
        login.setRol(rol.CLIENTE);
        cliente.setEmail(login);
        cliente.setApellidos(faker.name().lastName());
        cliente.setNombre(faker.name().name());
        cliente.setPais(faker.country().name());
        cliente.setDni(""+faker.number().numberBetween( 10000000, 99999999)+faker.);

    }
}
