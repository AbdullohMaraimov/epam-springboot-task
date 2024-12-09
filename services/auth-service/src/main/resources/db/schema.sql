CREATE TABLE auth_user (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE login_attempt (
    username VARCHAR(255) PRIMARY KEY,
    attempts INT NOT NULL,
    lockTime TIMESTAMP
);