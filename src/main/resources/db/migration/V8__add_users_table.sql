CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  first_name VARCHAR(45) NOT NULL,
  surname VARCHAR(45) NOT NULL,
  email VARCHAR(254) NOT NULL,
  password VARCHAR(45) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
);

INSERT INTO users(username,first_name, surname, email, password,enabled)
VALUES ('joe','joe', 'brewer','joe@brewlog.com','password', true);

INSERT INTO users(username,first_name, surname, email, password,enabled)
VALUES ('admin','admin','admin','admin@brewlog.com','admin', true);

INSERT INTO user_roles (username, role)
VALUES ('joe', 'ROLE_USER');

INSERT INTO user_roles (username, role)
VALUES ('admin', 'ROLE_USER');

INSERT INTO user_roles (username, role)
VALUES ('admin', 'ROLE_ADMIN');

