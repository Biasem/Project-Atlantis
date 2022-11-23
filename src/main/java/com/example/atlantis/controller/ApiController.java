package com.example.atlantis.controller;



import com.example.atlantis.model.*;
import com.example.atlantis.repository.*;
import com.example.atlantis.service.*;
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
    ClienteService clienteService;

    @Autowired
    HotelService hotelService;

    @Autowired
    LoginService loginService;

    @Autowired
    private RegimenService regimenService;

    @Autowired
    private Habitacion_Reserva_HotelService habitacion_reserva_hotelService;

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
        ModelAndView model = new ModelAndView("historialReservaCliente");
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
        model.addObject("todas", todasmodelohistorial);

        return todasmodelohistorial;

    }

    @RequestMapping(value = "/hoteless/{item}", method = RequestMethod.GET)
    @SchemaMapping(typeName = "Query", value = "resultadoHotel")
    public @ResponseBody Hotel resultadoHotel(@RequestParam(value = "id") @PathVariable @Argument(name = "id") Integer id) {

        ModelAndView model = new ModelAndView("hotelWeb");
        // Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idCliente = 0;
        Integer idHotel = 0;
        if (correo != null) {
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
        }
        Integer idHotelreserva = id;

        if (idHotel > 0) {
            Integer finalIdHotel = idHotel;
            Hotel hotelConectado = hotelRepository.findAll().stream().filter(x -> x.getId().equals(finalIdHotel)).collect(Collectors.toList()).get(0);
            model.addObject("hotelConectado", hotelConectado);
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        model.addObject("idHotelreserva", idHotelreserva);
        // Gestión sesión

        List<Hotel> listaHoteles = hotelService.getAll();
        List<Hotel> hotelfinal = new ArrayList<>();
        List<Habitaciones> listaHabitaciones = habitacionesService.getAll();
        List<ComentarioHotel> listaComentariosHotel = comentarioHotelRepository.findAll().stream().filter(x -> x.getHotel().getId().equals(idHotelreserva)).collect(Collectors.toList());
        BuscadorID numero = new BuscadorID(id);
        Hotel definitivo = buscadorService.Comparar(numero, listaHoteles);
        hotelfinal.add(definitivo);
        List<TipoRegimen> regimen = regimenService.getAll().stream().filter(r -> r.getId_hotel().getId().equals(id)).collect(Collectors.toList()).stream().map(Regimen::getCategoria).collect(Collectors.toList());
        Double latitud = definitivo.getLatitud();
        Double longitud = definitivo.getLongitud();
        Integer estrellas = definitivo.getNum_estrellas();
        model.addObject("latitud", latitud);
        model.addObject("longitud", longitud);
        model.addObject("listaComentariosHotel", listaComentariosHotel);
        model.addObject("idCliente", idCliente);
        model.addObject("texto", new Comentario());
        model.addObject("hotelfinal", hotelfinal);
        model.addObject("regimen", regimen);
        model.addObject("listaHabitaciones", habitacionesService.conseguir(id, listaHabitaciones).stream().filter(h -> h.getHab_ocupadas() < h.getNum_hab()).collect(Collectors.toList()));
        model.addObject("estrellas", estrellas);
        model.addObject("fechamin", LocalDate.now());
        Objeto_Aux_Reserva_html objetoInteger = new Objeto_Aux_Reserva_html();
//        if((session.getAttribute("fecha_inicial")!=null)&&(session.getAttribute("fecha_final")!=null)){
//            objetoInteger.setFechainicio(session.getAttribute("fecha_inicial").toString());
//            objetoInteger.setFechafin(session.getAttribute("fecha_final").toString());
//        }
        model.addObject("objeto_integer", objetoInteger);
        model.addObject("comentarios", comentarioService.conseguirComentarios(id));
        Integer comprobante = 0;
        model.addObject("comprobante", comprobante);

        return definitivo;
    }

    @PostMapping("/reservarr")
    @MutationMapping
    public ModelAndView reservarHab(@RequestBody @ModelAttribute("objeto_integer") @PathVariable @Argument(name = "objeto") GraphqlInput.Objeto_Aux_Reserva_htmlInput objeto_aux_reservaHtml,
                                    @RequestParam("idhotel") @PathVariable @Argument(name = "idhotel") Integer idhotel,
                                    @PathVariable @Argument(name = "correo") String correo) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate fechap = LocalDate.parse(objeto_aux_reservaHtml.getFechainicio(), formatter);
        LocalDate fechap1 = LocalDate.parse(objeto_aux_reservaHtml.getFechafin(), formatter);

        ModelAndView model = new ModelAndView("pagarReserva");
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
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        // Gestión sesión
        if (fechap.isAfter(fechap1) ||
                fechap.equals(fechap1)) {
            return new ModelAndView("redirect:/hoteles/item?id=" + idhotel); //siento esta fechoria xd
        }
        for (Integer i : objeto_aux_reservaHtml.getCantidadHabitaciones()) {
            if (i == null) {
                return new ModelAndView("redirect:/hoteles/item?id=" + idhotel);
            }
        }

        //objeto Reserva_para_bbdd
        reserva_para_bbdd = reservaService.precioHabReservadaApi(idhotel, objeto_aux_reservaHtml);
        reserva_para_bbdd.setIdCliente(idCliente);
        List<Ob_mostrar_reserva> listamostrar = reservaService.obtenerlistareserva(reserva_para_bbdd);
        for (Ob_mostrar_reserva omr : listamostrar) {
            if (omr.getCantHab() > omr.getHabitaciones().getNum_hab() - omr.getHabitaciones().getHab_ocupadas())
                return new ModelAndView("redirect:/hoteles/item?id=" + idhotel);
        }

        Long dias = DAYS.between(reserva_para_bbdd.getFechaEntrada(), reserva_para_bbdd.getFechasalida());

        model.addObject("dias", dias);
        model.addObject("total", reserva_para_bbdd.getPrecioTotal());
        model.addObject("listareserva", listamostrar);

        return model;
    }


    @GetMapping("/main")
    @SchemaMapping(typeName = "Query", value = "listaHotelAzar")
    public List<Hotel> listaHotel(@Argument(name = "busqueda") GraphqlInput.BusquedaInput input) {
        ModelAndView model = new ModelAndView("main");

        // Gestión sesión
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Integer idCliente = 0;
        Integer idHotel = 0;
        if (correo != null) {
            idCliente = clienteService.conseguirId(correo);
            idHotel = hotelService.conseguirId(correo);
        }
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        // Gestión sesión

        List<Hotel> listaprimera = hotelService.getAll();
        Map<Hotel, Integer> lista = hotelService.filtrarmejores(listaprimera);

        ModelAndView azar = new ModelAndView("mainazar");
        Collections.shuffle(listaprimera);
        List<Hotel> listaHotel = listaprimera.subList(0, 3);
        azar.addObject("fechamin", LocalDate.now());
        azar.addObject("busqueda", input);
        azar.addObject("listaHotel", listaHotel);
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        return listaHotel;
    }


    @PostMapping("/main")
    @SchemaMapping(typeName = "Query", value = "listaHotelBusqueda")
    public ModelAndView listaHoteles(@Argument(name = "busqueda") GraphqlInput.BusquedaInput busqueda, @Argument(name = "correo") String correo) {
        ModelAndView model = new ModelAndView("resultado");

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
        model.addObject("idHotel", idHotel);
        model.addObject("idCliente", idCliente);
        // Gestión sesión


        List<Hotel> listaHoteles = hotelService.getAll();
        List<Hotel> filtro = busquedaService.AccionBuscarApi(busqueda,listaHoteles);
        Map<Integer, Hotel> lista = hotelService.filtrarHotel(filtro);
        if(LocalDate.parse(busqueda.getFechaInicial()).isAfter(LocalDate.parse(busqueda.getFechaFinal())))
        {
            return new ModelAndView("redirect:main");
        }
        model.addObject("fechamin", LocalDate.now());
        model.addObject("lista", lista);
        return model ;
    }


}




