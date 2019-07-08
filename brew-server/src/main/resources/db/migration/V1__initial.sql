CREATE SCHEMA IF NOT EXISTS brews;

CREATE TABLE IF NOT EXISTS brews.recipe
(
	id BIGSERIAL PRIMARY KEY,
	batch_size VARCHAR(255) NULL,
	boil_time VARCHAR(255) NULL,
	estimatedabv VARCHAR(255) NULL,
	estimated_colour VARCHAR(255) NULL,
	final_gravity VARCHAR(255) NULL,
	ibu VARCHAR(255) NULL,
	name VARCHAR(255) NULL,
	notes TEXT NULL,
	original_gravity VARCHAR(255) NULL,
	style VARCHAR(255) NULL,
	type VARCHAR(255) NULL
);

CREATE INDEX recipe_name_idx ON brews.recipe(name);

CREATE TABLE IF NOT EXISTS brews.brew
(
	id BIGSERIAL PRIMARY KEY,
	brew_date TIMESTAMP NULL,
	brewer VARCHAR(255) NULL,
	recipe_id BIGINT NULL,
	CONSTRAINT brew_recipe_fk
		FOREIGN KEY (recipe_id) REFERENCES brews.recipe (id)
);

CREATE TABLE IF NOT EXISTS brews.ingredient
(
	id BIGSERIAL	PRIMARY KEY,
  type VARCHAR(31) NOT NULL,
	amount DOUBLE PRECISION NULL,
	name VARCHAR(255) NULL,
	addition_time DOUBLE PRECISION NULL,
	alpha DOUBLE PRECISION NULL,
	hop_usage VARCHAR(255) NULL,
	laboratory VARCHAR(255) NULL,
	product_id VARCHAR(255) NULL,
	add_after_boil BOOLEAN NULL,
	recipe_id BIGINT NULL,
	CONSTRAINT ingredient_recipe_fk
		FOREIGN KEY (recipe_id) REFERENCES brews.recipe (id)
);

CREATE TABLE IF NOT EXISTS brews.mash
(
	id BIGSERIAL	PRIMARY KEY,
	name VARCHAR(255) NULL,
	step_temp DOUBLE PRECISION NULL,
	step_time DOUBLE PRECISION NULL,
	recipe_id BIGINT NULL,
	CONSTRAINT mash_recipe_fk
		FOREIGN KEY (recipe_id) REFERENCES brews.recipe (id)
);

create table if not exists brews.measurement
(
	id BIGSERIAL PRIMARY KEY,
	measurement_date TIMESTAMP NULL,
	type INT NULL,
	value DOUBLE PRECISION NULL,
	brew_id BIGINT NULL,
	CONSTRAINT measurement_brew_fk
		FOREIGN KEY (brew_id) REFERENCES brews.brew (id)
);

