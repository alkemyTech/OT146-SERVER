use alkemy_ong;

create table if not exists lucas (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    age int
);

create table if not exists roles (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
	description varchar(256),
    created_at timestamp not null,
    updated_at timestamp
);