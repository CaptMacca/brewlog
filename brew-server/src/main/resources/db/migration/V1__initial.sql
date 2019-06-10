create schema if not exists brews collate latin1_swedish_ci;

create table if not exists recipe
(
	id bigint auto_increment
		primary key,
	batch_size varchar(255) null,
	boil_time varchar(255) null,
	estimatedabv varchar(255) null,
	estimated_colour varchar(255) null,
	final_gravity varchar(255) null,
	ibu varchar(255) null,
	name varchar(255) null,
	notes longtext null,
	original_gravity varchar(255) null,
	style varchar(255) null,
	type varchar(255) null
);

create table if not exists brew
(
	id bigint auto_increment
		primary key,
	brew_date date null,
	brewer varchar(255) null,
	recipe_id bigint null,
	constraint FKf0mv8607tqc9t1sw4o997gcgi
		foreign key (recipe_id) references recipe (id)
);

create table if not exists ingredient
(
	type varchar(31) not null,
	id bigint auto_increment
		primary key,
	amount double null,
	name varchar(255) null,
	addition_time double null,
	alpha double null,
	hop_usage varchar(255) null,
	laboratory varchar(255) null,
	product_id varchar(255) null,
	add_after_boil bit null,
	recipe_id bigint null,
	constraint FKj0s4ywmqqqw4h5iommigh5yja
		foreign key (recipe_id) references recipe (id)
);

create table if not exists mash
(
	id bigint auto_increment
		primary key,
	name varchar(255) null,
	step_temp double null,
	step_time double null,
	recipe_id bigint null,
	constraint FK94b26ql4hso7wd8dnnaea5c4p
		foreign key (recipe_id) references recipe (id)
);

create table if not exists measurement
(
	id bigint auto_increment
		primary key,
	measurement_date date null,
	type int null,
	value double null,
	brew_id bigint null,
	constraint FKms0rxxek1rcu66bvo7fdutuw9
		foreign key (brew_id) references brew (id)
);

