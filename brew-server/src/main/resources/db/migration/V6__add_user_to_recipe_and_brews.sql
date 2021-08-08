ALTER TABLE brews.recipe
  ADD COLUMN user_id BIGINT NOT NULL;

ALTER TABLE brews.recipe
  ADD CONSTRAINT users_recipe_fk
    FOREIGN KEY (user_id) REFERENCES brews.users (id);

ALTER TABLE brews.brew
  ADD COLUMN user_id BIGINT NOT NULL;

ALTER TABLE brews.brew
  ADD CONSTRAINT users_brew_fk
    FOREIGN KEY (user_id) REFERENCES brews.users (id);

