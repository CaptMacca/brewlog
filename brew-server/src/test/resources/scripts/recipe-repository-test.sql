INSERT INTO brews.users (id, first_name, surname, username, email, password ) VALUES (1,'Test','User','testuser','test_user@brews.com','password');
SELECT setval(pg_get_serial_sequence('brews.users', 'id'), (SELECT MAX(id) FROM brews.users));


INSERT INTO brews.recipe (id, name, user_id) VALUES (1,'Test Recipe', 1);
INSERT INTO brews.recipe (id, name, user_id) VALUES (2,'Test Recipe 2', 1);

UPDATE brews.recipe set notes = 'Test Notes' WHERE id = 1;

SELECT setval(pg_get_serial_sequence('brews.recipe', 'id'), (SELECT MAX(id) FROM brews.recipe));