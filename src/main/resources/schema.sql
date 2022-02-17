create table if not exists lucas (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    age int
);

create table if not exists activities (

    id int not null primary key AUTO_INCREMENT,
    name: VARCHAR NOT NULL
    content: VARCHAR NOT NULL
    image: VARCHAR NOT NULL
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted: bit(1) NOT NULL default = 0

);