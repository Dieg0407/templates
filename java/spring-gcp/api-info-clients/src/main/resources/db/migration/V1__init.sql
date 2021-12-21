CREATE TABLE clients(
    id INT auto_increment primary key,
    name varchar(20) not null,
    last_name varchar(30) not null,
    age int not null
);


insert into clients (name, last_name, age) values ('Diego', 'Pastor', 23);
insert into clients (name, last_name, age) values ('Alejandro', 'Guerrero', 18);