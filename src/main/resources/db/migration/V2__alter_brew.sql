alter table brew
  add column score int null,
  add column sparge_vol varchar(255) null,
  add column total_water varchar(255) null,
  add column fermenter_vol varchar(255) null,
  add column estimated_original_gravity varchar(255) null,
  add column measured_original_gravity varchar(255) null,
  add column estimated_preboil_gravity varchar(255) null,
  add column measured_preboil_gravity varchar(255) null,
  add column estimated_final_gravity varchar(255) null,
  add column measured_final_gravity varchar(255) null,
  add column estimated_ferment_volume varchar(255) null,
  add column measured_ferment_volume varchar(255) null,
  add column estimated_bottle_volume varchar(255) null,
  add column measured_bottle_volume varchar(255) null,
  add column notes text null,
  add column tasting_notes text null;

alter table measurement
  drop column type;
