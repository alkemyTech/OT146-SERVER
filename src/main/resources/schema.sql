create table if not exists category (
    id bigint not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    description varchar(256),
    image varchar(256),
    created_at date,
    updated_at date, 
    deleted bit not null
    
);