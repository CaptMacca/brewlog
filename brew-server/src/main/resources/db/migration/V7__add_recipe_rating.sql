ALTER TABLE brews.recipe
    ADD COLUMN rating SMALLINT;

CREATE INDEX recipe_rating_idx ON brews.recipe
(
  rating DESC
);