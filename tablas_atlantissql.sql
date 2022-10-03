create table login(
email varchar(50) not null,
password varchar(50) not null,
es_cliente int(1) not null, -- bool para busqueda en tabla cliente o Hotel
primary key (email)
);


create table cliente(
id int(10) auto_increment not null,
nombre varchar(50) not null,
apellidos varchar (150) not null,
dni varchar(9) not null,
pais varchar(50) not null,
telefono varchar(20) not null,
email varchar(50) not null,
constraint cliente_fk1 foreign key (email) references login(email),
primary key (id)
);


create table hotel(
id int(10) not null auto_increment,
nombre varchar(50) not null,
pais varchar(50) not null,
localidad varchar(50) not null,
direccion varchar(50) not null,
fecha_apertura date,
fecha_cierre date,
num_estrellas int(1) not null,
tipo_hotel int(1) not null, -- apartahotel, hotel, rural...etc
telefono varchar(20) not null,
email varchar(50) not null,
url_icono varchar(200),
url_imagen_general varchar(200),
constraint hotel_fk1 foreign key (email) references login(email),
primary key (id)
);




create table regimen(
id int(10) not null auto_increment,
id_hotel int (10) not null,
categoria int (1) not null, -- media pension, completa, todo incluido....etc
precio double not null,
constraint regimen_fk1 foreign key (id_hotel) references hotel(id),
primary key (id)
);


create table habitaciones (
id int(10) not null auto_increment,
id_hotel int (10) not null,
tipo_hab int(1) not null,
num_hab int(10) not null,
hab_ocupadas int(10) not null,
max_cliente int(2) not null,
constraint habitaciones_fk1 foreign key (id_hotel) references hotel(id),
primary key (id)
);


create table precio_hab(
id int(10) not null auto_increment,
id_hab int(10) not null,
id_hotel int(10) not null,
fecha_inicio date not null,
fecha_fin date not null,
precio double not null,
constraint precio_hab_fk1 foreign key (id_hab) references habitaciones(id),
constraint precio_hab_fk2 foreign key (id_hotel) references hotel(id),
primary key (id)
);


create table reserva(
id int(10) not null auto_increment,
id_hotel int (10) not null,
id_cliente int (10) not null,
id_regimen int (10) not null,
num_clientes int(5) not null,
fecha_entrada date not null,
fecha_salida date not null,
precio_total double not null,
constraint reserva_fk1 foreign key (id_hotel) references hotel(id),
constraint reserva_fk2 foreign key (id_cliente) references cliente(id),
constraint reserva_fk3 foreign key (id_regimen) references regimen(id),
primary key (id)
);


create table hab_reserva_regimen (
id int(10) not null auto_increment,
id_reserva int(10) not null,
id_regimen int(10) not null,
id_hab int(10) not null,
constraint hab_reserva_regimen_fk1 foreign key (id_reserva) references reserva(id),
constraint hab_reserva_regimen_fk2 foreign key (id_regimen) references regimen(id),
constraint hab_reserva_regimen_fk3 foreign key (id_hab) references habitaciones(id),
primary key (id)
);
















