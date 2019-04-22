alter table brew
  add column version_id bigint default 1 not null;

alter table recipe
  add column version_id bigint default 1 not null;

alter table measurement
  add column version_id bigint default 1 not null;

alter table ingredient
  add column version_id bigint default 1 not null;

alter table mash
  add column version_id bigint default 1 not null;






