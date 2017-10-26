DELETE FROM PREFERENCIAS;
DELETE FROM PEDIDOSDEORDEN;
DELETE FROM CONDICIONZONA;
DELETE FROM REPRESENTA;
DELETE FROM PRODUCTOSSIMILARES;
DELETE FROM TIPOPRODUCTO;
DELETE FROM INGREDIENTESSIMILARES;
DELETE FROM CLIENTESFRECUENTES;
DELETE FROM MENUS;
DELETE FROM BEBIDAS;
DELETE FROM PLATOSFUERTES;
DELETE FROM POSTRES;
DELETE FROM ENTRADAS;
DELETE FROM ORDENES;
DELETE FROM PEDIDOS;
DELETE FROM INGREDIENTES;
DELETE FROM CLIENTES;
DELETE FROM REPRESENTANTES;
DELETE FROM PRODUCTOS;
DELETE FROM RESTAURANTES;
DELETE FROM TIPOS;
DELETE FROM ZONAS;
DELETE FROM CONDICIONESTECNICAS;



/* TIPOS */

insert into TIPOS (ID, NAME) values (1, 'Red');
insert into TIPOS (ID, NAME) values (2, 'Goldenrod');
insert into TIPOS (ID, NAME) values (3, 'Aquamarine');
insert into TIPOS (ID, NAME) values (4, 'Fuscia');
insert into TIPOS (ID, NAME) values (5, 'Orange1');
insert into TIPOS (ID, NAME) values (6, 'Orange2');
insert into TIPOS (ID, NAME) values (7, 'Green');
insert into TIPOS (ID, NAME) values (8, 'Crimson');
insert into TIPOS (ID, NAME) values (9, 'Pink');
insert into TIPOS (ID, NAME) values (10, 'Mauv');
insert into TIPOS (ID, NAME) values (11, 'Indigo');
insert into TIPOS (ID, NAME) values (12, 'Puce');
insert into TIPOS (ID, NAME) values (13, 'Orange3');
insert into TIPOS (ID, NAME) values (14, 'Maroon');
insert into TIPOS (ID, NAME) values (15, 'Pink2');
insert into TIPOS (ID, NAME) values (16, 'Violet');
insert into TIPOS (ID, NAME) values (17, 'Green2');
insert into TIPOS (ID, NAME) values (18, 'Teal');
insert into TIPOS (ID, NAME) values (19, 'Blue');
insert into TIPOS (ID, NAME) values (20, 'Blue1');


/* ZONAS */ 

insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (1, 'volutpat', 1, 711, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (2, 'est lacinia', 1, 935, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (3, 'habitasse platea', 0, 578, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (4, 'libero', 1, 924, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (5, 'sed', 0, 917, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (6, 'in', 0, 191, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (7, 'consectetuer adipiscing', 1, 870, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (8, 'congue etiam', 1, 935, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (9, 'justo', 0, 2, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (10, 'sit', 0, 30, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (11, 'sapien non', 1, 386, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (12, 'elementum ligula', 1, 757, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (13, 'in tempor', 1, 726, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (14, 'vel pede', 0, 671, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (15, 'nullam', 1, 490, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (16, 'eget', 0, 189, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (17, 'posuere cubilia', 1, 198, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (18, 'ut', 1, 680, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (19, 'purus', 0, 281, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (20, 'enim sit', 1, 648, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (21, 'congue vivamus', 1, 145, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (22, 'cras', 1, 607, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (23, 'faucibus', 0, 616, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (24, 'pulvinar', 0, 312, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (25, 'nisl', 1, 562, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (26, 'id consequat', 1, 708, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (27, 'nam congue', 1, 526, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (28, 'nulla', 1, 561, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (29, 'non', 0, 249, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (30, 'nulla', 1, 855, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (31, 'in', 1, 812, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (32, 'lectus', 0, 771, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (33, 'blandit ultrices', 1, 730, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (34, 'quis justo', 1, 777, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (35, 'lacus', 1, 620, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (36, 'justo', 1, 907, 1);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (37, 'in congue', 1, 6, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (38, 'in leo', 0, 128, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (39, 'ut at', 0, 186, 0);
insert into ZONAS (ID, NAME, ESPACIO_ABIERTO, CAPACIDAD, INCLUYENTE) values (40, 'nulla suscipit', 0, 20, 1);


/* Clientes */


insert into CLIENTES (ID, NAME, MESA) values (1, 'Annabell Northin', 72);
insert into CLIENTES (ID, NAME, MESA) values (2, 'Briny Clipsham', 51);
insert into CLIENTES (ID, NAME, MESA) values (3, 'Rutger Coundley', 91);
insert into CLIENTES (ID, NAME, MESA) values (4, 'Marjorie Quantick', 65);
insert into CLIENTES (ID, NAME, MESA) values (5, 'Leoline Feronet', 61);
insert into CLIENTES (ID, NAME, MESA) values (6, 'Moses Arnfield', 95);
insert into CLIENTES (ID, NAME, MESA) values (7, 'Sigfried Donat', 70);
insert into CLIENTES (ID, NAME, MESA) values (8, 'Steffi Wegenen', 36);
insert into CLIENTES (ID, NAME, MESA) values (9, 'Rory Maffione', 73);
insert into CLIENTES (ID, NAME, MESA) values (10, 'Randolf Sparkwill', 28);
insert into CLIENTES (ID, NAME, MESA) values (11, 'Ernesto Alford', 7);
insert into CLIENTES (ID, NAME, MESA) values (12, 'Barde Greenroa', 76);
insert into CLIENTES (ID, NAME, MESA) values (13, 'Veronique Menico', 77);
insert into CLIENTES (ID, NAME, MESA) values (14, 'Anatola Olechnowicz', 69);
insert into CLIENTES (ID, NAME, MESA) values (15, 'Rici Chivers', 84);
insert into CLIENTES (ID, NAME, MESA) values (16, 'Corinne Beste', 16);
insert into CLIENTES (ID, NAME, MESA) values (17, 'Tedi Grocott', 57);
insert into CLIENTES (ID, NAME, MESA) values (18, 'Kaila Ranyard', 65);
insert into CLIENTES (ID, NAME, MESA) values (19, 'Jessi Mariette', 84);
insert into CLIENTES (ID, NAME, MESA) values (20, 'Raeann Malsher', 46);
insert into CLIENTES (ID, NAME, MESA) values (21, 'Griffin Durdy', 52);
insert into CLIENTES (ID, NAME, MESA) values (22, 'Margaux Massenhove', 33);
insert into CLIENTES (ID, NAME, MESA) values (23, 'Tandie Fetherby', 69);
insert into CLIENTES (ID, NAME, MESA) values (24, 'Celestyn Ecles', 67);
insert into CLIENTES (ID, NAME, MESA) values (25, 'Babs McCathie', 27);
insert into CLIENTES (ID, NAME, MESA) values (26, 'Jolene MacDearmaid', 8);
insert into CLIENTES (ID, NAME, MESA) values (27, 'Eunice Mallabon', 96);
insert into CLIENTES (ID, NAME, MESA) values (28, 'Shurwood Gollard', 67);
insert into CLIENTES (ID, NAME, MESA) values (29, 'Justinn Dudny', 34);
insert into CLIENTES (ID, NAME, MESA) values (30, 'Pammie Dods', 69);
insert into CLIENTES (ID, NAME, MESA) values (31, 'Pavia Emtage', 99);
insert into CLIENTES (ID, NAME, MESA) values (32, 'Cammy Tace', 91);
insert into CLIENTES (ID, NAME, MESA) values (33, 'Sheena St. Quintin', 96);
insert into CLIENTES (ID, NAME, MESA) values (34, 'Bernie Smardon', 87);
insert into CLIENTES (ID, NAME, MESA) values (35, 'Stevy Abrahim', 29);
insert into CLIENTES (ID, NAME, MESA) values (36, 'Dodi Kauble', 86);
insert into CLIENTES (ID, NAME, MESA) values (37, 'Archibald Durward', 36);
insert into CLIENTES (ID, NAME, MESA) values (38, 'Tibold Buffery', 53);
insert into CLIENTES (ID, NAME, MESA) values (39, 'Hebert Ponten', 3);
insert into CLIENTES (ID, NAME, MESA) values (40, 'Milissent Kenzie', 18);


/*Restaurantes */

insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (1, 'Redhold', 5115, 12, 30);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (2, 'Solarbreeze', 9495, 12, 36);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (3, 'Tres-Zap', 9918, 11, 14);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (4, 'Domainer', 8054, 9, 1);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (5, 'Home Ing', 9171, 6, 31);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (6, 'Zoolab', 7807, 9, 5);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (7, 'Namfix', 3197, 16, 37);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (8, 'Pannier', 1903, 11, 30);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (9, 'Namfix', 5962, 20, 38);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (10, 'Zontrax', 3458, 4, 5);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (11, 'Domainer', 3547, 10, 17);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (12, 'Flexidy', 2120, 20, 18);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (13, 'Konklux', 9769, 1, 11);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (14, 'Tres-Zap', 1516, 12, 11);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (15, 'Mat Lam Tam', 9608, 5, 4);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (16, 'Voyatouch', 8010, 19, 29);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (17, 'Alphazap', 9804, 16, 14);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (18, 'Opela', 4240, 9, 1);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (19, 'Zoolab', 6893, 14, 16);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (20, 'Zamit', 3372, 2, 19);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (21, 'Toughjoyfax', 4335, 13, 36);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (22, 'Sonair', 7274, 16, 24);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (23, 'Cardguard', 6443, 17, 32);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (24, 'Ventosanzap', 2080, 1, 8);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (25, 'Cardify', 4308, 7, 24);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (26, 'Tin', 1364, 1, 26);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (27, 'Y-Solowarm', 1700, 9, 7);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (28, 'Domainer', 9333, 16, 17);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (29, 'Flowdesk', 6842, 3, 3);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (30, 'Bytecard', 5193, 14, 1);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (31, 'Aerified', 3581, 15, 28);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (32, 'Bigtax', 3204, 7, 15);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (33, 'Subin', 7929, 19, 22);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (34, 'Opela', 6766, 17, 31);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (35, 'Holdlamis', 2959, 8, 4);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (36, 'Stronghold', 8143, 8, 4);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (37, 'Domainer', 9371, 12, 33);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (38, 'Flexidy', 6276, 17, 2);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (39, 'Tres-Zap', 3761, 13, 38);
insert into RESTAURANTES (ID , NAME, PRECIO, ID_TIPO, ID_ZONA) values (40, 'Alpha', 1434, 3, 34);



/* INGREDIENTES */ 

insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (1, 'Chicken - Wieners', 'Mauris sit amet eros.', 'In est risus, auctor sed, tristique in, tempus sit amet, sem.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (2, 'Bread Foccacia Whole', 'Mauris lacinia sapien quis libero.', 'Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (3, 'Vermouth - White, Cinzano', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio.', 'Quisque ut erat.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (4, 'Soup Campbells Mexicali Tortilla', 'Nulla mollis molestie lorem.', 'Nulla tellus.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (5, 'Bagel - Ched Chs Presliced', 'Suspendisse potenti.', 'Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (6, 'Beef - Tenderloin', 'Nullam porttitor lacus at turpis.', 'Duis at velit eu est congue elementum.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (7, 'Wine - Two Oceans Sauvignon', 'Donec semper sapien a libero.', 'Vestibulum rutrum rutrum neque.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (8, 'Cheese - Marble', 'Vestibulum ac est lacinia nisi venenatis tristique.', 'Curabitur convallis.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (9, 'Rice - Long Grain', 'Suspendisse potenti.', 'Ut at dolor quis odio consequat varius.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (10, 'Yogurt - Strawberry, 175 Gr', 'Vivamus vestibulum sagittis sapien.', 'Vestibulum quam sapien, varius ut, blandit non, interdum in, ante.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (11, 'Pepper - Paprika, Spanish', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit.', 'Proin eu mi.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (12, 'Ecolab - Ster Bac', 'Mauris lacinia sapien quis libero.', 'Nulla suscipit ligula in lacus.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (13, 'Doilies - 5, Paper', 'Fusce posuere felis sed lacus.', 'Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (14, 'Crab - Blue, Frozen', 'Aliquam non mauris.', 'In hac habitasse platea dictumst.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (15, 'Pepper - Red Chili', 'Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo.', 'In quis justo.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (16, 'Pork - Backs - Boneless', 'Sed sagittis.', 'Nunc purus.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (17, 'Bread - Mini Hamburger Bun', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est.', 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (18, 'Lighter - Bbq', 'Etiam vel augue.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (19, 'Wine - Magnotta - Bel Paese White', 'Sed accumsan felis.', 'Nulla nisl.');
insert into INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION) values (20, 'Truffle Cups - White Paper', 'Vestibulum sed magna at nunc commodo placerat.', 'Cras non velit nec nisi vulputate nonummy.');


/* PRODUCTOS */

insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (1, 'feugiat non pretium quis lectus suspendisse potenti in', 'condimentum id luctus nec molestie sed', 72042, 64308, 3, 657);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (2, 'gravida sem praesent id massa id nisl', 'lorem ipsum dolor sit amet consectetuer adipiscing', 18253, 71596, 9, 173);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (3, 'posuere nonummy integer non velit donec diam neque vestibulum', 'lectus pellentesque eget nunc donec quis orci eget orci', 1248, 50657, 12, 20);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (4, 'semper porta volutpat quam pede lobortis ligula', 'aliquam erat volutpat in congue etiam justo etiam', 58382, 34496, 20, 361);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (5, 'aenean auctor gravida sem praesent', 'consectetuer adipiscing elit proin risus praesent lectus vestibulum quam', 2777, 7305, 36, 292);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (6, 'id mauris vulputate elementum nullam varius nulla facilisi cras non', 'risus dapibus augue vel accumsan tellus nisi eu', 83942, 92073, 38, 4);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (7, 'vel nulla eget eros elementum pellentesque quisque', 'purus aliquet at feugiat non', 25847, 94223, 38, 245);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (8, 'ridiculus mus etiam vel augue vestibulum', 'morbi quis tortor id nulla ultrices aliquet maecenas leo odio', 29507, 27625, 7, 11);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (9, 'fermentum donec ut mauris eget', 'morbi sem mauris laoreet ut rhoncus', 83717, 68994, 16, 399);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (10, 'aliquet ultrices erat tortor sollicitudin mi sit amet lobortis', 'nullam porttitor lacus at turpis', 20474, 75022, 32, 530);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (11, 'vitae ipsum aliquam non mauris morbi non lectus aliquam sit', 'convallis eget eleifend luctus ultricies', 6884, 89332, 32, 179);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (12, 'amet turpis elementum ligula vehicula consequat morbi a', 'lectus in quam fringilla rhoncus', 47986, 64819, 26, 841);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (13, 'diam cras pellentesque volutpat dui maecenas', 'non sodales sed tincidunt eu felis', 98284, 78059, 8, 964);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (14, 'leo odio condimentum id luctus nec', 'dui nec nisi volutpat eleifend donec ut dolor morbi vel', 26294, 47102, 19, 642);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (15, 'eget elit sodales scelerisque mauris sit amet eros', 'ipsum ac tellus semper interdum mauris ullamcorper', 56715, 26192, 27, 505);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (16, 'nec dui luctus rutrum nulla tellus in sagittis dui vel', 'et ultrices posuere cubilia curae duis', 33788, 45101, 12, 779);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (17, 'eu massa donec dapibus duis at velit eu est', 'sit amet nulla quisque arcu', 5316, 85634, 4, 978);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (18, 'mauris laoreet ut rhoncus aliquet pulvinar sed', 'vulputate luctus cum sociis natoque', 95977, 59897, 19, 490);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (19, 'leo maecenas pulvinar lobortis est phasellus', 'duis bibendum morbi non quam nec', 66359, 78829, 40, 787);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (20, 'posuere cubilia curae nulla dapibus', 'metus aenean fermentum donec ut mauris eget', 9324, 64100, 36, 119);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (21, 'at nulla suspendisse potenti cras in purus eu magna', 'justo in hac habitasse platea', 55849, 11332, 19, 333);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (22, 'augue vestibulum rutrum rutrum neque aenean auctor gravida sem praesent', 'at nunc commodo placerat praesent blandit nam nulla integer pede', 61029, 84091, 5, 816);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (23, 'ac tellus semper interdum mauris ullamcorper purus', 'nisi at nibh in hac', 27680, 32492, 22, 849);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (24, 'nulla justo aliquam quis turpis eget', 'odio consequat varius integer ac leo pellentesque ultrices mattis', 31771, 99293, 13, 75);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (25, 'amet erat nulla tempus vivamus in', 'mattis nibh ligula nec sem duis aliquam convallis', 27683, 10224, 35, 724);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (26, 'placerat praesent blandit nam nulla', 'iaculis congue vivamus metus arcu adipiscing molestie hendrerit at vulputate', 87402, 35945, 7, 858);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (27, 'vel nisl duis ac nibh fusce lacus purus', 'in felis eu sapien cursus', 79306, 36350, 14, 520);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (28, 'mollis molestie lorem quisque ut erat curabitur gravida', 'id ornare imperdiet sapien urna pretium nisl ut volutpat', 18046, 4590, 16, 259);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (29, 'suscipit ligula in lacus curabitur at', 'eu nibh quisque id justo', 98376, 24879, 1, 907);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (30, 'et tempus semper est quam pharetra magna ac', 'vestibulum ante ipsum primis in faucibus orci luctus et', 89834, 47718, 34, 821);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (31, 'in hac habitasse platea dictumst', 'consequat lectus in est risus auctor sed', 54444, 78623, 20, 954);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (32, 'pede lobortis ligula sit amet eleifend pede libero quis', 'platea dictumst morbi vestibulum velit id pretium iaculis diam erat', 54858, 1304, 32, 980);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (33, 'id pretium iaculis diam erat', 'ipsum ac tellus semper interdum mauris ullamcorper', 38713, 70824, 13, 948);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (34, 'nisi vulputate nonummy maecenas tincidunt lacus at velit vivamus vel', 'est lacinia nisi venenatis tristique fusce congue diam id', 42921, 77226, 6, 350);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (35, 'feugiat non pretium quis lectus suspendisse potenti in eleifend quam', 'leo odio porttitor id consequat', 90983, 14934, 29, 851);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (36, 'amet sem fusce consequat nulla nisl nunc nisl duis bibendum', 'justo lacinia eget tincidunt eget tempus vel pede morbi', 19380, 2159, 39, 60);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (37, 'vulputate luctus cum sociis natoque penatibus et', 'sed interdum venenatis turpis enim blandit mi', 63628, 25804, 5, 185);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (38, 'pede justo eu massa donec dapibus', 'adipiscing lorem vitae mattis nibh ligula', 82893, 62899, 20, 340);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (39, 'amet diam in magna bibendum', 'egestas metus aenean fermentum donec ut mauris eget massa', 28338, 16667, 40, 545);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (40, 'lacus at velit vivamus vel', 'dui proin leo odio porttitor id consequat in consequat ut', 33849, 13227, 2, 144);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (41, 'consectetuer eget rutrum at lorem integer tincidunt ante', 'nunc proin at turpis a pede posuere nonummy integer', 98741, 83512, 18, 923);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (42, 'amet consectetuer adipiscing elit proin interdum mauris non ligula pellentesque', 'quisque id justo sit amet', 33238, 39468, 5, 790);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (43, 'id turpis integer aliquet massa id', 'viverra diam vitae quam suspendisse potenti nullam porttitor lacus at', 17882, 3811, 2, 887);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (44, 'pellentesque eget nunc donec quis orci eget orci vehicula condimentum', 'duis aliquam convallis nunc proin at turpis', 97683, 34534, 9, 10);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (45, 'integer tincidunt ante vel ipsum praesent blandit lacinia erat vestibulum', 'et ultrices posuere cubilia curae donec pharetra magna', 14901, 80520, 40, 117);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (46, 'aenean sit amet justo morbi ut odio cras mi pede', 'fusce consequat nulla nisl nunc nisl', 61318, 36076, 25, 660);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (47, 'in hac habitasse platea dictumst morbi', 'blandit mi in porttitor pede justo eu massa donec', 2596, 98641, 20, 891);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (48, 'volutpat quam pede lobortis ligula sit amet eleifend pede', 'sapien cum sociis natoque penatibus', 31434, 30398, 7, 88);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (49, 'fusce congue diam id ornare imperdiet', 'lectus pellentesque eget nunc donec quis orci eget', 18991, 18984, 33, 271);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (50, 'molestie nibh in lectus pellentesque', 'ac tellus semper interdum mauris ullamcorper purus sit', 4653, 89677, 28, 658);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (51, 'molestie sed justo pellentesque viverra pede', 'nibh in quis justo maecenas rhoncus aliquam lacus morbi', 32327, 40728, 6, 670);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (52, 'at dolor quis odio consequat', 'potenti cras in purus eu magna vulputate', 13384, 78983, 15, 994);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (53, 'enim in tempor turpis nec euismod scelerisque quam', 'erat curabitur gravida nisi at', 7344, 89773, 14, 484);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (54, 'montes nascetur ridiculus mus etiam', 'ultrices posuere cubilia curae donec pharetra magna', 45267, 66079, 37, 534);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (55, 'nisi volutpat eleifend donec ut dolor morbi vel lectus', 'in lectus pellentesque at nulla suspendisse potenti', 56462, 85189, 20, 569);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (56, 'orci vehicula condimentum curabitur in libero ut massa volutpat', 'sagittis dui vel nisl duis ac nibh fusce lacus', 62392, 83973, 14, 678);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (57, 'morbi quis tortor id nulla', 'metus aenean fermentum donec ut mauris', 68926, 11908, 4, 600);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (58, 'sapien dignissim vestibulum vestibulum ante ipsum primis', 'in hac habitasse platea dictumst etiam faucibus', 2652, 17304, 26, 778);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (59, 'ultrices erat tortor sollicitudin mi sit amet lobortis', 'vel enim sit amet nunc viverra dapibus nulla', 69065, 51326, 16, 998);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (60, 'proin risus praesent lectus vestibulum', 'maecenas pulvinar lobortis est phasellus sit', 99281, 67602, 3, 516);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (61, 'amet diam in magna bibendum imperdiet nullam orci pede venenatis', 'vel est donec odio justo', 13469, 71419, 19, 447);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (62, 'ultrices posuere cubilia curae donec pharetra', 'ac est lacinia nisi venenatis tristique fusce', 43975, 75713, 21, 501);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (63, 'libero ut massa volutpat convallis', 'amet cursus id turpis integer aliquet massa id', 40916, 35119, 18, 695);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (64, 'amet lobortis sapien sapien non mi integer ac neque', 'non mattis pulvinar nulla pede ullamcorper augue a', 81842, 97668, 13, 79);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (65, 'id ornare imperdiet sapien urna pretium nisl ut volutpat', 'a suscipit nulla elit ac', 16800, 92843, 27, 244);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (66, 'commodo vulputate justo in blandit ultrices enim', 'ipsum praesent blandit lacinia erat vestibulum sed magna at', 93388, 8189, 6, 803);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (67, 'mollis molestie lorem quisque ut erat curabitur gravida', 'rutrum nulla tellus in sagittis dui vel nisl duis', 34725, 23533, 30, 917);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (68, 'fringilla rhoncus mauris enim leo rhoncus sed vestibulum sit', 'semper porta volutpat quam pede', 72392, 18416, 15, 320);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (69, 'convallis nunc proin at turpis a pede posuere nonummy', 'vel nisl duis ac nibh fusce lacus purus aliquet', 8051, 54364, 8, 845);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (70, 'in hac habitasse platea dictumst aliquam augue quam sollicitudin vitae', 'integer a nibh in quis justo maecenas', 77969, 40823, 8, 462);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (71, 'ac lobortis vel dapibus at diam nam tristique tortor', 'et eros vestibulum ac est lacinia nisi', 51178, 28828, 21, 30);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (72, 'nec dui luctus rutrum nulla tellus', 'morbi ut odio cras mi', 47193, 22605, 19, 564);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (73, 'a nibh in quis justo maecenas rhoncus aliquam lacus', 'donec quis orci eget orci vehicula condimentum curabitur in', 11737, 71412, 25, 158);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (74, 'volutpat erat quisque erat eros viverra eget congue eget', 'nunc donec quis orci eget orci', 30498, 99268, 35, 612);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (75, 'justo in blandit ultrices enim lorem ipsum dolor sit amet', 'luctus tincidunt nulla mollis molestie lorem', 31274, 63061, 32, 10);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (76, 'felis ut at dolor quis odio consequat varius integer', 'justo aliquam quis turpis eget elit', 92765, 22517, 17, 497);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (77, 'sed ante vivamus tortor duis mattis egestas metus aenean fermentum', 'justo sollicitudin ut suscipit a feugiat', 23208, 94821, 10, 101);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (78, 'orci luctus et ultrices posuere cubilia curae nulla', 'faucibus accumsan odio curabitur convallis duis consequat dui nec nisi', 38767, 32806, 19, 509);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (79, 'sed accumsan felis ut at dolor quis odio', 'vel accumsan tellus nisi eu orci mauris lacinia sapien', 65263, 49459, 21, 813);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (80, 'blandit mi in porttitor pede justo eu massa donec', 'amet turpis elementum ligula vehicula consequat', 60688, 65883, 37, 264);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (81, 'consequat morbi a ipsum integer', 'dui luctus rutrum nulla tellus in sagittis', 88730, 35932, 31, 46);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (82, 'sociis natoque penatibus et magnis dis parturient', 'in est risus auctor sed', 12757, 37307, 22, 861);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (83, 'duis mattis egestas metus aenean fermentum donec ut', 'commodo vulputate justo in blandit ultrices enim', 14558, 83364, 31, 741);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (84, 'nulla suspendisse potenti cras in', 'amet consectetuer adipiscing elit proin risus praesent', 12105, 25464, 26, 929);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (85, 'ac enim in tempor turpis nec euismod scelerisque', 'habitasse platea dictumst etiam faucibus cursus urna ut tellus nulla', 7839, 21698, 3, 730);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (86, 'a libero nam dui proin leo odio porttitor id consequat', 'in eleifend quam a odio in hac habitasse', 10063, 50987, 32, 684);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (87, 'ultrices vel augue vestibulum ante ipsum primis', 'imperdiet et commodo vulputate justo in blandit ultrices enim lorem', 39089, 43968, 29, 799);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (88, 'augue vel accumsan tellus nisi eu orci mauris', 'sit amet justo morbi ut odio', 17909, 17080, 33, 62);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (89, 'elementum eu interdum eu tincidunt in leo', 'potenti in eleifend quam a odio', 50939, 37026, 29, 527);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (90, 'nulla ac enim in tempor turpis nec euismod scelerisque quam', 'maecenas tristique est et tempus', 17186, 48887, 31, 590);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (91, 'hac habitasse platea dictumst etiam faucibus cursus urna ut', 'eleifend donec ut dolor morbi vel lectus in', 52245, 57232, 39, 303);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (92, 'lacus purus aliquet at feugiat non pretium quis', 'sollicitudin mi sit amet lobortis sapien sapien non mi integer', 30013, 86283, 24, 142);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (93, 'rutrum at lorem integer tincidunt', 'nulla nisl nunc nisl duis bibendum felis sed interdum', 17955, 22151, 32, 727);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (94, 'eget orci vehicula condimentum curabitur in', 'neque sapien placerat ante nulla', 68576, 87252, 7, 242);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (95, 'in faucibus orci luctus et ultrices', 'donec ut mauris eget massa tempor', 53831, 75124, 22, 416);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (96, 'sagittis dui vel nisl duis', 'pede lobortis ligula sit amet eleifend pede libero quis orci', 59495, 25623, 17, 842);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (97, 'tortor sollicitudin mi sit amet lobortis sapien sapien non mi', 'vel augue vestibulum rutrum rutrum neque aenean auctor gravida', 52121, 27956, 9, 917);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (98, 'eleifend donec ut dolor morbi', 'ridiculus mus etiam vel augue vestibulum rutrum rutrum neque', 79994, 17509, 15, 64);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (99, 'mauris viverra diam vitae quam suspendisse potenti nullam', 'pede malesuada in imperdiet et commodo vulputate', 86075, 49144, 21, 872);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (100, 'tristique fusce congue diam id ornare imperdiet sapien urna', 'tellus in sagittis dui vel nisl', 97937, 83263, 14, 603);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (101, 'quam a odio in hac habitasse', 'nam ultrices libero non mattis pulvinar nulla pede ullamcorper', 4569, 52269, 26, 250);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (102, 'vel lectus in quam fringilla rhoncus mauris enim leo rhoncus', 'auctor gravida sem praesent id massa id nisl venenatis', 29135, 74212, 6, 998);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (103, 'dapibus nulla suscipit ligula in lacus curabitur at', 'id consequat in consequat ut', 18931, 36284, 33, 444);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (104, 'id massa id nisl venenatis lacinia aenean sit amet', 'consectetuer adipiscing elit proin interdum mauris non ligula pellentesque', 1558, 41951, 34, 165);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (105, 'morbi porttitor lorem id ligula suspendisse ornare consequat', 'sapien varius ut blandit non interdum', 58477, 4621, 12, 121);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (106, 'turpis sed ante vivamus tortor duis mattis egestas metus aenean', 'nibh in lectus pellentesque at nulla', 77148, 77475, 15, 502);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (107, 'mauris vulputate elementum nullam varius nulla facilisi cras non', 'turpis integer aliquet massa id lobortis convallis tortor risus', 58389, 3662, 1, 917);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (108, 'suscipit ligula in lacus curabitur at', 'non velit nec nisi vulputate nonummy maecenas tincidunt', 18913, 83010, 23, 812);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (109, 'sagittis nam congue risus semper porta volutpat', 'integer non velit donec diam neque vestibulum eget vulputate ut', 85809, 48547, 3, 246);
insert into PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, COSTO_PRODUCCION, PRECIO, ID_RESTAURANTE, CANTIDAD) values (110, 'a ipsum integer a nibh in quis justo maecenas', 'consequat morbi a ipsum integer a nibh', 71568, 43422, 27, 323);

/*PRODUCTOS SIMILARES */

insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (14, 3);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (57, 47);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (49, 28);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (9, 2);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (43, 30);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (8, 54);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (9, 25);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (7, 23);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (18, 6);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (13, 42);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (31, 23);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (27, 14);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (38, 31);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (20, 45);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (44, 54);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (20, 48);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (25, 48);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (22, 4);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (16, 6);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (18, 33);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (55, 61);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (26, 27);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (35, 37);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (35, 26);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (7, 21);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (20, 55);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (53, 34);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (42, 27);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (17, 41);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (61, 34);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (54, 37);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (39, 29);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (7, 58);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (7, 25);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (37, 41);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (10, 15);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (7, 29);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (31, 57);
insert into PRODUCTOSSIMILARES (ID_PRODUCTO1, ID_PRODUCTO2) values (54, 10);

/* INGREDIENTES SIMILARES */

insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (4, 9);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (18, 5);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (18, 19);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (16, 14);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (12, 13);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (10, 2);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (6, 8);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (2, 3);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (2, 6);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (1, 3);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (20, 18);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (7, 17);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (9, 6);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (8, 4);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (11, 1);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (5, 1);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (12, 14);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (14, 2);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (7, 8);
insert into INGREDIENTESSIMILARES (ID_INGREDIENTE1, ID_INGREDIENTE2) values (19, 9);

/* CLIENTES FRECUENTES */ 

insert into CLIENTESFRECUENTES (ID, PASSWORD) values (6, 'HKho2Fh');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (7, '0lHuJDAQ7vu');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (8, 'zuhjJE');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (9, 'oIokrDrLn');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (10, 'supNLWqKhceo');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (11, 'Keta2ySkBl3A');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (12, '8UeH4NuO');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (13, 'emKTEiQWeop');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (14, 'Ku4cAN');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (15, 'Gma0ZXbaYN3P');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (16, 'ASTdGsKoXsR');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (17, '5Dapbwfz');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (18, 'Hct2pviwqdV');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (19, 'Y6xSS9AMth');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (20, 'HikqsI');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (21, 'BV1BmpENm');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (22, 'fHvBby8JfxD');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (23, 'srBgW5N84a8B');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (24, 'FayCAtE4x');
insert into CLIENTESFRECUENTES (ID, PASSWORD) values (25, 'hxVo3yLA9');


/* TIPO - PRODUCTO */

insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (15, 11);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (15, 5);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (15, 4);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (10, 8);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (7, 16);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (10, 2);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (10, 20);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (18, 3);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (6, 4);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (14, 1);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (3, 15);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (19, 16);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (12, 12);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (6, 13);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (7, 20);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (15, 2);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (20, 15);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (10, 10);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (18, 11);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (13, 2);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (1, 9);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (8, 1);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (11, 14);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (19, 6);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (18, 20);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (13, 16);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (6, 16);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (10, 4);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (20, 2);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (16, 20);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (11, 10);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (19, 15);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (14, 17);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (3, 12);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (14, 18);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (11, 7);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (8, 2);
insert into TIPOPRODUCTO (ID_PROD, ID_TIPO) values (16, 12);


/* REPRESENTANTES */

insert into REPRESENTANTES (ID, NAME, PASSWORD) values (1, 'Joaquin Handley', 'HfEsGwW');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (2, 'Agnese Elven', 'vLhKJoqj8C');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (3, 'Siegfried Shakspeare', 'pyOMNP0AAg');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (4, 'Janenna Kuhl', 'T5BBAZCGshj');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (5, 'Pincas Hugnin', 'R1WCvNtMk8Sv');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (6, 'Milicent Lapthorne', 'Y32DXctXB');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (7, 'Lionel Syrad', 'Oc9OgSywi');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (8, 'Aubrey Symcock', 'dUTqorbZcxo');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (9, 'Olivier Queenborough', '9vBZr6');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (10, 'Dalia Rixon', 'dYPxjI');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (11, 'Fred Nashe', '6YqFr4OBX');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (12, 'Jodie Edinboro', 'pEzIlgxeQD');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (13, 'Donovan Mordecai', 'QvUV7a8LK');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (14, 'Angeline Stivey', '0OPdLsg0');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (15, 'Odilia Stopp', 'wd6stEUn9');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (16, 'Bil Siege', 'egFTFJu');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (17, 'Carlyle Cossons', 'uwTKI0Xc8mvJ');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (18, 'Veronica Batterton', 'T82l5orRB7J');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (19, 'Casar Bradock', 'Fb2wpmO0cC');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (20, 'Mattheus Tedahl', '2N1jWFaJn');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (21, 'Byrann Coode', 'hwu8zQ0k');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (22, 'Minna Buntine', 'EoDMuI3YkN');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (23, 'Dov Cartledge', 'BkZbgkX2n7');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (24, 'Grethel Langmuir', 'fiY31sKvdWN');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (25, 'Delainey Hargreave', 'n0tmKajo');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (26, 'Nicolais Duigan', 'G9lHdlIJSwM');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (27, 'Daniele Guillond', 'xzsF6LvhDdp');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (28, 'Liva Bergeau', 'GnWBKms8x3Y');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (29, 'Michail Tedder', 'o6xdtljIPrD7');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (30, 'Angie Housecroft', 'TEoUwPqYCT');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (31, 'Milka Arnson', 'yNDxUqM4');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (32, 'Hermy Tamblyn', 'ykAhWLnK');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (33, 'Dulcy Spenclay', 'CqryZWdVGnfQ');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (34, 'Alie Ridgers', '2FxhWMO');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (35, 'Thorstein Snaith', '8nZEq7');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (36, 'Blondy Belden', 'FrQud7zeZq');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (37, 'Isidro Valentinetti', 'ATNSq4UKz');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (38, 'Elva Collinge', 'RgLqU0U');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (39, 'Della McGarrie', 'GFZ9LR');
insert into REPRESENTANTES (ID, NAME, PASSWORD) values (40, 'Karry Grandisson', 'LId1MdvR');

/* REPRESENTACIÓN */

insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (1, 31);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (2, 16);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (3, 2);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (4, 22);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (5, 37);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (6, 38);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (7, 2);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (8, 26);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (9, 15);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (10, 32);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (11, 34);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (12, 10);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (13, 11);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (14, 13);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (15, 5);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (16, 2);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (17, 8);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (18, 13);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (19, 30);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (20, 34);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (21, 35);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (22, 37);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (23, 4);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (24, 33);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (25, 12);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (26, 25);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (27, 37);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (28, 13);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (29, 9);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (30, 11);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (31, 23);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (32, 25);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (33, 7);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (34, 6);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (35, 30);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (36, 30);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (37, 2);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (38, 9);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (39, 39);
insert into REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE) values (40, 31);

DELETE FROM REPRESENTANTES WHERE ID NOT IN (SELECT ID_REPRESENTANTE FROM REPRESENTA);

/* BEIDAS */

insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (1, 'Diazepam', 20, 0, 0, 0, 'dolor');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (2, 'Nitrofurantoin Macrocrystals', 19, 0, 1, 1, 'sed');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (3, 'Methylphenidate Hydrochloride', 18, 1, 1, 0, 'sapien');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (4, 'Good Sense Childrens Mucus Relief', 17, 0, 0, 1, 'amet');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (5, 'Lamotrigine', 16, 1, 1, 0, 'lectus');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (6, 'BencoCaine Topical Anesthetic', 15, 0, 1, 1, 'morbi');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (7, 'SKIN SUPPLIES FOR MEN', 14, 0, 0, 1, 'pellentesque volutpat');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (8, 'Exchange Select Calcium Antacid', 13, 0, 0, 1, 'ultrices posuere');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (9, 'KIDS CHOICE', 12, 1, 0, 1, 'orci');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (10, 'ADVIL', 11, 1, 1, 1, 'et');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (11, 'AZO', 10, 0, 0, 0, 'cubilia curae');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (12, 'Enteric Coated Aspirin', 9, 1, 0, 0, 'elit sodales');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (13, 'Pomegranate Antibacterial Foaming Hand Wash', 8, 1, 1, 1, 'aliquet');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (14, 'Acetaminophen', 7, 1, 1, 1, 'ut');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (15, 'LEVOBUNOLOL HYDROCHLORIDE', 6, 0, 1, 0, 'ipsum integer');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (16, 'Less Drowsy Motion Sickness Relief', 5, 0, 1, 1, 'phasellus');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (17, 'Clonidine Hydrochloride', 4, 1, 0, 0, 'nulla nisl');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (18, 'CRESTOR', 3, 0, 0, 1, 'tristique');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (19, 'Dry Scalp Care', 2, 1, 1, 0, 'dolor');
insert into BEBIDAS (ID, NAME, ID_PRODUCTO, CALIENTE, NATURAL, HIELO, SABOR) values (20, 'PREP (AND) PRIME BB BEAUTY BALM SPF 35 BROAD SPECTRUM', 1, 0, 1, 1, 'dignissim vestibulum');

/* POSTRES */

insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (1, 'Fluocinolone Acetonide', 'lacus', 40);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (2, 'Flurazepam', 'in', 39);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (3, 'Ziprasidone Hydrochloride', 'convallis tortor', 38);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (4, 'California Black Walnut', 'odio odio', 37);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (5, 'Liothyronine sodium', 'ante', 36);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (6, 'omeprazole', 'suspendisse potenti', 35);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (7, 'Lithium Carbonate', 'nulla nisl', 34);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (8, 'clindamycin hydrochloride', 'eros elementum', 33);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (9, 'TOY CLEANER', 'eleifend pede', 32);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (10, 'Pain Relief', 'integer', 31);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (11, 'Ibuprofen and Pseudoephedrine Hydrochloride', 'et', 30);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (12, 'Paroxetine', 'condimentum', 29);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (13, 'Panto Flu', 'praesent', 28);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (14, 'ACETAMINOPHEN AND CODEINE', 'blandit non', 27);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (15, 'Antigas', 'ultrices', 26);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (16, 'One Step Hand Sanitizer', 'turpis nec', 25);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (17, 'Hand Sanitizer', 'cubilia curae', 24);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (18, 'ZOCOR', 'mauris sit', 23);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (19, 'ATORVASTATIN CALCIUM', 'risus dapibus', 22);
insert into POSTRES (ID, NAME, SABOR, ID_PRODUCTO) values (20, 'Xanax', 'posuere', 21);

/* ENTRADAS */

insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (1, 'SODIUM SULFACETAMIDE, SULFUR', 60);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (2, 'Sodium Bicarbonate', 59);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (3, 'Hycet', 58);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (4, 'ZEA MAYS POLLEN', 57);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (5, 'PurSan', 56);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (6, 'DIGOX', 55);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (7, 'Alka-Seltzer Heartburn Plus Gas ReliefChews Tropical Punch', 54);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (8, 'Rite Aid', 53);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (9, 'Candesartan Cilexetil-Hydrochlorothiazide', 52);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (10, 'Lodosyn', 51);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (11, 'Oxcarbazepine', 50);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (12, 'CORYLUS AMERICANA POLLEN', 49);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (13, 'Caldolor', 48);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (14, 'napoleon PERDIS FOUNDATION STICK BROAD SPECTRUM SPF 15 Look 3B', 47);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (15, 'Thiamine Hydrochloride', 46);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (16, 'Hackberry', 45);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (17, 'Clear Eyes Travelers Eye Relief', 44);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (18, 'Neutrogena Nourishing Eye Quad', 43);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (19, 'Ovaplex', 42);
insert into ENTRADAS (ID, NAME, ID_PRODUCTO) values (20, 'Nitrofurantoin Monohydrate/ Macrocrystalline', 41);

/* PLATOS FUERTES */

insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (1, 'Natroba', 20, 80);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (2, 'Topiramate', 6, 79);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (3, 'Epinephrine', 17, 78);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (4, 'Hydrocortisone', 7, 77);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (5, 'Glimepiride', 20, 76);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (6, 'Amlodipine and Benazepril Hydrochloride', 19, 75);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (7, 'Geri-Dryl allergy relief', 8, 74);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (8, 'Irbesartan', 3, 73);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (9, 'Indomethacin', 17, 72);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (10, 'Ivory Oil Controlling Makeup Broad Spectrum SPF 15', 16, 71);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (11, 'LISINOPRIL AND HYDROCHLOROTHIAZIDE', 5, 70);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (12, 'Carvedilol', 19, 69);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (13, 'CRESTOR', 2, 68);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (14, 'Firazyr', 5, 67);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (15, 'waterless anti bacterial hand sanitizer', 3, 66);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (16, 'Phisoderm Clear Confidence Face Scrub', 17, 65);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (17, 'GNP Calamine Topical Suspension', 6, 64);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (18, 'Caduet', 20, 63);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (19, 'BENZTROPINE MESYLATE', 14, 62);
insert into PLATOSFUERTES (ID, NAME, ID_ACOMPANIAMIENTO, ID_PRODUCTO) values (20, 'Ethambutol', 5, 61);

/* MENUS */

insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (1, 'et tempus semper', 110, 3, 3, 16, 8, 23);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (2, 'mauris sit', 109, 15, 15, 1, 5, 12);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (3, 'cubilia curae', 108, 8, 7, 2, 7, 18);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (4, 'vulputate', 107, 7, 16, 2, 20, 39);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (5, 'sit amet', 106, 3, 9, 16, 13, 25);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (6, 'duis ac', 105, 14, 15, 16, 14, 28);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (7, 'justo', 104, 17, 1, 12, 17, 29);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (8, 'ultrices', 103, 14, 13, 14, 6, 22);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (9, 'sapien', 102, 18, 20, 12, 10, 32);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (10, 'ante nulla justo', 101, 6, 4, 6, 6, 7);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (11, 'consequat in', 100, 18, 6, 2, 4, 4);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (12, 'congue eget semper', 99, 17, 5, 14, 13, 28);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (13, 'velit', 98, 4, 4, 3, 17, 16);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (14, 'elementum', 97, 3, 8, 13, 10, 7);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (15, 'blandit non', 96, 17, 18, 13, 15, 17);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (16, 'luctus et ultrices', 95, 11, 7, 13, 11, 11);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (17, 'lorem', 94, 5, 14, 12, 11, 19);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (18, 'mauris ullamcorper purus', 93, 10, 2, 12, 9, 40);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (19, 'erat eros viverra', 92, 4, 18, 6, 5, 37);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (20, 'duis ac nibh', 91, 3, 9, 6, 16, 3);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (21, 'diam vitae quam', 90, 20, 15, 12, 18, 16);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (22, 'semper rutrum nulla', 89, 15, 15, 8, 10, 5);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (23, 'nulla', 88, 4, 15, 11, 17, 30);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (24, 'libero non mattis', 87, 3, 2, 4, 14, 37);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (25, 'nam ultrices libero', 86, 18, 1, 5, 16, 37);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (26, 'sed', 85, 20, 14, 10, 8, 1);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (27, 'duis at', 84, 18, 19, 6, 5, 3);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (28, 'suspendisse', 83, 6, 19, 2, 17, 22);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (29, 'potenti nullam', 82, 13, 12, 1, 19, 20);
insert into MENUS (ID, NAME, ID_PRODUCTO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE) values (30, 'in', 81, 18, 16, 16, 14, 32);

/* ORDENES */



insert into ORDENES (ID, COSTOTOTAL, FECHA) values (1, 626380, TIMESTAMP '2017-06-02 23:25:39');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (2, 315777, TIMESTAMP '2017-08-18 21:21:02');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (3, 752876, TIMESTAMP '2017-02-11 12:18:02');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (4, 587830, TIMESTAMP '2017-09-09 10:33:03');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (5, 458269, TIMESTAMP '2017-03-09 19:37:09');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (6, 629086, TIMESTAMP '2017-03-09 03:29:57');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (7, 983516, TIMESTAMP '2016-12-06 02:59:08');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (8, 39465, TIMESTAMP '2017-01-05 14:29:33');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (9, 582758, TIMESTAMP '2017-06-23 23:16:42');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (10, 21353, TIMESTAMP '2016-10-15 16:27:29');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (11, 234787, TIMESTAMP '2017-02-09 22:14:09');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (12, 750048, TIMESTAMP '2016-12-03 03:00:03');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (13, 477717, TIMESTAMP '2016-11-08 15:06:07');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (14, 747770, TIMESTAMP '2017-01-24 22:23:01');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (15, 250191, TIMESTAMP '2017-04-23 13:52:32');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (16, 688365, TIMESTAMP '2017-04-25 07:12:42');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (17, 684391, TIMESTAMP '2016-10-19 08:29:24');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (18, 609239, TIMESTAMP '2017-01-27 16:33:27');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (19, 954002, TIMESTAMP '2017-02-06 14:11:16');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (20, 389049, TIMESTAMP '2017-06-29 12:32:33');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (21, 144432, TIMESTAMP '2017-07-14 19:30:33');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (22, 94174, TIMESTAMP '2017-09-08 12:47:32');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (23, 892900, TIMESTAMP '2016-12-29 21:14:36');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (24, 860782, TIMESTAMP '2017-08-22 20:31:23');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (25, 728975, TIMESTAMP '2017-04-13 17:43:29');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (26, 734018, TIMESTAMP '2017-02-08 12:41:59');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (27, 127209, TIMESTAMP '2016-10-31 09:12:29');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (28, 967376, TIMESTAMP '2017-04-29 15:40:53');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (29, 278714, TIMESTAMP '2016-10-31 05:07:37');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (30, 898512, TIMESTAMP '2017-09-11 20:38:16');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (31, 524810, TIMESTAMP '2017-08-24 15:29:43');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (32, 711413, TIMESTAMP '2017-07-11 13:36:49');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (33, 728832, TIMESTAMP '2017-05-27 19:08:58');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (34, 854951, TIMESTAMP '2017-06-19 04:34:07');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (35, 472600, TIMESTAMP '2017-05-22 10:49:45');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (36, 398898, TIMESTAMP '2016-12-26 06:26:34');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (37, 895783, TIMESTAMP '2017-06-24 19:30:02');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (38, 730514, TIMESTAMP '2016-10-07 13:20:04');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (39, 315525, TIMESTAMP '2017-04-10 03:18:05');
insert into ORDENES (ID, COSTOTOTAL, FECHA) values (40, 641375, TIMESTAMP '2017-09-17 06:10:45');


/* Condiciones técnicas */

insert into CONDICIONESTECNICAS (ID , NAME) values (1, 'pellentesque');
insert into CONDICIONESTECNICAS (ID , NAME) values (2, 'nulla elit ac');
insert into CONDICIONESTECNICAS (ID , NAME) values (3, 'at velit eu');
insert into CONDICIONESTECNICAS (ID , NAME) values (4, 'pede ullamcorper');
insert into CONDICIONESTECNICAS (ID , NAME) values (5, 'suspendisse');
insert into CONDICIONESTECNICAS (ID , NAME) values (6, 'viverra pede ac');
insert into CONDICIONESTECNICAS (ID , NAME) values (7, 'odio justo');
insert into CONDICIONESTECNICAS (ID , NAME) values (8, 'donec');
insert into CONDICIONESTECNICAS (ID , NAME) values (9, 'quam a');
insert into CONDICIONESTECNICAS (ID , NAME) values (10, 'adipiscing');
insert into CONDICIONESTECNICAS (ID , NAME) values (11, 'tortor');
insert into CONDICIONESTECNICAS (ID , NAME) values (12, 'sodales scelerisque mauris');
insert into CONDICIONESTECNICAS (ID , NAME) values (13, 'at');
insert into CONDICIONESTECNICAS (ID , NAME) values (14, 'enim');
insert into CONDICIONESTECNICAS (ID , NAME) values (15, 'nisl venenatis');
insert into CONDICIONESTECNICAS (ID , NAME) values (16, 'mi integer');
insert into CONDICIONESTECNICAS (ID , NAME) values (17, 'etiam');
insert into CONDICIONESTECNICAS (ID , NAME) values (18, 'dui nec nisi');
insert into CONDICIONESTECNICAS (ID , NAME) values (19, 'justo in');
insert into CONDICIONESTECNICAS (ID , NAME) values (20, 'maecenas ut massa');
insert into CONDICIONESTECNICAS (ID , NAME) values (21, 'primis in');
insert into CONDICIONESTECNICAS (ID , NAME) values (22, 'pretium');
insert into CONDICIONESTECNICAS (ID , NAME) values (23, 'curabitur convallis duis');
insert into CONDICIONESTECNICAS (ID , NAME) values (24, 'sagittis sapien cum');
insert into CONDICIONESTECNICAS (ID , NAME) values (25, 'nulla ac enim');
insert into CONDICIONESTECNICAS (ID , NAME) values (26, 'libero');
insert into CONDICIONESTECNICAS (ID , NAME) values (27, 'lectus in quam');
insert into CONDICIONESTECNICAS (ID , NAME) values (28, 'odio consequat');
insert into CONDICIONESTECNICAS (ID , NAME) values (29, 'eget');
insert into CONDICIONESTECNICAS (ID , NAME) values (30, 'et eros');
insert into CONDICIONESTECNICAS (ID , NAME) values (31, 'vestibulum vestibulum ante');
insert into CONDICIONESTECNICAS (ID , NAME) values (32, 'sit');
insert into CONDICIONESTECNICAS (ID , NAME) values (33, 'diam in magna');
insert into CONDICIONESTECNICAS (ID , NAME) values (34, 'in');
insert into CONDICIONESTECNICAS (ID , NAME) values (35, 'bibendum felis');
insert into CONDICIONESTECNICAS (ID , NAME) values (36, 'eget massa tempor');
insert into CONDICIONESTECNICAS (ID , NAME) values (37, 'erat curabitur gravida');
insert into CONDICIONESTECNICAS (ID , NAME) values (38, 'viverra');
insert into CONDICIONESTECNICAS (ID , NAME) values (39, 'fusce');
insert into CONDICIONESTECNICAS (ID , NAME) values (40, 'amet');

/* Condiciones de las zonas */

insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (15, 37);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (32, 5);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (7, 27);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (36, 29);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (36, 26);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (22, 15);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (32, 20);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (10, 34);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (6, 27);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (26, 19);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (14, 21);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (4, 39);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (31, 37);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (27, 26);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (25, 34);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (35, 37);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (5, 7);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (35, 6);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (25, 8);
insert into CONDICIONZONA (ID_ZONA, ID_CONDICION) values (37, 21);

/* Pedidos */

insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (1, 13, 97, TIMESTAMP '2017-01-01 18:27:19', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (2, 27, 84, TIMESTAMP '2016-05-23 22:17:51', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (3, 6, 101, TIMESTAMP '2016-05-02 07:36:23', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (4, 32, 20, TIMESTAMP '2016-02-08 04:47:12', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (5, 27, 95, TIMESTAMP '2016-09-03 19:53:39', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (6, 11, 104, TIMESTAMP '2016-08-01 22:35:15', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (7, 33, 28, TIMESTAMP '2017-05-03 22:18:08', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (8, 4, 43, TIMESTAMP '2017-08-05 06:51:16', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (9, 8, 49, TIMESTAMP '2016-03-07 04:24:43', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (10, 19, 33, TIMESTAMP '2017-08-29 23:50:41', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (11, 7, 44, TIMESTAMP '2017-10-03 22:11:52', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (12, 21, 106, TIMESTAMP '2017-02-26 22:21:34', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (13, 7, 78, TIMESTAMP '2016-10-07 16:06:17', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (14, 17, 93, TIMESTAMP '2016-03-06 19:05:33', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (15, 40, 72, TIMESTAMP '2017-09-27 23:51:09', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (16, 27, 100, TIMESTAMP '2017-08-09 08:30:54', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (17, 20, 26, TIMESTAMP '2017-07-19 05:29:02', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (18, 38, 51, TIMESTAMP '2016-01-29 11:29:29', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (19, 1, 68, TIMESTAMP '2017-02-08 09:24:30', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (20, 23, 79, TIMESTAMP '2016-08-02 13:18:42', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (21, 7, 59, TIMESTAMP '2016-07-29 12:20:30', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (22, 14, 26, TIMESTAMP '2016-10-05 06:33:49', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (23, 12, 45, TIMESTAMP '2016-03-02 13:03:56', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (24, 2, 9, TIMESTAMP '2016-07-23 06:25:31', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (25, 40, 28, TIMESTAMP '2016-03-12 22:28:27', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (26, 10, 93, TIMESTAMP '2016-07-22 18:17:39', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (27, 33, 41, TIMESTAMP '2017-08-21 16:01:44', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (28, 29, 65, TIMESTAMP '2016-03-01 06:15:18', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (29, 38, 80, TIMESTAMP '2016-05-19 14:10:00', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (30, 15, 53, TIMESTAMP '2017-05-25 21:30:39', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (31, 30, 48, TIMESTAMP '2016-09-22 03:09:07', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (32, 15, 1, TIMESTAMP '2016-01-02 02:05:56', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (33, 2, 67, TIMESTAMP '2016-06-04 04:32:00', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (34, 4, 78, TIMESTAMP '2016-06-11 02:34:37', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (35, 23, 61, TIMESTAMP '2016-12-30 06:38:00', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (36, 30, 83, TIMESTAMP '2016-10-17 17:17:49', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (37, 3, 35, TIMESTAMP '2016-05-05 12:24:51', 1);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (38, 1, 89, TIMESTAMP '2017-03-12 21:18:16', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (39, 2, 90, TIMESTAMP '2016-08-12 06:29:40', 0);
insert into PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO) values (40, 4, 48, TIMESTAMP '2017-04-30 07:47:57', 1);

/* PEDIDOS DE ORDEN */

insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (13, 13);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (21, 10);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (17, 19);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (35, 17);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (22, 38);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (40, 33);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (19, 29);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (30, 15);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (11, 1);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (39, 4);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (6, 12);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (21, 24);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (25, 6);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (32, 31);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (2, 2);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (38, 38);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (5, 34);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (14, 20);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (15, 37);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (6, 33);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (19, 38);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (6, 15);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (13, 14);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (16, 6);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (38, 36);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (18, 21);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (23, 21);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (1, 35);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (7, 23);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (6, 39);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (31, 19);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (16, 15);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (39, 9);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (15, 13);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (25, 24);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (24, 20);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (18, 36);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (34, 30);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (17, 15);
insert into PEDIDOSDEORDEN (ID_PEDIDO, ID_ORDEN) values (18, 5);

/* PREFERENCIAS */



/* insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (15, 104); */
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (6, 54);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (20, 3);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (6, 48);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (6, 71);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (12, 76);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (12, 41);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (18, 50);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (20, 70);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (15, 20);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (11, 39);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (15, 98);
insert into PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) values (19, 57);

