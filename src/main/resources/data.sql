--ACTIVITIES--
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(1, 'actividad1', 'cont1', 'http://img1', '2022-02-24', '2022-02-24', 0);
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(2, 'actividad2', 'cont2', 'http://img2','2022-02-24', '2022-02-24', 0);
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(3, 'actividad3', 'cont3', 'http://img3' ,'2022-02-24', '2022-02-24', 0);
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(4, 'actividad4', 'cont4', 'http://img4' ,'2022-02-24', '2022-02-24', 0);
INSERT INTO `alkemy_ong`.`activities` (`id`, `name`, `content`, `image`, `created_at`, `updated_at`, `deleted`) VALUES(5, 'actividad5', 'cont5', 'http://img5', '2022-02-24', '2022-02-24', 0);
--ROLES--
INSERT INTO `alkemy_ong`.`roles` (`name`, `description`, `created_at`) VALUES ('Administrador', 'Usuario Admin', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`roles` (`name`, `description`, `created_at`) VALUES ('Regular', 'Usuario Regular', CURRENT_TIMESTAMP);
--USUARIOS--
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Uno', 'uno@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Dos', 'dos@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Tres', 'tres@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Cuatro', 'cuatro@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Cinco', 'cinco@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Seis', 'seis@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Siete', 'siete@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Ocho', 'ocho@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Nueve', 'nueve@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Admin', 'Diez', 'diez@mail.com', '1234', '1', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Uno', 'reg@mail.com', '1234', '2', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Dos', 'reg2@mail.com', '1234', '2', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Tres', 'reg3@mail.com', '1234', '2', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Cuatro', 'reg4@mail.com', '1234', '2', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Cinco', 'reg5@mail.com', '1234', '2', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Seis', 'reg6@mail.com', '1234', '2', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Siete', 'reg7@mail.com', '1234', '2', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Ocho', 'reg8@mail.com', '1234', '2', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Nueve', 'reg9@mail.com', '1234', '2', CURRENT_TIMESTAMP);
INSERT INTO `alkemy_ong`.`users` (`first_name`, `last_name`, `email`, `password`, `role_id`, `created_at`) VALUES ('Regular', 'Diez', 'reg10@mail.com', '1234', '2', CURRENT_TIMESTAMP);