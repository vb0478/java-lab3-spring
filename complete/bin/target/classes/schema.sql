create table students
(
    id integer not null AUTO_INCREMENT,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    age integer not null,
    email_address varchar(255) not null,
    primary key(id)
);