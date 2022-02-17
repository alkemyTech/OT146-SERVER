create table if not exists lucas (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    age int
);

create table if not exists testimonials (
    id int not null AUTO_INCREMENT,
    name varchar(256) not null,
    image varchar(256),
    content varchar(500),
    creation_date timestamp not null,
    is_deleted bit(1) not null default 0,
    primary key(id)
);