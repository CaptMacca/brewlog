INSERT INTO brews.users (id, first_name, surname, username, email, password ) VALUES (1,'Test','User','testuser','test_user@brews.com','password1234');
SELECT setval(pg_get_serial_sequence('brews.users', 'id'), (SELECT MAX(id) FROM brews.users));
