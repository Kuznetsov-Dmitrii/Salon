-- Дропаем таблицы, если они уже существуют
DROP TABLE IF EXISTS work_hours_monday;
DROP TABLE IF EXISTS work_hours_tuesday;
DROP TABLE IF EXISTS work_hours_wednesday;
DROP TABLE IF EXISTS work_hours_thursday;
DROP TABLE IF EXISTS work_hours_friday;
DROP TABLE IF EXISTS hairdresser_work;

-- Создаем основную таблицу для работы парикмахеров
CREATE TABLE hairdresser_work (
    hairdresser_id VARCHAR(255) NOT NULL,
    first_day_of_week DATE NOT NULL,
    PRIMARY KEY (hairdresser_id, first_day_of_week),
    FOREIGN KEY (hairdresser_id) REFERENCES user_entity(id)
);

-- Создаем таблицу для рабочих часов по понедельникам
CREATE TABLE work_hours_monday (
    work_id_hairdresser_id VARCHAR(255) NOT NULL,
    work_id_first_day_of_week DATE NOT NULL,
    work_hour TIMESTAMP WITHOUT TIME ZONE,
    PRIMARY KEY (work_id_hairdresser_id, work_id_first_day_of_week, work_hour),
    FOREIGN KEY (work_id_hairdresser_id, work_id_first_day_of_week) REFERENCES hairdresser_work(hairdresser_id, first_day_of_week)
);

-- Создаем таблицу для рабочих часов по вторникам
CREATE TABLE work_hours_tuesday (
    work_id_hairdresser_id VARCHAR(255) NOT NULL,
    work_id_first_day_of_week DATE NOT NULL,
    work_hour TIMESTAMP WITHOUT TIME ZONE,
    PRIMARY KEY (work_id_hairdresser_id, work_id_first_day_of_week, work_hour),
    FOREIGN KEY (work_id_hairdresser_id, work_id_first_day_of_week) REFERENCES hairdresser_work(hairdresser_id, first_day_of_week)
);

-- Создаем таблицу для рабочих часов по средам
CREATE TABLE work_hours_wednesday (
    work_id_hairdresser_id VARCHAR(255) NOT NULL,
    work_id_first_day_of_week DATE NOT NULL,
    work_hour TIMESTAMP WITHOUT TIME ZONE,
    PRIMARY KEY (work_id_hairdresser_id, work_id_first_day_of_week, work_hour),
    FOREIGN KEY (work_id_hairdresser_id, work_id_first_day_of_week) REFERENCES hairdresser_work(hairdresser_id, first_day_of_week)
);

-- Создаем таблицу для рабочих часов по четвергам
CREATE TABLE work_hours_thursday (
    work_id_hairdresser_id VARCHAR(255) NOT NULL,
    work_id_first_day_of_week DATE NOT NULL,
    work_hour TIMESTAMP WITHOUT TIME ZONE,
    PRIMARY KEY (work_id_hairdresser_id, work_id_first_day_of_week, work_hour),
    FOREIGN KEY (work_id_hairdresser_id, work_id_first_day_of_week) REFERENCES hairdresser_work(hairdresser_id, first_day_of_week)
);

-- Создаем таблицу для рабочих часов по пятницам
CREATE TABLE work_hours_friday (
    work_id_hairdresser_id VARCHAR(255) NOT NULL,
    work_id_first_day_of_week DATE NOT NULL,
    work_hour TIMESTAMP WITHOUT TIME ZONE,
    PRIMARY KEY (work_id_hairdresser_id, work_id_first_day_of_week, work_hour),
    FOREIGN KEY (work_id_hairdresser_id, work_id_first_day_of_week) REFERENCES hairdresser_work(hairdresser_id, first_day_of_week)
);