create table Users (
    id serial primary key,
    login text,
    password text,
    role int not null
);

create table Auth_tokens (
    id serial primary key,
    user_id int references Users(id),
    token text,
    time timestamp
);

create table Location (
    id serial primary key,
    x bigint not null,
    y bigint,
    z bigint not null,
    name text not null check ( name!='')
);

create table Coordinates (
    id serial primary key,
    x int not null ,
    y int not null check (y > -761)
);

create table Route (
    id serial primary key,
    name text not null check ( name!=''),
    coordinates_id int references Coordinates(id) not null,
    creationDate timestamp not null,
    from_id int references Location(id) not null,
    to_id int references Location(id) not null,
    distance numeric not null check (distance > 1),
    rating int not null check (rating > 0),
    owner int references Users(id)
);

-- -- Рассчитать сумму значений поля rating для всех объектов.
-- CREATE OR REPLACE FUNCTION calculate_total_rating()
--     RETURNS integer AS $$
-- BEGIN
--     RETURN (SELECT SUM(rating) FROM Route);
-- END;
-- $$;
--
-- -- Вернуть один (любой) объект, значение поля to которого является максимальным.
-- CREATE OR REPLACE FUNCTION get_route_with_max_to()
--     RETURNS Route AS $$
-- BEGIN
--     RETURN (SELECT * FROM Route ORDER BY to_id DESC LIMIT 1);
-- END;
-- $$;
--
-- -- Вернуть массив объектов, значение поля rating которых больше заданного.
-- CREATE OR REPLACE FUNCTION get_routes_with_rating_greater_than(rating_threshold integer)
--     RETURNS SETOF Route AS $$
-- BEGIN
--     RETURN QUERY SELECT * FROM Route WHERE rating > rating_threshold;
-- END;
-- $$;
--
-- -- Найти все маршруты между указанными пользователем локациями, отсортировать список по заданному параметру.
-- CREATE OR REPLACE FUNCTION find_routes_between_locations(from_location_id integer, to_location_id integer, sort_field text)
--     RETURNS SETOF Route AS $$
-- BEGIN
--     RETURN QUERY SELECT * FROM Route
--                  WHERE from_id = from_location_id AND to_id = to_location_id
--                  ORDER BY sort_field;
-- END;
-- $$;
--
-- -- Добавить новый маршрут между указанными пользователем локациями.
-- CREATE OR REPLACE FUNCTION add_new_route(
--     name text,
--     coordinates_id int,
--     creation_date timestamp,
--     from_location_id int,
--     to_location_id int,
--     distance numeric,
--     rating int,
--     owner_id int
-- )
--     RETURNS void AS $$
-- BEGIN
--     INSERT INTO Route (name, coordinates, creationDate, from_id, to_id, distance, rating, owner)
--     VALUES (name, coordinates_id, creation_date, from_location_id, to_location_id, distance, rating, owner_id);
-- END;
-- $$;