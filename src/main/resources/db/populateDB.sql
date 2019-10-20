DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100000, TIMESTAMP '2019-10-19 09:00', 'завтрак', 1000),
  (100000, TIMESTAMP '2019-10-19 13:20', 'обед', 800),
  (100000, TIMESTAMP '2019-10-19 20:30', 'ужин', 500),
  (100000, TIMESTAMP '2019-10-20 08:00', 'завтрак', 850),
  (100000, TIMESTAMP '2019-10-20 13:00', 'обед', 700),
  (100000, TIMESTAMP '2019-10-20 19:00', 'ужин', 420),
  (100001, TIMESTAMP '2019-10-15 13:00', 'завтрак', 700),
  (100001, TIMESTAMP '2019-10-15 14:00', 'обед', 420),
  (100001, TIMESTAMP '2019-10-15 19:00', 'ужин', 420);