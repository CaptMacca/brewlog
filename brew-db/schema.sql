CREATE DATABASE brewdb;
CREATE USER springboot WITH PASSWORD 'springboot';
GRANT ALL PRIVILEGES ON DATABASE brewdb to springboot;

-- \connect brewdb

-- CREATE TABLE public.users
-- (
--     id serial NOT NULL,
--     username text NOT NULL,
--     password text NOT NULL,
--     expiration_time timestamp without time zone,
--     account_locked boolean NOT NULL DEFAULT false,
--     CONSTRAINT users_pkey PRIMARY KEY (id),
--     CONSTRAINT users_username_key UNIQUE (username)
-- );
-- ALTER TABLE public.users
--     OWNER to springboot;