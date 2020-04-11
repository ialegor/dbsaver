drop table if exists district;

create table if not exists district
(
    id         int8        not null primary key,
    name       text        not null,
    square     float8      not null,
    population int4        not null,
    density    float4      not null,
    cities     int4        null,
    rurals     int4        null,
    center     varchar(50) not null,
    since      date        not null,
    created    timestamp   not null
);

insert into district
values (1, 'Бокситогорский муниципальный район', 7201.7, 49256, 7.27, 3, 6, 'Бокситогорск', '1952-07-25', now());
insert into district
values (2, 'Волосовский муниципальный район', 2680.5, 51668, 18.96, 1, 15, 'Волосово', '1927-08-01', now());
insert into district
values (3, 'Волховский муниципальный район', 5124.6, 89070, 18.53, 3, 12, 'Волхов', '1927-09-01', now());
insert into district
values (4, 'Всеволожский муниципальный район', 3036.4, 398896, 132, 9, 10, 'Всеволожск', '1936-01-01', now());
insert into district
values (5, 'Выборгский муниципальный район', 7431.2, 199571, 27.68, 7, 5, 'Выборг', '1940-01-01', now());
insert into district
values (6, 'Гатчинский муниципальный район', 2891.8, 243156, 83.55, 6, 11, 'Гатчина', '1927-09-01', now());
insert into district
values (7, 'Кингисеппский муниципальный район', 2908.1, 76182, 27.24, 2, 9, 'Кингисепп', '1927-09-01', now());
insert into district
values (8, 'Киришский муниципальный район', 3045.3, 62069, 21.34, 2, 4, 'Кириши', '1931-09-01', now());
insert into district
values (9, 'Кировский муниципальный район', 2590.5, 105936, 40.41, 8, 3, 'Кировск', '1977-04-01', now());
insert into district
values (10, 'Лодейнопольский муниципальный район', 4910.9, 28530, 6.13, 2, 3, 'Лодейное Поле', '1927-09-01', now());
insert into district
values (11, 'Ломоносовский муниципальный район', 1919.2, 73475, 36.44, 4, 11, 'Ломоносов', '1927-08-01', now());
insert into district
values (12, 'Лужский муниципальный район', 6006.4, 72035, 12.84, 2, 13, 'Луга', '1927-08-01', now());
insert into district
values (13, 'Подпорожский муниципальный район', 7705.5, 28263, 4.03, 4, 1, 'Подпорожье', '1927-08-01', now());
insert into district
values (14, 'Приозерский муниципальный район', 3597.0, 61028, 17.56, 2, 12, 'Приозерск', '1940-01-01', now());
insert into district
values (15, 'Сланцевский муниципальный район', 2191.1, 42494, 19.88, 1, 6, 'Сланцы', '1949-01-01', now());
insert into district
values (16, 'Сосновоборский городской округ', 72.0, 68344, 930.1, null, null, 'Сосновый Бор', '2006-01-01', now());
insert into district
values (17, 'Тихвинский муниципальный район', 7017.7, 69567, 10.12, 1, 8, 'Тихвин', '1927-09-01', now());
insert into district
values (18, 'Тосненский муниципальный район', 3601.9, 128327, 35.88, 7, 6, 'Тосно', '1930-08-19', now());
