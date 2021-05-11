CREATE TABLE IF NOT EXISTS company(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company(id, name) values (1, 'BMW');
insert into company(id, name) values (2, 'Porsche');
insert into company(id, name) values (3, 'Audi');
insert into company(id, name) values (4, 'Lada');
insert into company(id, name) values (5, 'Toyota');
insert into company(id, name) values (6, 'Honda');

insert into person(id, name, company_id) values (1, 'Ivan', 1);
insert into person(id, name, company_id) values (2, 'Max', 1);
insert into person(id, name, company_id) values (3, 'Fedor', 1);
insert into person(id, name, company_id) values (4, 'Petr', 1);
insert into person(id, name, company_id) values (5, 'Nick', 2);
insert into person(id, name, company_id) values (6, 'Alex', 3);
insert into person(id, name, company_id) values (7, 'Ben', 3);
insert into person(id, name, company_id) values (8, 'John', 3);
insert into person(id, name, company_id) values (9, 'Stepan', 4);
insert into person(id, name, company_id) values (10, 'Andrew', 4);
insert into person(id, name, company_id) values (11, 'Dmitry', 4);
insert into person(id, name, company_id) values (12, 'Sasuke', 5);
insert into person(id, name, company_id) values (13, 'Naruto', 5);
insert into person(id, name, company_id) values (14, 'Obito', 6);
insert into person(id, name, company_id) values (15, 'Satoshi', 6);
insert into person(id, name, company_id) values (16, 'Hanzo', 6);

-- имена всех person, которые не состоят в компании с id = 5 --
select * from person where company_id != 5;

-- название компании для каждого человека --
select c.name as "Название компании", p.name as "Имя сотрудника"
from company c join person p on c.id = p.company_id;

-- Название компании с максимальным количеством человек и количество человек в этой компании --
select c.name, count(p) from person p
join company c on c.id = p.company_id
group by c.name
order by count(p) desc
fetch first row only;