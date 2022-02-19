create table if not exists members (
    id bigint unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name varchar(256) NOT NULL,
    facebookUrl varchar(256),
    instagramUrl varchar(256),
    linkedinUrl varchar(256),
    image varchar(256) NOT NULL,
    description varchar(256),
    created_at date,
    updated_at date,
    deleted bit(1) NOT NULL default 0
);

CREATE TABLE IF NOT EXISTS users (
   id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   photo VARCHAR(255) NULL,
--   role_id INT NOT NULL,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL,
   deleted BIT(1) NOT NULL DEFAULT 0,
   PRIMARY KEY (id)
);

create table if not exists testimonials (
    id bigint unsigned not null AUTO_INCREMENT,
    name varchar(256) not null,
    image varchar(256),
    content varchar(500),
    createdAt timestamp not null,
    updatedAt timestamp null on update current_timestamp,
    deleted bit(1) not null default 0,
    primary key(id)
);

create table if not exists roles (
    id bigint unsigned not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    description varchar(256),
    created_at timestamp not null,
    updated_at timestamp,
    deleted bit(1) not null default 0
);

CREATE TABLE IF NOT EXISTS organizations (
  id_organization BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  image VARCHAR(256) NOT NULL,
  address VARCHAR(256) NULL,
  phone INT UNSIGNED NULL,
  email VARCHAR(45) NOT NULL,
  aboutUsText TEXT NULL,
  welcomeText TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  deleted BIT(1) NOT NULL DEFAULT 0
);