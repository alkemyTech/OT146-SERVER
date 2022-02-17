create table if not exists category (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    description varchar(256) null,
    image varchar(256) null,

);