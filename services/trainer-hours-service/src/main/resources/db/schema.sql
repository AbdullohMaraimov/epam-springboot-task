CREATE TABLE training_hour (
                               id INT AUTO_INCREMENT NOT NULL,
                               first_name VARCHAR(255),
                               is_active BOOLEAN NOT NULL,
                               last_name VARCHAR(255),
                               trainer_username VARCHAR(255),
                               PRIMARY KEY (id)
);

CREATE TABLE training_year (
                               id INT AUTO_INCREMENT NOT NULL,
                               total_duration DOUBLE,
                               training_hour_id INT,
                               training_year INT,
                               PRIMARY KEY (id),
                               FOREIGN KEY (training_hour_id) REFERENCES training_hour(id)
);

CREATE TABLE training_month (
                                id BIGINT AUTO_INCREMENT NOT NULL,
                                duration_in_hour DOUBLE,
                                training_month VARCHAR(20),
                                training_year_id INT,
                                PRIMARY KEY (id),
                                FOREIGN KEY (training_year_id) REFERENCES training_year(id)
);
