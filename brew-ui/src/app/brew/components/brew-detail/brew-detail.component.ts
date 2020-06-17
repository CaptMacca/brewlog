import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormArray, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RemoveBrew, SetSavingBrew, UpdateBrew } from '@app/brew/state/brew.actions';
import { BrewState } from '@app/brew/state/brew.state';
import { Brew, Measurement } from '@app/model';
import { Select, Store } from '@ngxs/store';
import { Observable, throwError } from 'rxjs';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { RxFormBuilder, RxwebValidators } from '@rxweb/reactive-form-validators';
import { catchError, finalize, withLatestFrom } from 'rxjs/operators';
import { MeasurementState } from '@app/brew/state/measurement.state';
import { DeleteMeasurement, RemoveMeasurement, SaveMeasurements } from '@app/brew/state/measurement.actions';

@Component({
  selector: 'app-brew-detail',
  templateUrl: './brew-detail.component.html',
  styleUrls: ['./brew-detail.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BrewDetailComponent implements OnInit {
  @Select(BrewState.getBrew) brew$: Observable<Brew>;
  @Select(MeasurementState.getMeasurements) measurements$: Observable<Measurement[]>;
  @Select(BrewState.getSavingBrew) saving: Observable<boolean>;

  brewId: number;
  brewForm: FormGroup;
  measurementId = 0;

  constructor(
    private readonly store: Store,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly fb: RxFormBuilder,
    private readonly message: NzMessageService,
    private readonly modalService: NzModalService
  ) {
    this.initForm();
   }

  ngOnInit() {
    this.loadBrewForm()
  }

  private loadBrewForm() {
    this.brew$.pipe(
      withLatestFrom(this.measurements$),
      catchError(err => throwError(err))
    ).subscribe(([brew, measurements]) => {
      this.brewId = brew.id;
      this.populateForm(brew, measurements);
    });
  }

  get fc() {
    return this.brewForm.controls;
  }

  private initForm() {
    this.brewForm = this.fb.group({
      brewDate: ['', [
        RxwebValidators.required({message: 'The brew date is required.'})
      ]],
      score: [''],
      spargeVol: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Sparge volume must be greater than 0'})
      ]],
      totalWater: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Total water must be greater than 0'})
      ]],
      fermenterVol: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Fermenter volume must be greater than 0'})
      ]],
      estimatedOriginalGravity: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Estimated original gravity must be greater than 0'})
      ]],
      measuredOriginalGravity: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Measured original gravity must be greater than 0'})
      ]],
      estimatedPreboilGravity: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Estimated pre boil gravity must be greater than 0'})
      ]],
      measuredPreboilGravity: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Measured pre boil gravity must be greater than 0'})
      ]],
      estimatedFinalGravity: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Estimated final gravity must be greater than 0'})
      ]],
      measuredFinalGravity: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Measured final gravity must be greater than 0'})
      ]],
      estimatedFermentVolume: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Estimated fermenter volume must be greater than 0'})
      ]],
      measuredFermentVolume: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Measured fermenter must be greater than 0'})
      ]],
      estimatedBottleVolume: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Estimated bottling volume must be greater than 0'})
      ]],
      measuredBottleVolume: ['', [
        RxwebValidators.greaterThanEqualTo({value: 0, message: 'Measured bottling volume must be greater than 0'})
      ]],
      notes: [''],
      tastingNotes: [''],
    });
  }

  private populateForm(brew: Brew, measurements: Measurement[]) {
    this.brewForm.patchValue({
      brewDate: brew.brewDate,
      score: brew.score,
      spargeVol: brew.spargeVol,
      totalWater: brew.totalWater,
      fermenterVol: brew.fermenterVol,
      estimatedOriginalGravity: brew.estimatedOriginalGravity,
      measuredOriginalGravity: brew.measuredOriginalGravity,
      estimatedPreboilGravity: brew.estimatedPreboilGravity,
      measuredPreboilGravity: brew.measuredPreboilGravity,
      estimatedFinalGravity: brew.estimatedFinalGravity,
      measuredFinalGravity: brew.measuredFinalGravity,
      estimatedFermentVolume: brew.estimatedFermentVolume,
      measuredFermentVolume: brew.measuredFermentVolume,
      estimatedBottleVolume: brew.estimatedBottleVolume,
      measuredBottleVolume: brew.measuredBottleVolume,
      notes: brew.notes,
      tastingNotes: brew.tastingNotes,
    });
    const measurementFormArray = new FormArray([]);
    measurements.forEach(measurement => {
      measurementFormArray.push(this.createMeasurementControl(measurement))
    });
    this.brewForm.addControl('measurements', measurementFormArray);
    this.brewForm.markAsPristine();
  }

  gotoBrews(): void {
    this.router.navigate(['/main/brews/']);
  }

  public save(brew: Brew): void {

    if (brew && this.brewForm.dirty && this.brewForm.valid) {
      brew.brewDate = this.fc['brewDate'].value;
      brew.score = this.fc['score'].value;
      brew.fermenterVol = this.fc['fermenterVol'].value;
      brew.spargeVol = this.fc['spargeVol'].value;
      brew.totalWater = this.fc['totalWater'].value;
      brew.estimatedOriginalGravity = this.fc['estimatedOriginalGravity'].value;
      brew.measuredOriginalGravity = this.fc['measuredOriginalGravity'].value;
      brew.estimatedPreboilGravity = this.fc['estimatedPreboilGravity'].value;
      brew.measuredPreboilGravity = this.fc['measuredPreboilGravity'].value;
      brew.estimatedFinalGravity = this.fc['estimatedFinalGravity'].value;
      brew.measuredFinalGravity = this.fc['measuredFinalGravity'].value;
      brew.estimatedBottleVolume = this.fc['estimatedBottleVolume'].value;
      brew.measuredBottleVolume = this.fc['measuredBottleVolume'].value;
      brew.estimatedFermentVolume = this.fc['estimatedFermentVolume'].value;
      brew.measuredFermentVolume = this.fc['measuredFermentVolume'].value;
      brew.notes = this.fc['notes'].value;
      brew.tastingNotes = this.fc['tastingNotes'].value;

      const measurementsArray = this.fc['measurements'].value;
      const measurements: Measurement[] = [];
      if (measurementsArray) {
        measurementsArray.forEach(m => {
          const measurement = {
            id: m.measurementId,
            brewId: this.brewId,
            measurementDate: m.measurementDate,
            value: m.measurementValue
          };
          measurements.push(measurement);
        });
      }
      this.store.dispatch([
        new SetSavingBrew(true),
        new UpdateBrew(brew),
        new SaveMeasurements(measurements)]).pipe(
        finalize(() => this.store.dispatch(new SetSavingBrew(false)))
      ).subscribe(
        () => {
          this.loadBrewForm();
          this.message.success('The brew session details have been updated');
      });
    }
  }

  confirm(brew: Brew): void {
    this.modalService.confirm({
      nzTitle: 'Are you sure delete this brew session?',
      nzOkText: 'Yes',
      nzOkType: 'danger',
      nzOnOk: () => this.deleteBrew(brew),
      nzCancelText: 'No'
    });
  }

  private deleteBrew(brew: Brew): void {
    if (brew) {
      this.store.dispatch(new RemoveBrew(brew)).subscribe(() => {
        this.message.success('Brew Session deleted');
        this.router.navigate(['/main/brews']);
      });
    }
  }

  deleteMeasurement(id: number) {
    if (id) {
      const measurement = this.measurements.at(id).value;
      let removeMeasurement$: Observable<any> = undefined;
      if (measurement.measurementId > 0) {
        // Saved measurement delete via backend first
        removeMeasurement$ = this.store.dispatch(new DeleteMeasurement(measurement.measurementId));
      } else {
        // Unsaved measurement just remove from state and FormArray
        removeMeasurement$ = this.store.dispatch(new RemoveMeasurement(measurement));
      }
      removeMeasurement$.subscribe(
        () => {
          this.measurements.removeAt(id);
          this.loadBrewForm();
          this.message.success('Measurement removed');
      });
    }
  }

  addMeasurement() {
    // Make new items id negative to separate from updates
    const measurement: Measurement = {
      id: this.measurementId--,
      brewId: this.brewId,
      measurementDate: new Date(),
      value: 0
    };
    this.newMeasurement(measurement);
    this.brewForm.markAsDirty();
  }

  private newMeasurement(measurement: Measurement) {
    const measurementControl = this.brewForm.controls['measurements'] as FormArray;
    measurementControl.push(this.createMeasurementControl(measurement));
  }

  get measurements(): FormArray {
    return this.brewForm.controls['measurements'] as FormArray;
  }

  private createMeasurementControl(measurement: Measurement): FormGroup {
    return this.fb.group({
      measurementId: measurement.id,
      measurementDate: measurement.measurementDate,
      measurementValue: measurement.value,
    });
  }
}
