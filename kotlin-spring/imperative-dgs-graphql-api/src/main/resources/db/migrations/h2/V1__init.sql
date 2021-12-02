CREATE TABLE shows (
    id IDENTITY PRIMARY KEY,
    title varchar(100) NOT NULl,
    release_year int NOT NULL
);

CREATE TABLE ratings (
    id IDENTITY PRIMARY KEY,
    rate_value int default 0,
    comment varchar(1000),
    show_id bigint,
    FOREIGN KEY (show_id) references shows(id)
);

insert into shows (title, release_year) values ('The great show', 2001);
insert into shows (title, release_year) values ('The voice', 2005);
insert into shows (title, release_year) values ('Factor X', 1998);
insert into shows (title, release_year) values ('Rick & Morty', 2010);

insert into ratings (rate_value, comment, show_id) values (5, null, 1);
insert into ratings (rate_value, comment, show_id) values (4, null, 1);

insert into ratings (rate_value, comment, show_id) values (5, null, 2);
insert into ratings (rate_value, comment, show_id) values (3, null, 2);
insert into ratings (rate_value, comment, show_id) values (1, null, 2);
insert into ratings (rate_value, comment, show_id) values (1, null, 2);

insert into ratings (rate_value, comment, show_id) values (5, null, 3);
insert into ratings (rate_value, comment, show_id) values (4, null, 3);
insert into ratings (rate_value, comment, show_id) values (1, null, 3);

insert into ratings (rate_value, comment, show_id) values (4, null, 4);