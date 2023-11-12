create schema customer;

drop table if exists customer.user;
create table customer.user (
    userId serial primary key, -- auto increment
    email varchar(255) unique not null ,
    nick_name varchar(255) default null,
    password varchar(255) default null,
    person_description varchar(255) default null,
    sex int default 0, --0:未知 1:男 2:女
    join_time timestamp default current_timestamp
);

drop table if exists customer.verify_code;
create table customer.verify_code (
    id serial primary key,
    userId varchar(255),
    code varchar(255) not null,
    type int, -- 1:注册 2:找回密码 3:修改密码
    create_time timestamp default current_timestamp
);

