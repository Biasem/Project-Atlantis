import com.example.atlantis.model.Cliente;
import com.example.atlantis.model.Comentario;
import com.example.atlantis.model.Hotel;
import com.example.atlantis.repository.ComentarioRepository;
import com.example.atlantis.service.ComentarioService;
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

    @Before
    void setComentarioService(){


    }

    @Test
    public void guardarComentarioTest(){



    }
}
