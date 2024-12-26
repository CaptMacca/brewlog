INSERT INTO brews.users (id, first_name, surname, username, email, password ) VALUES (1,'Test','User','testuser','test_user@brews.com','password');
SELECT setval(pg_get_serial_sequence('brews.users', 'id'), (SELECT MAX(id) FROM brews.users));


INSERT INTO brews.recipe (id, name, user_id) VALUES (1,'Test Recipe', 1);
INSERT INTO brews.recipe (id, name, user_id) VALUES (2,'Test Recipe 2', 1);

UPDATE brews.recipe set notes = 'Test Notes' WHERE id = 1;

INSERT INTO brews.brew (id, recipe_id, brew_date, user_id, notes, tasting_notes) VALUES (1, 1, '2020-01-01','1', 'Test Notes', 'Test Tasting Notes');
INSERT INTO brews.brew (id, recipe_id, brew_date, user_id) VALUES (2, 1, '2021-01-02', '1');
INSERT INTO brews.brew (id, recipe_id, brew_date, user_id) VALUES (3, 1, '2024-01-02', '1');
INSERT INTO brews.brew (id, recipe_id, brew_date, user_id) VALUES (4, 1, '2024-02-02', '1');
INSERT INTO brews.brew (id, recipe_id, brew_date, user_id) VALUES (5, 2, '2024-02-02', '1');
INSERT INTO brews.brew (id, recipe_id, brew_date, user_id) VALUES (6, 2, '2024-05-02', '1');

SELECT setval(pg_get_serial_sequence('brews.recipe', 'id'), (SELECT MAX(id) FROM brews.recipe));
SELECT setval(pg_get_serial_sequence('brews.brew', 'id'), (SELECT MAX(id) FROM brews.brew));