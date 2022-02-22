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
--   FOREIGN KEY (role_id) REFERENCES Role(id)
);

create table if not exists testimonials (
    id bigint unsigned not null AUTO_INCREMENT,
    name varchar(256) not null,
    image varchar(256),
    content varchar(500),
    created_at timestamp not null,
    updated_at timestamp null on update current_timestamp,
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

create table if not exists category (
    id bigint not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    description varchar(256),
    image varchar(256),
    created_at date,
    updated_at date,
    deleted bit(1) not null default 0
);

create table if not exists slides (
    id bigint unsigned not null primary key AUTO_INCREMENT,
    image_url varchar(256) not null,
    text varchar(256),
    order int not null,
    created_at timestamp not null,
    updated_at timestamp,
    organization_id bigint unsigned not null
);

create table if not exists news (
    id bigint unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256) NOT NULL,
    content VARCHAR(256) NOT NULL,
    image VARCHAR(256) NOT NULL,
    -- category_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted bit(1) NOT NULL default 0
    --  FOREIGN KEY (category_id) REFERENCES Category(id)
);

create table if not exists activities (
    id int not null primary key AUTO_INCREMENT,
    name  varchar (256)not null,
    content  varchar(256) not null,
    image  varchar(256) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP ,
    deleted  bit(1) not null

CREATE TABLE IF NOT EXISTS organizations (
  id_organization BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  image VARCHAR(256) NOT NULL,
  address VARCHAR(45) NULL,
  phone INT UNSIGNED NULL,
  email VARCHAR(45) NOT NULL,
  about_us_text TEXT NULL,
  welcome_text TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  deleted BIT(1) NOT NULL DEFAULT 0
);

