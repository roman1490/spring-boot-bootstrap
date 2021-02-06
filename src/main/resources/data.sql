INSERT INTO users (id, age, email, first_name, last_name, password) VALUES (1, 25, 'admin@admin.com', 'admin', 'admin', 'admin');

INSERT INTO roles (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, role) VALUES (2, 'ROLE_USER');

INSERT INTO users_roles (user_id, roles_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES (1, 2);