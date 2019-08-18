CREATE DATABASE city_repository;

CREATE SCHEMA city_storage;

CREATE TABLE city
(
  id          BIGSERIAL PRIMARY KEY,
  name        VARCHAR(50) NOT NULL UNIQUE,
  information TEXT        NOT NULL
);

INSERT INTO city_repository.city_storage.city(name, information)
VALUES ('москва', 'Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))'),
       ('минск',
        'Площадь Независимости - самая большая площадь Минска, окруженная известными достопримечательностями.'),
       ('париж', 'Вам обязательно надо подняться на Эйфелеву башню, чтобы насладиться прекрасным видом на Париж'),
       ('берлин',
        'Бранденбургские ворота - настоящая визитная карточка города, также являются одним из символов воссоединения Западного и Восточного Берлина.');