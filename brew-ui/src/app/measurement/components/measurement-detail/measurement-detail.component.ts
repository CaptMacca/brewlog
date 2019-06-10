import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { Store, Select } from '@ngxs/store';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';

import { Measurement, Brew } from '@app/model';
import { MeasurementState } from '@app/measurement/state/measurement.state';
import { AddMeasurement, UpdateMeasurement } from '@app/measurement/state/measurement.actions';
import { BrewState } from '@app/brew/state/brew.state';
import * as moment from 'moment';

const now = new Date();

function measurementTypeValidator(c: AbstractControl): { [key: string]: boolean } | null {
  if (c.value === undefined || c.value === 'default') {
    return { measurementTypeNotDefault: true };
  }
  return null;
}

@Component({
  selector: 'app-measurement-detail',
  templateUrl: './measurement-detail.component.html',
  styleUrls: ['./measurement-detail.component.css']
})
export class MeasurementDetailComponent implements OnInit {

  @Select(BrewState.getBrew) brew$: Observable<Brew>;
  @Select(MeasurementState.getMeasurement) measurement$: Observable<Measurement>;

  form: FormGroup;
  measurementId: number;
  brewId: number;
  selectedDate: Date;
  submitted = false;

  form_validation_messages = {
    measurementDate: [{ type: 'required', message: 'The measurement date is required' }],
    measurementValue: [{ type: 'required', message: 'The measurement values is required' }]
  };

  constructor(
    private store: Store,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.initForm();
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.brewId = +params['id'];
      this.measurementId = +params['measurementId'];
    });
    this.populateForm();
  }

  get measurementDate() {
    return this.form.get('measurementDate');
  }

  get measurementValue() {
    return this.form.get('measurementValue');
  }

  private initForm() {
    this.form = this.formBuilder.group({
      measurementDate: [now, Validators.required],
      measurementValue: ['', Validators.required]
    });
  }

  private populateForm() {
    const measurement = this.store.selectSnapshot(MeasurementState.getMeasurement);

    if (this.form) {
      this.form.reset();
    }

    if (measurement.id > 0) {
      const measurementDate = moment(measurement.measurementDate);
      this.form.patchValue({
        measurementDate: measurementDate.toDate(),
        measurementValue: measurement.value
      });
    }
    this.form.markAsPristine();
  }

  public save() {
    this.submitted = true;
    if (this.form.valid && this.form.dirty) {
      const measurement = this.store.selectSnapshot(MeasurementState.getMeasurement);

      measurement.brewId = this.brewId;
      measurement.measurementDate = this.measurementDate.value;
      measurement.value = this.measurementValue.value;

      if (measurement.id === 0) {
        this.store.dispatch(new AddMeasurement(measurement));
      } else {
        this.store.dispatch(new UpdateMeasurement(measurement));
      }

      this.toastr.success('Saved Measurement', 'Success');
      this.backToMeasurements();
    }
  }

  public backToMeasurements() {
    this.router.navigate(['/brews/' + this.brewId + '/measurements']);
  }
}
