create table if not exists lucas (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    age int
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