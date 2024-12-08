
CREATE TABLE training_type (
                               id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                               name VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE users (
                       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       username VARCHAR(50),
                       password VARCHAR(50),
                       is_active BOOLEAN
);


CREATE TABLE trainee (
                         id BIGINT PRIMARY KEY,
                         date_of_birth DATE,
                         address VARCHAR(100),
                         CONSTRAINT fk_users_trainee FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE trainer (
                         id BIGINT PRIMARY KEY,
                         specialization_id BIGINT,
                         CONSTRAINT fk_users_trainer FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE,
                         CONSTRAINT fk_training_type FOREIGN KEY (specialization_id) REFERENCES training_type(id) ON DELETE CASCADE
);


CREATE TABLE training (
                          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          trainee_id BIGINT NOT NULL,
                          trainer_id BIGINT NOT NULL,
                          training_name VARCHAR(50),
                          training_type_id BIGINT,
                          training_date DATE,
                          duration DECIMAL,
                          CONSTRAINT fk_trainee_training FOREIGN KEY (trainee_id) REFERENCES trainee(id) ON DELETE CASCADE,
                          CONSTRAINT fk_trainer_training FOREIGN KEY (trainer_id) REFERENCES trainer(id) ON DELETE CASCADE,
                          CONSTRAINT fk_training_type_training FOREIGN KEY (training_type_id) REFERENCES training_type(id) ON DELETE CASCADE
);


CREATE TABLE trainee_trainer (
                                 trainee_id BIGINT NOT NULL,
                                 trainer_id BIGINT NOT NULL,
                                 PRIMARY KEY (trainee_id, trainer_id),
                                 CONSTRAINT fk_trainee FOREIGN KEY (trainee_id) REFERENCES trainee(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_trainer FOREIGN KEY (trainer_id) REFERENCES trainer(id) ON DELETE CASCADE
);
