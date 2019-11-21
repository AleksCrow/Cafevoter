-- DELETE FROM user_roles;
DELETE FROM cafes;
-- DELETE FROM users;
-- ALTER SEQUENCE USER_SEQ RESTART WITH 100000;

-- INSERT INTO users (name, email, password) VALUES
-- ('User', 'user@yandex.ru', 'password'),
-- ('Admin', 'admin@gmail.com', 'admin');

-- INSERT INTO user_roles (role, user_id) VALUES
-- ('ROLE_USER', 100000),
-- ('ROLE_ADMIN', 100001);

INSERT INTO cafes (name, rating, date) VALUES
('Челентано', 0, '2019-11-10 10:00:00'),
('Пузата хата', 0, '2019-11-10 10:00:00'),
('Пьяная Вишня', 0, '2019-11-10 10:00:00'),
('Сушия', 0, '2019-11-10 10:00:00'),
('Круасан-Кафе', 0, '2019-11-10 10:00:00');

