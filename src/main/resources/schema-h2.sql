DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE cafes IF EXISTS;
DROP TABLE cafe_votes IF EXISTS;
DROP TABLE meals IF EXISTS;
DROP SEQUENCE USER_SEQ IF EXISTS;
DROP SEQUENCE CAFE_START_SEQ IF EXISTS;

CREATE SEQUENCE USER_SEQ START WITH 100000;
CREATE SEQUENCE CAFE_START_SEQ START WITH 1;

CREATE TABLE users
(
    id               INTEGER DEFAULT USER_SEQ.nextval PRIMARY KEY,
    email            VARCHAR(255)            NOT NULL,
    password         VARCHAR(255)            NOT NULL,
    enabled          BOOLEAN DEFAULT TRUE    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON users (email);

CREATE TABLE cafes
(
    id               INTEGER DEFAULT CAFE_START_SEQ.nextval PRIMARY KEY,
    name             VARCHAR(255)            NOT NULL,
    date             TIMESTAMP DEFAULT now() NOT NULL
);

CREATE TABLE MEALS
(
    name             VARCHAR(255)            NOT NULL,
    price            DOUBLE                  NOT NULL,
    cafe_id          INTEGER                 NOT NULL,
    FOREIGN KEY (cafe_id) REFERENCES cafes (id) ON DELETE CASCADE
);

CREATE TABLE cafe_votes
(
    cafe_id         INTEGER               NOT NULL REFERENCES cafes (id),
    user_id         INTEGER               NOT NULL REFERENCES users (id),
    PRIMARY KEY (cafe_id, user_id)
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);