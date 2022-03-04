--ACTIVITIES--
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(1, 'actividad1', 'cont1', 'http://img1', '2022-02-24', '2022-02-24', 0);
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(2, 'actividad2', 'cont2', 'http://img2','2022-02-24', '2022-02-24', 0);
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(3, 'actividad3', 'cont3', 'http://img3' ,'2022-02-24', '2022-02-24', 0);
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(4, 'actividad4', 'cont4', 'http://img4' ,'2022-02-24', '2022-02-24', 0);
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(5, 'actividad5', 'cont5', 'http://img5', '2022-02-24', '2022-02-24', 0);
--ROLES--
INSERT INTO `alkemy_ong`.`roles` (`id`, `name`, `description`, `created_at`) VALUES (1, 'Administrador', 'Usuario Admin', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`roles` (`id`, `name`, `description`, `created_at`) VALUES (2, 'Regular', 'Usuario Regular', CURRENT_TIMESTAMP);
--USUARIOS--
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (1, 'Admin', 'Uno', 'uno@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (2, 'Admin', 'Dos', 'dos@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (3, 'Admin', 'Tres', 'tres@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (4, 'Admin', 'Cuatro', 'cuatro@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (5, 'Admin', 'Cinco', 'cinco@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (6, 'Admin', 'Seis', 'seis@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (7, 'Admin', 'Siete', 'siete@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (8, 'Admin', 'Ocho', 'ocho@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (9, 'Admin', 'Nueve', 'nueve@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (10, 'Admin', 'Diez', 'diez@mail.com', '1234', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (11, 'Regular', 'Uno', 'reg@mail.com', '1234', '2', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (12, 'Regular', 'Dos', 'reg2@mail.com', '1234', '2', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (13, 'Regular', 'Tres', 'reg3@mail.com', '1234', '2', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (14, 'Regular', 'Cuatro', 'reg4@mail.com', '1234', '2', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (15, 'Regular', 'Cinco', 'reg5@mail.com', '1234', '2', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (16, 'Regular', 'Seis', 'reg6@mail.com', '1234', '2', CURRENT_TIMESTAMP, 1);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (17, 'Regular', 'Siete', 'reg7@mail.com', '1234', '2', CURRENT_TIMESTAMP, 1);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (18, 'Regular', 'Ocho', 'reg8@mail.com', '1234', '2', CURRENT_TIMESTAMP, 1);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (19, 'Regular', 'Nueve', 'reg9@mail.com', '1234', '2', CURRENT_TIMESTAMP, 1);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (20, 'Regular', 'Diez', 'reg10@mail.com', '1234', '2', CURRENT_TIMESTAMP, 1);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (21, 'Admin', 'Test', 'admin@gmail.com', '$2a$10$hqv5Urco59/GD6M6/5cIF.PfgFVqmywIz6guVGarCKtnkCTg/mvHG', '1', CURRENT_TIMESTAMP, 0);
INSERT INTO `alkemy_ong`.`users` (`id`, `first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`, `deleted`) VALUES (22, 'User', 'Test', 'user@gmail.com', '$2a$10$nzalharca5Dag806LTD1XeET59dDMtvlFNlr2eijyo2TLlMSX5LuO', '2', CURRENT_TIMESTAMP, 0);
