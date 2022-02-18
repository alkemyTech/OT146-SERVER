create table if not exists lucas (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    age int)

create table if not exists activities (
    id int not null primary key AUTO_INCREMENT,
    name  varchar (256)not null,
    content  varchar(256) not null,
    image  varchar(256) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP ,
    deleted  bit(1) not null default = 0)