ALTER TABLE brews.brew
  ADD COLUMN version_id BIGINT DEFAULT 1 NOT NULL;

ALTER TABLE brews.recipe
  ADD COLUMN version_id BIGINT DEFAULT 1 NOT NULL;

ALTER TABLE brews.measurement
  ADD COLUMN version_id BIGINT DEFAULT 1 NOT NULL;

ALTER TABLE brews.ingredient
  ADD COLUMN version_id BIGINT DEFAULT 1 NOT NULL;

ALTER TABLE brews.mash
  ADD COLUMN version_id BIGINT DEFAULT 1 NOT NULL;






