import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-brew-measurements',
  templateUrl: './brew-measurements.component.html',
  styleUrls: ['./brew-measurements.component.css']
})
export class BrewMeasurementsComponent implements OnInit {

  @Input()
  parentForm: FormGroup;
  @Output()
  deleteMeasurementEvent = new EventEmitter<number>();
  @Output()
  addMeasurementEvent = new EventEmitter();
  dateFormat = 'dd/MM/yyyy';

  constructor() { }

  ngOnInit() {
  }

  private addMeasurement(): void {
    this.addMeasurementEvent.emit();
  }

  private deleteMeasurement(id: number): void {
    this.deleteMeasurementEvent.emit(id);
  }

  get measurements(): FormArray {
    return this.parentForm.controls['measurements'] as FormArray;
  }

}
