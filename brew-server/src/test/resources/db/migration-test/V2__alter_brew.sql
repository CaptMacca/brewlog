ALTER TABLE brews.brew
  ADD COLUMN (
    score INT NULL,
    sparge_vol VARCHAR(255) NULL,
    total_water VARCHAR(255) NULL,
    fermenter_vol VARCHAR(255) NULL,
    estimated_original_gravity VARCHAR(255) NULL,
    measured_original_gravity VARCHAR(255) NULL,
    estimated_preboil_gravity VARCHAR(255) NULL,
    measured_preboil_gravity VARCHAR(255) NULL,
    estimated_final_gravity VARCHAR(255) NULL,
    measured_final_gravity VARCHAR(255) NULL,
    estimated_ferment_volume VARCHAR(255) NULL,
    measured_ferment_volume VARCHAR(255) NULL,
    estimated_bottle_volume VARCHAR(255) NULL,
    measured_bottle_volume VARCHAR(255) NULL,
    notes VARCHAR NULL,
    tasting_notes VARCHAR NULL
  );

ALTER TABLE brews.measurement
  DROP COLUMN "type";
