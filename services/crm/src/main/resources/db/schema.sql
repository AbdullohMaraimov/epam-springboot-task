create table training_type (
                               id bigint generated always as identity primary key,
                               name varchar(50) not null unique
);

create table users (
                       id bigint generated always as identity primary key,
                       first_name varchar(50),
                       last_name varchar(50),
                       username varchar(50),
                       password varchar(50),
                       is_active boolean
);

create table trainee (
                         id bigint primary key,
                         date_of_birth date,
                         address varchar(100),
                         foreign key (id) references users(id) on delete cascade
);

create table trainer (
                         id bigint primary key,
                         specialization_id bigint,
                         foreign key (id) references users(id) on delete cascade,
                         foreign key (specialization_id) references training_type(id) on delete cascade
);

create table training (
                          id bigint generated always as identity primary key,
                          trainee_id bigint not null,
                          trainer_id bigint not null,
                          training_name varchar(50),
                          training_type_id bigint,
                          training_date date,
                          duration decimal,
                          foreign key (trainee_id) references trainee(id) on delete cascade,
                          foreign key (trainer_id) references trainer(id) on delete cascade,
                          foreign key (training_type_id) references training_type(id) on delete cascade
);

create table trainee_trainer (
                                 trainee_id bigint not null,
                                 trainer_id bigint not null,
                                 primary key (trainee_id, trainer_id),
                                 foreign key (trainee_id) references trainee(id) on delete cascade,
                                 foreign key (trainer_id) references trainer(id) on delete cascade
);