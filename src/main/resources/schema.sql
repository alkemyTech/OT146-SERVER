use alkemy_ong;

create table if not exists roles (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
	description varchar(256),
    created_at timestamp not null,
    updated_at timestamp
);
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
