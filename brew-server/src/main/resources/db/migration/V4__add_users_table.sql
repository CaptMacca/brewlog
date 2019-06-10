CREATE  TABLE users (
  id bigint auto_increment primary key,
  username VARCHAR(50) NOT NULL UNIQUE ,
  first_name VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1

);

CREATE TABLE roles (
  id bigint auto_increment primary key,
  role_name varchar(60) NOT NULL
);

CREATE TABLE user_roles (
  user_id bigint NOT NULL ,
  role_id bigint NOT NULL ,
  UNIQUE KEY uni_user_roles (user_id, role_id),
  KEY fk_userrole_idx (user_id, role_id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id)
);


