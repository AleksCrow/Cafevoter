DELETE FROM user_roles;
DELETE FROM cafes;
DELETE FROM users;
ALTER SEQUENCE USER_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('Admin', 'admin@gmail.com', 'admin'),
('User1', 'user@yandex.ru', '123'),
('User2', 'user@gmail.com', '123');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100000),
('ROLE_USER', 100001),
('ROLE_USER', 100002);

INSERT INTO cafes (name, date) VALUES
('Челентано', '2019-11-10 10:00:00'),
('Пузата хата', '2019-11-10 10:00:00'),
('Пьяная Вишня', '2019-11-10 10:00:00'),
('Сушия', '2019-11-10 10:00:00'),
('Круасан-Кафе', '2019-11-10 10:00:00');

INSERT INTO CAFE_VOTES (CAFE_ID, USER_ID) VALUES
(1, 100001),
(1, 100002),
(4, 100000);

-- INSERT  INTO meals (name, price, cafe_id) VALUES
-- ('Пицца', 60.00, 1 ),
-- ('Блинчики', 25.00, 1 ),
-- ('Борщ', 36.00, 2 ),
-- ('Солянка', 40.00, 2),
-- ('Коньяк с вишнями', 25.00, 3),
-- ('Коньяк с апельсинами', 30.00, 3),
-- ('Суши с рыбой', 34.00, 4),
-- ('Суши с овощами', 28.00, 4),
-- ('Круасан с шоколадом', 35.00, 5),
-- ('Круасан большой', 45.00, 5);


