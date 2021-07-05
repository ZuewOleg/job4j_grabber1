create table meetings(
id serial primary key,
name text
);

create table users(
id serial primary key,
name varchar(255)
);

create table meetings_users(
id serial primary key,
meetings_id int references meetings(id),
users_id int references users(id),
status bool
);

insert into users(name) values ('Oleg');
insert into users(name) values ('Maks');
insert into users(name) values ('Ivan');
insert into users(name) values ('Alex');
insert into users(name) values ('Petr');

insert into meetings(name) values ('Маркетинг');
insert into meetings(name) values ('Разработка');
insert into meetings(name) values ('Снабжение');
insert into meetings(name) values ('Дизайн');
insert into meetings(name) values ('Дизайн2');

insert into meetings_users(meetings_id, users_id, status) values (1, 2, false);
insert into meetings_users(meetings_id, users_id, status) values (1, 5, true);
insert into meetings_users(meetings_id, users_id, status) values (2, 1, true);
insert into meetings_users(meetings_id, users_id, status) values (2, 2, true);
insert into meetings_users(meetings_id, users_id, status) values (3, 3, false);
insert into meetings_users(meetings_id, users_id, status) values (3, 4, true);
insert into meetings_users(meetings_id, users_id, status) values (4, 3, true);
insert into meetings_users(meetings_id, users_id, status) values (4, 4, false);

select * from users;
select * from meetings;
select * from meetings_users;

-- запрос, который получит список всех пользователей с положительными явками
select u.name, m.name, mu.status from meetings_users mu left join meetings m on mu.meetings_id = m.id
left join users u on mu.users_id = u.id
where mu.status = true;

-- запрос, который получит список всех заяков и количество подтвердивших участников
select  m.name as "Совещания", count(mu.status) as "Кол-во подтвердивших" from meetings m left join meetings_users mu on mu.meetings_id = m.id
group by mu.status, m.name
having mu.status = true;

-- все совещания, где не было ни одной заявки на посещения
select m.name as "Совещания без посещений" from meetings m left join meetings_users mu on mu.meetings_id = m.id
group by mu.status, m.name
having count(mu.status) = 0;