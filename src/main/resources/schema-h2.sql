DROP TABLE USER_ROLES IF EXISTS;
DROP TABLE USERS IF EXISTS;
DROP TABLE SPRING_SESSION IF EXISTS;
DROP TABLE SPRING_SESSION_ATTRIBUTES IF EXISTS;
-- DROP TABLE usr IF EXISTS;
DROP SEQUENCE USER_SEQ IF EXISTS;

-- CREATE SEQUENCE USER_SEQ START WITH 100000;
CREATE SEQUENCE CAFE_SEQ START WITH 1;

-- CREATE TABLE users
-- (
--     id               INTEGER DEFAULT USER_SEQ.nextval PRIMARY KEY,
--     name             VARCHAR(255)            NOT NULL,
--     email            VARCHAR(255)            NOT NULL,
--     password         VARCHAR(255)            NOT NULL,
--     is_vote          BOOLEAN DEFAULT FALSE   NOT NULL
-- );
-- CREATE UNIQUE INDEX users_unique_email_idx
--     ON USERS (email);

CREATE TABLE CAFES
(
    id               INTEGER DEFAULT CAFE_SEQ.nextval PRIMARY KEY,
    name             VARCHAR(255)            NOT NULL,
    rating           INTEGER DEFAULT 0       NOT NULL,
    date             TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX cafes_unique_name_idx
    ON CAFES (name);

-- CREATE TABLE user_roles
-- (
--     user_id INTEGER NOT NULL,
--     role    VARCHAR(255),
--     CONSTRAINT user_roles_idx UNIQUE (user_id, role),
--     FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
-- );

create table USR
(
    ID         VARCHAR(255) not null
        primary key,
    EMAIL      VARCHAR(255),
    LAST_VISIT TIMESTAMP(6, 0),
    LOCALE     VARCHAR(255),
    NAME       VARCHAR(255),
    USERPIC    VARCHAR(255)
);