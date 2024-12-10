-- Рассчитать сумму значений поля rating для всех объектов.
CREATE OR REPLACE FUNCTION calculate_total_rating()
RETURNS INTEGER AS $$
BEGIN
 RETURN (SELECT SUM(rating) FROM route);
END;
$$ LANGUAGE plpgsql;

-- Вернуть один (любой) объект, значение поля to которого является максимальным
CREATE OR REPLACE FUNCTION find_max_to()
RETURNS TABLE (
 -- Определить столбцы результата, соответствующие структуре таблицы Route
 id BIGINT,
 from_id BIGINT,
 to_id BIGINT,
 rating INTEGER
 -- ... other columns
) AS $$
BEGIN
 RETURN QUERY SELECT * FROM Route ORDER BY to_id DESC LIMIT 1;
END;
$$ LANGUAGE plpgsql;

-- Вернуть массив объектов, значение поля rating которых больше заданного.
CREATE OR REPLACE FUNCTION find_routes_with_greater_rating(rating_value INTEGER)
RETURNS TABLE (
 -- Определить столбцы результата, соответствующие структуре таблицы Route
 id BIGINT,
 from_id BIGINT,
 to_id BIGINT,
 rating INTEGER
 -- ... other columns
) AS $$
BEGIN
 RETURN QUERY SELECT * FROM Route WHERE rating > rating_value;
END;
$$ LANGUAGE plpgsql;

-- Найти все маршруты между указанными пользователем локациями, отсортировать список по заданному параметру.
CREATE OR REPLACE FUNCTION find_all_routes_by(from_location_id BIGINT, to_location_id BIGINT)
RETURNS TABLE (
 -- Определить столбцы результата, соответствующие структуре таблицы Route
 id BIGINT,
 from_id BIGINT,
 to_id BIGINT,
 rating INTEGER
 -- ... other columns
) AS $$
BEGIN
 RETURN QUERY SELECT * FROM Route WHERE from_id = from_location_id AND to_id = to_location_id;
END;
$$ LANGUAGE plpgsql;
