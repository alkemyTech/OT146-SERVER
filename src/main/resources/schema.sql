create table if not exists category (
    id bigint not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    description varchar(256) null,
    image varchar(256) null,
    created_at date not null,
    updated_at date not null, 
    deleted bit not null
    
);