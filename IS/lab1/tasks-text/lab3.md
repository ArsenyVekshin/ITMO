# Информационные системы. ЛР №2
   

----

### Векшин Арсений P3316
#### Вариант: 1600
#### portbase: 32810
Стырил - зафолловься

----

### [frontend](https://github.com/ArsenyVekshin/ITMO/tree/master/IS/lab1/lab1_frontend)   
Запуск отдельно: `npm start`  
Запуск сборки: `npm run build`  


### [backend](https://github.com/ArsenyVekshin/ITMO/tree/master/IS/lab1/lab1_backend)   
Запуск: `mvn spring-boot:run`  
Запуск сборки jar: `mvn clean package`

----

### [Условие ЛР2](https://github.com/ArsenyVekshin/ITMO/tree/master/IS/lab1/tasks-text/lab2.md)   
### Доработать ИС из ЛР2 следующим образом:

Реализовать сохранение загруженных на сервер файлов, используемых для импорта данных, в файловом хранилище MinIO (можно взять любое другое S3-совместимое хранилище). Поднять и настроить MinIO требуется самостоятельно. Загруженные файлы должны быть доступны для скачивания из таблицы с логом импорта.

Сохранение загруженных файлов в файловом хранилище должно быть реализовано транзакционно по отношению к операциям, реализующим непосредственную вставку объектов в БД при импорте.

Для реализации распределенной транзакции из пункта 2 разрешается использовать любые инструменты. Рекомендуется решать задачу при помощи собственной реализации двух фазного коммита.
    
Необходимо на защите быть готовым продемонстрировать корректность реализованной распределенной транзакции в следующих условиях:
 - отказ файлового хранилища (БД продолжает работать)
 - отказ БД (файловое хранилище продолжает работать)
 - ошибка в бизнес-логике сервера (работают и БД, и файловое хранилище, однако в коде сервера вылетает RuntimeException между запросами в разные источники данных)
 
Необходимо на защите быть готовым продемонстрировать корректность работы распределенной транзакции в условиях параллельных запросов от нескольких пользователей (реализованный в ЛР 2 сценарий для Apache JMeter, тестирующий функцию импорта, должен продолжать корректно отрабатывать).лжен проверять корректность соблюдения системой ограничений уникальности предметной области при одновременной попытке нескольких пользователей создать объект с одним и тем же уникальным значением.