alter table recipe
  add column brewer_id bigint null;

alter table recipe
  add constraint FKj0225oPK888oo0999mn
		foreign key (brewer_id) references brewer (id);