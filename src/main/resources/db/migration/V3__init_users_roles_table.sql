 DROP TABLE IF EXISTS users_roles;
 CREATE TABLE users_roles (
    user_id INT UNSIGNED,
    role_id INT UNSIGNED,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
 )