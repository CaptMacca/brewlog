ALTER TABLE brews.brew
  ADD COLUMN score INT NULL,
  ADD COLUMN sparge_vol VARCHAR(255) NULL,
  ADD COLUMN total_water VARCHAR(255) NULL,
  ADD COLUMN fermenter_vol VARCHAR(255) NULL,
  ADD COLUMN estimated_original_gravity VARCHAR(255) NULL,
  ADD COLUMN measured_original_gravity VARCHAR(255) NULL,
  ADD COLUMN estimated_preboil_gravity VARCHAR(255) NULL,
  ADD COLUMN measured_preboil_gravity VARCHAR(255) NULL,
  ADD COLUMN estimated_final_gravity VARCHAR(255) NULL,
  ADD COLUMN measured_final_gravity VARCHAR(255) NULL,
  ADD COLUMN estimated_ferment_volume VARCHAR(255) NULL,
  ADD COLUMN measured_ferment_volume VARCHAR(255) NULL,
  ADD COLUMN estimated_bottle_volume VARCHAR(255) NULL,
  ADD COLUMN measured_bottle_volume VARCHAR(255) NULL,
  ADD COLUMN notes TEXT NULL,
  ADD COLUMN tasting_notes TEXT NULL;

ALTER TABLE brews.measurement
  DROP COLUMN type;
