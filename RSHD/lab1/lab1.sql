-- psql -U s367133 -d studs -v schema_name=s367133 -f lab1.sql

\set ON_ERROR_STOP 1
SET vars.schema_name TO :'schema_name';


CREATE OR REPLACE PROCEDURE pg_temp.show_tables_with_nulls(_schema text DEFAULT 'public')
LANGUAGE plpgsql
AS $$
DECLARE
    has_null    BOOLEAN;
    counter     INT := 1;

    _table      RECORD;
    _column     RECORD;
    _sql        TEXT;

    result      TEXT := E'\nSchema: '
                    || _schema
                    || E'\n\n'
                    || format('%-3s %-25s', 'No.', 'Table name')
                    || E'\n'
                    || format('%-3s %-25s', '---', repeat('-',25))
                    || E'\n';
BEGIN
    BEGIN
        -- Проерка существования схемы
        IF NOT EXISTS (
        SELECT 1 FROM pg_namespace WHERE nspname = _schema
        ) THEN
            RAISE EXCEPTION 'Схема "%" не существует.', _schema;
        END IF;

        -- Проверка прав доступа
        IF NOT has_schema_privilege(_schema, 'USAGE') THEN
            RAISE EXCEPTION 'У пользователя "%" нет прав USAGE на схему "%".', current_user, _schema;
        END IF;

        FOR _table IN
            SELECT c.relname AS table_name
            FROM pg_class c
            JOIN pg_namespace n ON n.oid = c.relnamespace
            WHERE c.relkind = 'r'
                AND n.nspname = _schema
                AND has_table_privilege(quote_ident(n.nspname) || '.' || quote_ident(c.relname), 'SELECT')
        LOOP
            _sql := 'SELECT EXISTS (SELECT 1 FROM ' || quote_ident(_schema) || '.' || quote_ident(_table.table_name) || ' WHERE ';
            FOR _column IN
                SELECT a.attname
                FROM pg_attribute a
                WHERE a.attrelid = format('%I.%I', _schema, _table.table_name)::regclass
                  AND a.attnum > 0
                  AND NOT a.attisdropped
            LOOP
                _sql := _sql || quote_ident(_column.attname) || ' IS NULL OR ';
            END LOOP;

            -- Удаляем последний OR
            _sql := left(_sql, length(_sql) - 4) || ' LIMIT 1)';
            EXECUTE _sql INTO has_null;

            IF has_null THEN
                result := result || format('%-3s | %-50s', counter, _table.table_name) || E'\n';
                counter := counter + 1;
            END IF;

        END LOOP;
        RAISE NOTICE '%', result;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE NOTICE 'Произошла ошибка: %', SQLERRM;
            RAISE EXCEPTION 'Завершение процедуры по ошибке';
    END;
END
$$;

CALL pg_temp.show_tables_with_nulls(:'schema_name');
