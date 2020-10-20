import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RemoveBrew, RemoveMeasurement, SetSavingBrew, UpdateBrew } from '@app/brew/state/brew.actions';
import { BrewState } from '@app/brew/state/brew.state';
import { Brew, Measurement } from '@app/brew/model';
import { Select, Store } from '@ngxs/store';
import { Observable, of, throwError } from 'rxjs';
import { NzMessageService, NzModalService } from 'ng-zorro-antd';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { catchError, finalize, switchMap } from 'rxjs/operators';
import { ConcurrentUpdateError } from '@app/common/errors/optimistic-lock-error';

@Component({
  selector: 'app-brew-detail',
  templateUrl: './brew-detail.component.html',
  styleUrls: ['./brew-detail.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BrewDetailComponent implements OnInit {
  @Select(BrewState.getBrew) brew$: Observable<Brew>;
  @Select(BrewState.getSavingBrew) saving: Observable<boolean>;

  brewForm: FormGroup;
  brew: Brew;

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
      catchError(err => throwError(err))
    ).subscribe((brew: Brew) => {
      this.brew = brew;
      this.populateForm(brew);
    });
  }

  get fc() {
    return this.brewForm.controls;
  }

  private initForm() {
    const brew = new Brew();
    const measurement = new Measurement();
    brew.measurements = new Array<Measurement>();
    brew.measurements.push(measurement);
    this.brewForm = this.fb.formGroup(brew);
  }

  private populateForm(brew: Brew) {

    const measurementFormArray = <FormArray>this.measurementsFormArray;
    measurementFormArray.clear();

    if (brew) {
      this.brewForm.patchValue(brew);

      const measurements: Measurement[] = brew.measurements;

      if (measurements && measurements.length > 0) {
        measurements.forEach(measurement => {
          const measurementControl = this.createMeasurementControl();
          measurementControl.patchValue(measurement);
          measurementFormArray.push(measurementControl);
        });
      }

      this.brewForm.markAsPristine();
    }
  }

  gotoBrews(): void {
    this.router.navigate(['/main/brews/']);
  }

  public save(brew: Brew): void {

    if (brew && this.brewForm.dirty && this.brewForm.valid) {
      brew.brewDate = new Date(this.fc['brewDate'].value);
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
      brew.measurements = this.extractMeasurements(this.measurementsFormArray);

      this.store.dispatch([
        new SetSavingBrew(true),
        new UpdateBrew(brew),
      ]).pipe(
        switchMap(results => of(results[1])),
        finalize(() => this.store.dispatch(new SetSavingBrew(false)))
      ).subscribe(
          () => {
          this.loadBrewForm();
          this.message.success('The brew session details have been updated');
        },
        error => {
          if (error instanceof ConcurrentUpdateError) {
            this.message.error('Brew details have been updated by another user, reload the brew session and retry.');
          } else {
            throw error;
          }
        }
      );
    }
  }

  confirmDelete(brew: Brew): void {
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

  deleteMeasurement(id: number): void {
    const measurementCtl = this.measurementsFormArray.at(id).value;
    const measurementId = measurementCtl.id;
    if (measurementId && measurementId > 0) {
      // Saved measurement delete via backend first
      const measurement: Measurement = this.brew.measurements.find(m => m.id === measurementId);
      this.store.dispatch(new RemoveMeasurement(measurement)).subscribe(
        () => {
          this.removeMeasurementControl(id);
          this.loadBrewForm();
        }
      );
    } else {
      // Unsaved measurement just remove from formArray
      this.removeMeasurementControl(id);
    }
  }

  private removeMeasurementControl(id: number) {
    this.measurementsFormArray.removeAt(id);
    this.brewForm.markAsPristine();
    this.message.success('Measurement removed');
  }

  addMeasurement() {
    const measurement: Measurement = {
      id: undefined,
      measurementDate: new Date(),
      value: 0,
      versionId: 1
    };
    this.newMeasurement(measurement);
    this.brewForm.markAsDirty();
  }

  private newMeasurement(measurement: Measurement) {
    const measurementControl = this.createMeasurementControl();
    const measurementFormArray = this.measurementsFormArray;
    measurementControl.patchValue(measurement);
    measurementFormArray.push(measurementControl);
  }

  get measurementsFormArray(): FormArray {
    return <FormArray>this.brewForm.controls.measurements;
  }

  private createMeasurementControl(): FormGroup {
    return this.fb.formGroup(Measurement);
  }

  private extractMeasurements(measurementsControl: AbstractControl): Measurement[] {
    const measurementsArray = measurementsControl.value;
    const measurements: Measurement[] = [];
    if (measurementsArray) {
      measurementsArray.forEach(m => {
        const measurement = {
          id: m.id,
          measurementDate: m.measurementDate,
          value: m.value,
          versionId: m.versionId
        };
        measurements.push(measurement);
      });
    }
    return measurements;
  }
}
