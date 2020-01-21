DELETE FROM user_roles;
DELETE FROM restaurants;
DELETE FROM users;
DELETE FROM restaurant_votes;
ALTER SEQUENCE USER_SEQ RESTART WITH 100000;

INSERT INTO users (email, password) VALUES
('admin@gmail.com', 'admin'),
('user@gmail.com', '123'),
('user@yandex.ru', '123');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100000),
('ROLE_USER', 100001),
('ROLE_USER', 100002);

INSERT INTO restaurants (name, date) VALUES
('El Celler de Can Roca', '2019-11-10'),
('White Rabbit', '2019-11-10'),
('Peter Luger', '2019-11-10'),
('Can Majo', '2019-11-10'),
('Maní', '2019-11-10'),
('Sukiyabashi Jirō', '2020-01-01'),
('Sierra Mar', now()),
('D.O.M.', now()),
('Ithaa', now()),
('360 Istanbul East', now()),
('Blindekuh', now());

INSERT INTO restaurant_votes (restaurant_id, user_id) VALUES
(1, 100001),
(1, 100002),
(6, 100000),
(7, 100001),
(8, 100002),
(10, 100002),
(9, 100000),
(9, 100001),
(4, 100000);

INSERT  INTO meals (name, price, restaurant_id) VALUES
('Пицца', 60.00, 1 ),
('Блинчики', 25.00, 1 ),
('Борщ', 36.00, 2 ),
('Солянка', 40.00, 2),
('Коньяк с вишнями', 25.00, 3),
('Коньяк с апельсинами', 30.00, 3),
('Суши с рыбой', 34.00, 4),
('Суши с овощами', 28.00, 4),
('Круасан с шоколадом', 35.00, 5),
('Круасан большой', 45.00, 5);


