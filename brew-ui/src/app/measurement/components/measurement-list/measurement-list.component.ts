import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

import { Store, Select } from '@ngxs/store';
import { SimpleModalService } from 'ngx-simple-modal';

import { Measurement } from '@app/model';
import { MeasurementState } from '@app/measurement/state/measurement.state';
import { NewMeasurement, LoadMeasurement, RemoveMeasurement } from '@app/measurement/state/measurement.actions';
import { ConfirmComponent } from '@app/common/confirm/confirm.component';
import { BrewState } from '@app/brew/state/brew.state';

@Component({
  selector: 'app-measurement-list',
  templateUrl: './measurement-list.component.html',
  styleUrls: ['./measurement-list.component.css']
})
export class MeasurementListComponent implements OnInit {

  @Select(MeasurementState.getMeasurements) measurements$: Observable<Measurement[]>;
  brewId: number;

  constructor(private store: Store,
              private router: Router,
              private simpleModalService: SimpleModalService) { }

  ngOnInit() {
    const brew = this.store.selectSnapshot(BrewState.getBrew);
    this.brewId = brew.id;
  }

  public addMeasurement() {
    this.store.dispatch(new NewMeasurement()).subscribe(
      () => this.router.navigate(['/brews/' + this.brewId + '/measurement/0']));
  }

  public editMeasurement(measurementId: number) {
    this.store.dispatch(new LoadMeasurement(measurementId)).subscribe(
      () => {
        this.router.navigate(['/brews/' + this.brewId + '/measurement/' + measurementId]);
      });
  }

  private deleteMeasurement(measurement: Measurement) {
    this.store.dispatch(new RemoveMeasurement(measurement));
  }

  public confirmDelete(measurement: Measurement) {
    this.simpleModalService.addModal(ConfirmComponent, {
        title: 'Confirm Delete',
        message: 'Are you sure you wish to delete this measurement'
      }).subscribe(
        isConfirmed => {
          if (isConfirmed) {
            this.deleteMeasurement(measurement);
          }
      });
  }


}
