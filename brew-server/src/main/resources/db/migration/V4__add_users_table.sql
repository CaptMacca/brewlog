CREATE TABLE brews.users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE ,
  first_name VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL ,
  enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE brews.roles (
  id BIGSERIAL PRIMARY KEY,
  role_name VARCHAR(60) NOT NULL
);

CREATE TABLE brews.user_roles (
  user_id BIGINT NOT NULL ,
  role_id BIGINT NOT NULL ,
  UNIQUE  (user_id, role_id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES brews.users (id),
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES brews.roles (id)
);

CREATE UNIQUE INDEX fk_userrole_idx ON brews.user_roles (user_id, role_id);


