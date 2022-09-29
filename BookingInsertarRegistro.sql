insert into login (email, password, es_cliente) values('femarro@gmail.com','Ls261hg&',1);
insert into login (email, password, es_cliente) values('macare189@gmail.com','7aP$S#22',1);
insert into login (email, password, es_cliente) values('emiglesias1@outlook.com','1#4Kj8h@',1);
insert into login (email, password, es_cliente) values('eloysaavedra@gmail.com','Ut83S!L2',1);
insert into login (email, password, es_cliente) values('sarita18sm@outlook.com','j300$iQf',1);
insert into login (email, password, es_cliente) values('ven1.al-andalus@hoteles-silken.com','6o&#72D6',0);
insert into login (email, password, es_cliente) values('reservas@exesevillamacarena.com','98V@4Zgx',0);
insert into login (email, password, es_cliente) values('reserva@letohgv.com','47fF2n*F', 0);
insert into login (email, password, es_cliente) values('hmreservas@gmail.com', '*f#MI598', 0);
insert into login (email, password, es_cliente) values('reservas.lasarenas@hsantos.es', '@2iAog48',0);


insert into cliente (nombre, apellidos,dni,telefono,email,pais) values ('Felipe', 'Martinez Romero', '88008630G', 921864215,'femarro@gmail.com', 'Spain');
insert into cliente (nombre, apellidos,dni,telefono,email,pais) values('Macarena', 'Mañas Rodríguez', '89900216R',746649411,'macare189@gmail.com', 'Spain');
insert into cliente (nombre, apellidos,dni,telefono,email,pais) values('Enric', 'Mayoral Iglesias','26694110B', 680965067, 'emiglesias1@outlook.com', 'Spain');
insert into cliente (nombre, apellidos,dni,telefono,email,pais) values('Eloy', 'Saavedra Pérez', '72901566F',792963375, 'eloysaavedra@gmail.com', 'Spain');
insert into cliente (nombre, apellidos,dni,telefono,email,pais) values('Sara', 'Segura Moreno', '14160838Z', 603860605, 'sarita18sm@outlook.com', 'Spain');


insert into hotel(nombre,pais, localidad, direccion, fecha_apertura, fecha_cierre, num_estrellas, tipo_hotel, telefono, email) values('Silken Al-Andalus Palace', 'Spain', 'Sevilla', 'Paraná, Bellavista - Palmera','12*07*22', '11*07*23', 4, 1, 954230600, 'ven1.al-andalus@hoteles-silken.com');
insert into hotel(nombre,pais, localidad, direccion, fecha_apertura, fecha_cierre, num_estrellas, tipo_hotel, telefono, email) values('Exe Sevilla Macarena', 'Spain', 'Sevilla', 'San Juan de Ribera, 2, Macarena', '01*09*22', '20*6*23', 4, 2, 954375800,'reservas@exesevillamacarena.com');
insert into hotel(nombre,pais, localidad, direccion, fecha_apertura, fecha_cierre, num_estrellas, tipo_hotel, telefono, email) values('Letoh Letoh Gran Vía', 'Spain', 'Madrid', '41 Calle de Leganitos, Centro de Madrid','01*03*22','30*01*23', 3, 3, 913933333, 'reserva@letohgv.com');
insert into hotel(nombre,pais, localidad, direccion, fecha_apertura, fecha_cierre, num_estrellas, tipo_hotel, telefono, email) values('Hotel Mirador Puerta del Sol','Spain', 'Madrid', 'Montera, 6, Centro de Madrid', '01*09*22', '01/08/23', 2, 4, 910325364,'hmreservas@gmail.com');
insert into hotel(nombre,pais, localidad, direccion, fecha_apertura, fecha_cierre, num_estrellas, tipo_hotel, telefono, email) values('Las Arenas Balneario Resort','Spain', 'Valencia', 'Eugenia Viñes, 22', '01*09*22', '01/08/23', 5, 1, 963120600, 'reservas.lasarenas@hsantos.es');


insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(1, 2, 100, 0, 3);
insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(1, 3, 50, 0, 5);
insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(2, 2, 10, 0, 3);
insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(2, 4, 30, 0, 8);
insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(3, 1, 10, 0, 1);
insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(3, 2, 20, 0, 3);
insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(4, 1, 10, 0, 1);
insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(5, 2, 100, 0, 3);
insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(5, 3, 20, 0, 5);
insert into habitaciones(id_hotel, tipo_hab,num_hab,hab_ocupadas,max_cliente) values(5, 4, 10, 0, 6);


insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(1, 1, '26*09*22', '30*03*23', 60);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(2, 1, '26*09*22', '30*03*23', 80);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(3, 2, '20*10*22', '30*10*22', 70);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(4, 2, '20*10*22', '30*10*22', 85);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(5, 3, '10*12*22', '31*12*22', 72);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(6, 3, '10*12*22', '31*12*22', 50);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(7, 4, '01*01*22', '31*10*22', 30);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(7, 4, '01*01*22', '31*10*22', 40);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(8, 5, '10*01*23', '31*09*23', 80);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(9, 5, '10*01*23', '31*09*23', 100);
insert into precio_hab(id_hab ,id_hotel,fecha_inicio, fecha_fin, precio) values(10, 5, '10*01*23', '31*09*23', 120);


insert into regimen(id_hotel,categoria,precio) values(1, 1, 15);
insert into regimen(id_hotel,categoria,precio) values(1, 2, 25);
insert into regimen(id_hotel,categoria,precio) values(1, 3, 40);
insert into regimen(id_hotel,categoria,precio) values(2, 1, 15);
insert into regimen(id_hotel,categoria,precio) values(2, 2, 25);
insert into regimen(id_hotel,categoria,precio) values(2, 3, 40);
insert into regimen(id_hotel,categoria,precio) values(3, 1, 10);
insert into regimen(id_hotel,categoria,precio) values(3, 2, 20);
insert into regimen(id_hotel,categoria,precio) values(3, 3, 35);
insert into regimen(id_hotel,categoria,precio) values(4, 1, 10);
insert into regimen(id_hotel,categoria,precio) values(5, 1, 20);
insert into regimen(id_hotel,categoria,precio) values(1, 2, 30);
insert into regimen(id_hotel,categoria,precio) values(1, 3, 45);

insert into reserva(id_hotel, id_cliente, id_regimen, num_clientes, fecha_entrada, fecha_salida, precio_total) values(1, 1, 1, 3,'28*09*22', '30*09*22', 90);
insert into reserva(id_hotel, id_cliente, id_regimen, num_clientes, fecha_entrada, fecha_salida, precio_total) values(2, 2, 4, 3,'20*10*22', '25*10*22', 150);
insert into reserva(id_hotel, id_cliente, id_regimen, num_clientes, fecha_entrada, fecha_salida, precio_total) values(3, 3, 8, 2,'21*11*22', '25*11*22', 160);
insert into reserva(id_hotel, id_cliente, id_regimen, num_clientes, fecha_entrada, fecha_salida, precio_total) values (4, 4, 13, 1, '16*09*22', '20*09*22', 180);


insert into hab_reserva_regimen(id_reserva,id_regimen,id_hab) values(1,1,1);
insert into hab_reserva_regimen(id_reserva,id_regimen,id_hab) values(2,4,3);
insert into hab_reserva_regimen(id_reserva,id_regimen,id_hab) values(3,8,5);
insert into hab_reserva_regimen(id_reserva,id_regimen,id_hab) values(4,13,8);



