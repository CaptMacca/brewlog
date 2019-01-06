create table if not exists brewer
(
	id bigint auto_increment
		primary key,
	given_name varchar(255) null,
	surname varchar(255) null,
	email varchar(255) null,
	enabled bit,
  created_on date not null
);

alter table brew
  add column brewer_id bigint null;

alter table brew
  drop column brewer;

alter table brew
  add constraint FKj0s43433fcvv76vvbndgga
		foreign key (brewer_id) references brewer (id);