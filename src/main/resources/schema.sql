create table if not exists news (
    id int not null primary key AUTO_INCREMENT,
    name VARCHAR(256) NOT NULL,
    content VARCHAR(256) NOT NULL,
    image VARCHAR(256) NOT NULL,
    category_id INT NOT NULL,
    creation_date TIMESTAMP,
    modification_date TIMESTAMP,
    deleted bit(1) NOT NULL default 0
    --   FOREIGN KEY (category_id) REFERENCES Category(id)
);