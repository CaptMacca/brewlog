alter table recipe
  add column user_id bigint NOT NULL;

alter table recipe
  add constraint FKddfdfgg33fcvv76vvbndgga
    foreign key (user_id) references users (id);

alter table brew
  add column user_id bigint NOT NULL;

alter table brew
  add constraint Fdfdfdfhhh6676vvb9uu9
    foreign key (user_id) references users (id);

alter table brew
  drop foreign key FKj0s43433fcvv76vvbndgga;

drop index FKj0s43433fcvv76vvbndgga on brew;

alter table brew
  drop column brewer_id;

drop table brewer;