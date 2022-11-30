package com.example.atlantis.controller;



import com.example.atlantis.model.*;
import com.example.atlantis.repository.*;
import com.example.atlantis.service.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Habitacion_Reserva_HotelService habitacionReservaHotelService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    HabitacionesRepository habitacionesRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    HotelService hotelService;

    @Autowired
    LoginService loginService;

    @Autowired
    private RegimenService regimenService;

    @Autowired
    private Habitacion_Reserva_HotelService habitacion_reserva_hotelService;
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private BusquedaService busquedaService;

    @Autowired
    private ComentarioLikeRepository comentarioLikeRepository;

    @Autowired
    private ComentarioHotelRepository comentarioHotelRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HabitacionesService habitacionesService;

    protected static Reserva_Para_BBDD reserva_para_bbdd = null;

    @Autowired
    private BuscadorIDService buscadorService;

    @Autowired
    private Precio_HabitacionService precio_habitacionService;



    @PostMapping("/register")
    @MutationMapping
    public String registerForm(@RequestBody @Argument(name = "input") GraphqlInput.ClienteInput input) {

        try {
            //Selección de Rol Cliente para el nuevo cliente
            input.getEmail().setRol(GraphqlInput.RolInput.CLIENTE);
            input.getEmail().setPassword(bCryptPasswordEncoder.encode(input.getEmail().getPassword()));

            Cliente cliente = new Cliente();
            cliente.setApellidos(input.getApellidos());
            cliente.setNombre(input.getNombre());
            cliente.setDni(input.getDni());
            cliente.setPais(input.getPais());
            cliente.setTelefono(input.getTelefono());
            Login login = new Login();
            cliente.setEmail(login);
            cliente.getEmail().setEmail(input.getEmail().getEmail());
            cliente.getEmail().setPassword(input.getEmail().getPassword());
            cliente.getEmail().setRol(Rol.CLIENTE);


            //Guardado del cliente en base de datos
            clienteService.guardarCliente(cliente);
            System.out.println(input);

            return "Datos guardados correctamente";


        } catch (Exception e) {
            return "Ha habido un problema al guardar cliente";
        }
    }


    @PostMapping("/registerhotel")
    @MutationMapping
    public String registerhotelForm(@RequestBody @Argument(name = "input") GraphqlInput.RegisHotFechInput input) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        try {
            //Primer if para que tenga los datos que sean obligatorios y las fechas no sean raras
            if (input.getNombre() != null && input.getDireccion() != null && input.getPais() != null
                    && input.getLocalidad() != null && input.getFecha_apertura() != null
                    && input.getFecha_cierre() != null && input.getTipo_hotel() != null
//                && LocalDate.parse(input.getFecha_cierre()).isAfter(LocalDate.parse(input.getFecha_apertura()))
                    && input.getEmail() != null && input.getEmail().getPassword() != null && input.getLatitud() != null
                    && input.getLongitud() != null) {

                //If para mirar si el Hotel es apartamento y tenga las estrellas a 0
                if (hotelService.siEsApartaHotelApi(input) != true) {
                    input.setNum_estrellas(0);
                }

                //Funcion que guarda hotel
                hotelService.guardarHotel(hotelService.convertirAHotelApi(input));
                System.out.println(hotelService.convertirAHotelApi(input));

                return "Datos guardados correctamente";
            } else {

                return "Ha habido un problema al guardar cliente";
            }
        } catch (Exception e) {
            return "Ha habido un problema al guardar cliente";
        }

    }


    @PostMapping("/deletecliente")
    @MutationMapping

    public String deleteCliente2(@RequestBody @Argument(name = "input") GraphqlInput.ClienteInput input) {
        Cliente cliente2 = new Cliente();
        //Encriptado y recogida de datos de al sesión apra copiar todo el modelo
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (input.getEmail().getEmail() != null) {
            cliente2 = clienteService.copiartodoclienteApi(input);

        } else {
            //Encriptado y recogida de datos de al sesión para copiar todo el modelo
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();

            cliente2 = loginService.copiartodoclienteconsession(correo);
        }

        //If para saber si los datos estan correctos y pueden borrar o no
        if (cliente2.getEmail().getEmail().equals(input.getEmail().getEmail()) && encoder.matches(input.getEmail().getPassword(), cliente2.getEmail().getPassword())) {
            clienteService.borrarClienteApi(input);

            return "Cliente borrado con éxito";
        } else {
            return "Fallo al borrar cliente";
        }
    }


    @PostMapping("/deletehotel")
    @MutationMapping
    public String deleteHotel2(@RequestBody @Argument(name = "input") GraphqlInput.HotelInput hotel) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();


        Hotel hotel1 = hotelService.copiartodohotelApi(hotel);

        if (hotel1.getEmail().getEmail().equals(hotel.getEmail().getEmail()) && encoder.matches(hotel.getEmail().getPassword(), hotel1.getEmail().getPassword())) {
            List<ComentarioLike> comentarioLike = comentarioLikeRepository.findAll().stream().filter(x -> x.getId_hotel().getId().equals(hotel1.getId())).collect(Collectors.toList());
            List<ComentarioHotel> comentarioHotels = comentarioHotelRepository.findAll().stream().filter(x -> x.getHotel().getId().equals(hotel1.getId())).collect(Collectors.toList());
            List<Comentario> comentarios = comentarioRepository.findAll().stream().filter(x -> x.getHotel().getId().equals(hotel1.getId())).collect(Collectors.toList());
            comentarioLike.stream().forEach(x -> comentarioLikeRepository.delete(x));
            comentarioHotels.stream().forEach(x -> comentarioHotelRepository.delete(x));
            comentarios.stream().forEach(x -> comentarioRepository.delete(x));
            hotelService.borrarHotel(hotel1);
            return "Hotel borrado con éxito";
        } else {
            return "Hotel no pudo ser borrado";
        }

    }

    @PostMapping("/editcliente")
    @SchemaMapping(typeName = "Mutation", value = "editarCliente2")
    public String editarCliente2(@RequestBody @Argument(name = "input") GraphqlInput.ClienteInput input) {
        //If para verificar que los datos introducidos sean tal cual se necesite
        if (input.getNombre() != null && input.getApellidos() != null
                && clienteService.validarDNI(input.getDni()) != false) {

            if (input.getEmail().getEmail() != null) {
                input.setId(null);
                input.getEmail().setRol(GraphqlInput.RolInput.CLIENTE);

            } else {

                //Recogida datos de sesión e insertarlos en el modelo
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String correo = auth.getName();
                Cliente datos = loginService.cogeridcliente(correo);
                input.setId(datos.getId());
                //               input.setEmail(new Login());
                input.getEmail().setPassword(datos.getEmail().getPassword());
                input.getEmail().setRol(GraphqlInput.RolInput.CLIENTE);
                input.getEmail().setEmail(datos.getEmail().getEmail());
            }


            //Introduccion de datos a Service para meter en ddbb
            clienteService.editarClienteApi(input);
            return "Cliente editado con éxito";

        } else {

            return "El cliente no se pudo editar";
        }
    }


    @PostMapping("/edithotel")
    @SchemaMapping(typeName = "Mutation", value = "editarHotel2")
    public String editarhotel2(@RequestBody @Argument(name = "input") GraphqlInput.RegisHotFechInput input) {
        //Primer if para que tenga los datos que sean obligatorios y las fechas no sean raras
        if (input.getNombre() != null && input.getDireccion() != null && input.getPais() != null
                && input.getLocalidad() != null && input.getFecha_apertura() != null
                && input.getFecha_cierre() != null && input.getTipo_hotel() != null
//                && LocalDate.parse(input.getFecha_cierre()).isAfter(LocalDate.parse(input.getFecha_apertura()))
        ) {


            if (input.getEmail().getEmail() != null) {
                input.setId(null);
                input.getEmail().setRol(GraphqlInput.RolInput.HOTEL);
            } else {
                //Recogida de datos con sesión y copia del modelo entero
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String correo = auth.getName();
                Hotel datos = loginService.cogerid(correo);


                input.setId(datos.getId());
//            input.setEmail(new GraphqlInput.LoginInput());
                input.getEmail().setPassword(datos.getEmail().getPassword());
                input.getEmail().setRol(GraphqlInput.RolInput.HOTEL);
                input.getEmail().setEmail(datos.getEmail().getEmail());
            }
            //Método para meter el hotel ya convertido en el modelo para ddbb
            hotelService.editarHotel(hotelService.convertirAHotelApi(input));
            return "Hotel editado con éxito";
        } else {

            return "El hotel no se pudo editar";
        }
    }


    @GetMapping("/irMain")
    @MutationMapping
    public String irAMain(@PathVariable @Argument(name = "busqueda") GraphqlInput.BusquedaInput input) {
        return "redirect:/main";
    }

    @RequestMapping("/perfcliente")
    @SchemaMapping(typeName = "Query", value = "perfil")
    public Cliente perfil(@Argument(name = "correo") String correo) {
        ModelAndView model = new ModelAndView("perfilCliente");
        // Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo1 = auth.getName();
        if (correo != null) {
            correo = correo;
        } else {
            correo = correo1;
        }

        Integer idCliente = 0;
        Integer idHotel = 0;
        if (correo != null) {
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
            System.out.println(idCliente);
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        // Gestión sesión
        Cliente cliente = clienteService.getById(idCliente);
        List<Comentario> comentarios = new ArrayList<>();
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        model.addObject("cliente", cliente);
        model.addObject("correo", correo);
        model.addObject("comentarios", comentarioService.conseguirComentariosCliente(idCliente));
        return cliente;
    }

    @RequestMapping("/perfilhotel")
    @SchemaMapping(typeName = "Query", value = "perfilHotel")
    public Hotel perfilhotel(@Argument(name = "correo") String correo) {
        ModelAndView model = new ModelAndView("perfilHotel");
        // Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo1 = auth.getName();
        if (correo != null) {
            correo = correo;
        } else {
            correo = correo1;
        }

        Integer idCliente = 0;
        Integer idHotel = 0;
        if (correo != null) {
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
            System.out.println(idCliente);
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        // Gestión sesión

        Hotel hotel = hotelService.getById(idHotel);
        List<Comentario> comentarios = new ArrayList<>();
        model.addObject("hotel", hotel);
        model.addObject("correo", correo);
        model.addObject("comentarios", comentarioService.conseguirComentariosHotel(idHotel));
        model.addObject("media", comentarioService.mediaPuntuacion(idHotel));
        model.addObject("estrellas", hotel.getNum_estrellas());
        return hotel;
    }


    @GetMapping("/historialReservaHotelVigentess")
    @SchemaMapping(typeName = "Query", value = "historialVigenteHotel")
    public List<HistorialReservaHotel> historialVigenteHotel(@RequestBody @Argument(name = "hotel") GraphqlInput.HotelInput hotel) {

        Hotel hotel1 = new Hotel();
        ModelAndView model = new ModelAndView("historialReservaHotel");
        if (hotel.getEmail().getEmail() != null) {
            hotel1 = hotelService.copiartodohotelApi(hotel);

        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();
            hotel1 = loginService.cogerid(correo);
        }
        //Obtención de listas para metodo
        List<Reserva> todas = reservaService.todasReservasHotel(hotel1.getId());
        List<Hab_Reserva_Hotel> todasReservaporHab = habitacion_reserva_hotelService.getAll();
        List<Regimen> todosregimen = regimenService.getAll();
        //Lista que se devuelve con método de búsqueda
        List<HistorialReservaHotel> todasmodelohistorial = reservaService.cambiomodelohistorialhotelvigente(todas, todasReservaporHab, todosregimen);
        model.addObject("todas", todasmodelohistorial);

        return todasmodelohistorial;

    }


    @GetMapping("/historialReservaClienteVigentess")
    @SchemaMapping(typeName = "Query", value = "historialVigente")
    public List<HistorialReservaClientes> historialVigente(@RequestBody @Argument(name = "cliente") GraphqlInput.ClienteInput cliente) {
        Cliente cliente1 = new Cliente();
        if (cliente.getEmail().getEmail() != null) {
            cliente1 = clienteService.copiartodoclienteApi(cliente);
        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String correo = auth.getName();
            cliente1 = loginService.copiartodoclienteconsession(correo);
        }
        //Obtención de listas para metodo
        List<Reserva> todas = reservaService.todasReservas(cliente1.getId());
        List<Hab_Reserva_Hotel> todasReservaporHab = habitacion_reserva_hotelService.getAll();
        List<Regimen> todosregimen = regimenService.getAll();
        //Lista que se devuelve con método de búsqueda
        List<HistorialReservaClientes> todasmodelohistorial = reservaService.cambiomodelohistorialvigente(todas, todasReservaporHab, todosregimen);

        return todasmodelohistorial;

    }

    @RequestMapping(value = "/hoteless/{item}", method = RequestMethod.GET)
    @SchemaMapping(typeName = "Query", value = "resultadoHotel")
    public @ResponseBody Hotel resultadoHotel(@RequestParam(value = "id") @PathVariable @Argument(name = "id") Integer id) {

        // Gestión sesión

        List<Hotel> listaHoteles = hotelService.getAll();
        List<Hotel> hotelfinal = new ArrayList<>();

          BuscadorID numero = new BuscadorID(id);
        Hotel definitivo = buscadorService.Comparar(numero, listaHoteles);
        hotelfinal.add(definitivo);
        return definitivo;
    }


    @PostMapping("/reservarr")
    @SchemaMapping(typeName = "Mutation", value = "reservarHab")
    public String confirmarReserva(@Argument(name = "reserva") GraphqlInput.Reserva_Input reservainput,
                                   @Argument(name = "habreser") GraphqlInput.Hab_Reserva_HotelInput habres){


        Reserva reserva = new Reserva();
        //Formateo de fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechap =  LocalDate.parse(reservainput.getFecha_entrada(), formatter);
        LocalDate fechap1 =  LocalDate.parse(reservainput.getFecha_salida(), formatter);

        Integer idhotel = reservainput.getId_hotel().getId();
        Integer idcliente = reservainput.getId_cliente().getId();

        reserva.setId(0);
        Hotel hotel =  hotelService.getById(idhotel);
        reserva.setId_hotel(hotel);

        Cliente cliente = clienteService.getById(idcliente);
        reserva.setId_cliente(cliente);
        reserva.getId_cliente().setId(idcliente);
        reserva.setFecha_entrada(fechap);
        reserva.setFecha_salida(fechap1);
        reserva.setPrecio_total(reservainput.getPrecio_total());
        reservaService.guardarReserva(reserva);

        //guardamos los detalles de la reserva sacando el id de la reserva del cliente creada anteriormente

            Habitaciones habitaciones = habitacionesService.getById(habres.getId_habitaciones().getId());
            Regimen regimen = regimenService.getById(habres.getId_regimen().getId());
            Hab_Reserva_Hotel habReservaHotel = new Hab_Reserva_Hotel();
            habReservaHotel.setId_hab(habitaciones);
            habReservaHotel.setId_regimen(regimen);
            Reserva ultireserva = reservaService.obtenerUltima();
            habReservaHotel.setReserva(ultireserva);
            habReservaHotel.setNumhab(habres.getNumhab());
            habitacionReservaHotelService.guardarHabReservaHotel(habReservaHotel);

            Habitaciones hab = habitacionesService.getById(habres.getId_habitaciones().getId());
            hab.setHab_ocupadas(hab.getHab_ocupadas()+habres.getNumhab());
            habitacionesRepository.save(hab);

        return "Reserva concretada";
    }




    @GetMapping("/listaHotelAzar")
    @SchemaMapping(typeName = "Query", value = "listaHotelAzar")
    public List<Hotel> listaHotel(@Argument(name = "busqueda") GraphqlInput.BusquedaInput input) {

        // Gestión sesión
        List<Hotel> listaprimera = hotelService.getAll();
        Collections.shuffle(listaprimera);
        List<Hotel> listaHotel = listaprimera.subList(0, 3);
        return listaHotel;
    }


    @PostMapping("/listaHotelBusqueda")
    @SchemaMapping(typeName = "Query", value = "listaHotelBusqueda")
    public List<Hotel> listaHoteles(@Argument(name = "busqueda") GraphqlInput.BusquedaInput busqueda, @Argument(name = "correo") String correo) {

        // Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo1 = auth.getName();
        if (correo != null) {
            correo = correo;
        } else {
            correo = correo1;
        }

        Integer idCliente = 0;
        Integer idHotel = 0;
        if (correo != null){
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
        }
        // Gestión sesión
        List<Hotel> listaHoteles = hotelService.getAll();
        List<Hotel> filtro = busquedaService.AccionBuscarApi(busqueda,listaHoteles);
        return filtro;

    }


    @PostMapping("/CrearHabitacion")
    @SchemaMapping(typeName = "Mutation", value = "crearHabitacion")
    public String habitaciones(@Argument(name = "habitaciones") GraphqlInput.HabitacionesInput habitaciones,
                               @Argument(name = "id") Integer idhotel) {

        habitaciones.setHab_ocupadas(0);
        Habitaciones habitaciones1 = new Habitaciones();
        Hotel nuevo = new Hotel();
        //Seteo en del input en modelo Habitacion y guardado
        habitaciones1.setHab_ocupadas(habitaciones.getHab_ocupadas());
        habitaciones1.setNum_hab(habitaciones.getNum_hab());
        habitaciones1.setTipo_hab(habitaciones.getTipo_hab());
        habitaciones1.setId_hotel(nuevo);
        habitaciones1.getId_hotel().setId(idhotel);
        habitaciones1.setMax_cliente(habitaciones.getMax_cliente());
        habitacionesService.guardarHabitacion(habitaciones1);

        return "Guardado correctamente";
    }


    @RequestMapping("/admin/habitacioness/editar/hecho/{item}")
    @SchemaMapping(typeName = "Mutation", value = "editarHabitacion")
    public @ResponseBody String editarHabitacionhecho(@Argument(name = "id") Integer id,
                                                      @Argument(name = "habitaciones") GraphqlInput.HabitacionesInput habitaciones) {
        habitacionesService.editarHabitacionApi(id, habitaciones);
        return "Editado correctamente";
    }


    @RequestMapping("/admin/habitacioness/borrar/{item}")
    @SchemaMapping(typeName = "Mutation", value = "borrarHabitacion")
    public @ResponseBody String borrarHabitacion(@Argument(value = "id") Integer id) {
        //Obtiene todas las habitaciones, consigue la habitación con el id dado y borra de la lista la habitacion
        List<Habitaciones> habitaciones = habitacionesService.getAll();
        Habitaciones habitacion = new Habitaciones();
        habitacion = habitacionesService.conseguirHabitacion(id, habitaciones);
        List<Precio_Hab> precios = precio_habitacionService.getAll();
        precio_habitacionService.borrarLista(precios, habitacion);
        habitacionesService.borrarHabitacion(id);
        return "Borrado correctamente";
    }

    @RequestMapping(value = "/admin/habitacioness/crear/regimen/hecho/")
    @SchemaMapping(typeName = "Mutation", value = "crearRegimen")
    public String crearRegimen(@Argument(name = "regimen") GraphqlInput.RegimenInput regimen,
                                     @Argument("idhotel") Integer idhotel,
                                     @Argument("categoria") TipoRegimen categoria,
                                     @Argument("precio") Double precio) {

        List<Regimen> regimenes = regimenService.getAll();
        regimen.setId(idhotel);
        boolean bool = true;
        //Mira si la categoría del regimen y el id_hotel son iguales entre ambos en el bucle, devolviendo false en boolean
        for(Regimen r : regimenes) {
            if (r.getCategoria().equals(regimen.getCategoria()) && r.getId_hotel().getId().equals(regimen.getId())) {
                bool = false;
                break;
            }
        }
        //Si el boolean es true crea nuevo regimen, consigue la lista de hoteles con el idhotel del regimen y guarda el regimen
        if(bool !=false) {
            Regimen nuevoRegimen = new Regimen();
            nuevoRegimen.setCategoria(categoria);
            nuevoRegimen.setPrecio(precio);
            List<Hotel> hotel = regimenService.conseguirHotel(idhotel);
            Hotel hotelfinal = new Hotel();
            for (Hotel x : hotel) {
                if (x.getId().equals(idhotel)) ;
                hotelfinal = x;
            }
            nuevoRegimen.setId_hotel(hotelfinal);
            regimenService.guardarRegimen(nuevoRegimen);
        }
        return "Guardado regimen correctamente";
    }


    @RequestMapping("/admin/habitacioness/regimen/borrar/{item}")
    @SchemaMapping(typeName = "Mutation", value = "borrarRegimen")
    public @ResponseBody String borrarRegimen(@Argument(value = "id") Integer id, @Argument(value = "idhotel") Integer idhotel) {

        //Consigue id del hotel del precio final y si es igual, borra o da error si es distinto
        Integer preciofinal = regimenService.conseguirRegimenIDHotel(id);

        if (preciofinal == idhotel){
            Integer comprobar = habitacion_reserva_hotelService.checkearReserva(id);
            if (comprobar.equals(0)){
                regimenService.borrarRegimen(id);
                return "Borrado con éxito";
            }
            else{
                return "Fallo al borrar";
            }
        }
        else{
            return "Fallo al borrar";
        }
    }


    @RequestMapping("/admin/habitacioness/editar/precio/hecho/{item}")
    @SchemaMapping(typeName = "Mutation", value = "crearPrecio")
    public @ResponseBody String crearPrecio(@Argument(value = "id") Integer id,
                                                  @Argument("precio_hab") GraphqlInput.Precio_HabInput nuevoprecio,
                                                  @Argument("fechainicio") String fechainicio,
                                                  @Argument("fechafin") String fechafin,
                                                  @Argument("idhotel") Integer idhotel) {
        //Formateo de fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechap =  LocalDate.parse(fechainicio, formatter);
        LocalDate fechap1 =  LocalDate.parse(fechafin, formatter);

        Precio_Hab precio_hab = new Precio_Hab();

        //Traslado del input al precio_hab y guardado
        precio_hab.setFecha_inicio(fechap);
        precio_hab.setFecha_fin(fechap1);
        Hotel hotel = precio_habitacionService.conseguirIDHotelprecio(idhotel);
        Habitaciones habitacion = precio_habitacionService.conseguirIDHabitacionprecio(id);
        precio_hab.setId_hotel(hotel);
        precio_hab.setId_hab(habitacion);
        precio_hab.setPrecio(nuevoprecio.getPrecio());
        precio_hab.getId_hab().setId(nuevoprecio.getId_hotel().getId());
        precio_habitacionService.guardarPrecio(precio_hab);
        return "Creado correctamente";
    }


    @RequestMapping("/admin/habitacioness/precio/borrar/{item}")
    @SchemaMapping(typeName = "Mutation", value = "borrarPrecio")
    public @ResponseBody String borrarPrecio(@Argument(value = "id") Integer id,
                                             @Argument(value = "idhotel")Integer idHotel) {

        //Consigue el id del Hotel del precio final y devuelve el borrado o el error
        Integer preciofinal = precio_habitacionService.conseguirPrecioHabitacion(id);
        if (preciofinal == idHotel){
            precio_habitacionService.borrarPrecio(id);
            return "Borrado correctamente";
        }
        else{
            return "Error al borrar";
        }
    }

    @PostMapping("/listarClientes")
    @SchemaMapping(typeName = "Query", value = "listarClientes")
    public @ResponseBody List<Cliente> listarClientes(@Argument(value = "email") String email) {

        List<Cliente> todos = clienteRepository.findAll();
        List<Hotel> hoteles = hotelRepository.findAll();
        List<Cliente> falsisimo = new ArrayList<>();
        boolean cierto = false;

        //Busca cada hotel para ver si el email es igual que el email dado
        for (Hotel x : hoteles) {
            if (x.getEmail().getEmail().equals(email)) {
                cierto = true;
                break;
            } else {
                cierto = false;
            }
        }
        //Devuelve según sea el email real una lista con datos o sin datos
        if (cierto=true){
            return todos;
        }else {
            return falsisimo;
        }
    }


    @PostMapping("/listarHoteles")
    @SchemaMapping(typeName = "Query", value = "listarHoteles")
    public @ResponseBody List<Hotel> listarHoteles(@Argument(value = "email") String email) {

        List<Cliente> todos = clienteRepository.findAll();
        List<Hotel> hoteles = hotelRepository.findAll();
        List<Hotel> falsisimo = new ArrayList<>();
        boolean cierto = false;

        //Busca cada hotel para ver si el email es igual que el email dado
        for (Cliente x : todos) {
            if (x.getEmail().getEmail().equals(email)) {
                cierto = true;
                break;
            } else {
                cierto = false;
            }
        }
        //Devuelve según sea el email real una lista con datos o sin datos
        if (cierto=true){
            return hoteles;
        }else {
            return falsisimo;
        }
    }


    }

















