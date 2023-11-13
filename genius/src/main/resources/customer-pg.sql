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

drop table if exists customer.comment;
create table customer.comment
(
    id                serial
        primary key,
    content           text    not null,
    user_id           integer not null
        references customer."user",
    work_id           text    not null,
    time              timestamp default CURRENT_TIMESTAMP,
    parent_comment_id integer
        references customer.comment,
    like_count        integer   default 0
);

drop table if exists customer.comment_likes;
create table customer.comment_likes
(
    comment_id integer not null
        references customer.comment,
    user_id    integer not null
        references customer."user",
    primary key (comment_id, user_id)
);

create trigger like_count_update
    after insert or delete
    on customer.comment_likes
    for each row
execute procedure public.update_like_count();

create function update_like_count() returns trigger
    language plpgsql
as
$$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        -- 点赞时增加点赞数
        UPDATE customer.comment SET like_count = like_count + 1
        WHERE id = NEW.comment_id;
        RETURN NEW;
    ELSIF (TG_OP = 'DELETE') THEN
        -- 取消点赞时减少点赞数
        UPDATE customer.comment SET like_count = like_count - 1
        WHERE id = OLD.comment_id;
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$;


drop table if exists customer.comment_mentions;
create table customer.comment_mentions
(
    comment_id        integer not null
        references customer.comment,
    mentioned_user_id integer not null
        references customer."user",
    primary key (comment_id, mentioned_user_id)
);