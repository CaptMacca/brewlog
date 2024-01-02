import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UntypedFormArray, UntypedFormGroup } from '@angular/forms';
import { RxFormArray, RxFormControl, RxFormGroup } from '@rxweb/reactive-form-validators';

@Component({
  selector: 'app-brew-measurements',
  templateUrl: './brew-measurements.component.html',
  styleUrls: ['./brew-measurements.component.css']
})
export class BrewMeasurementsComponent implements OnInit {

  @Input()
  parentForm: UntypedFormGroup;
  @Output()
  deleteMeasurementEvent = new EventEmitter<number>();
  @Output()
  addMeasurementEvent = new EventEmitter();
  dateFormat = 'dd/MM/yyyy';

  constructor() { }

  ngOnInit() {
  }

  addMeasurement(): void {
    this.addMeasurementEvent.emit();
  }

  deleteMeasurement(id: number): void {
    this.deleteMeasurementEvent.emit(id);
  }

  get measurements(): UntypedFormArray {
    return this.parentForm.controls['measurements'] as RxFormArray;
  }

  validationMessages(index, controlName: string): string[] {
    const abstractCtl = (<RxFormGroup>this.measurements.controls[index]).controls[controlName] as RxFormControl;
    if (abstractCtl.invalid) {
      return abstractCtl.errorMessages;
    } else {
      return null;
    }
  }
}
