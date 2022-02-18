create table if not exists lucas (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    age int
);

CREATE TABLE IF NOT EXISTS `alkemy_ong`.`organizations` (
  `id_organization` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `image` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NULL,
  `phone` INT UNSIGNED NULL,
  `email` VARCHAR(45) NOT NULL,
  `aboutUsText` TEXT NULL,
  `welcomeText` TEXT NOT NULL,
  `timestamp` TIMESTAMP NOT NULL,
  `isDelete` TINYINT NOT NULL,
  PRIMARY KEY (`id_organization`))
ENGINE = InnoDB;