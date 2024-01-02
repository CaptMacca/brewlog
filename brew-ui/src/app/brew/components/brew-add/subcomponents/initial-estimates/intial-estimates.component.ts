import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-intial-estimates',
  templateUrl: './intial-estimates.component.html',
  styleUrls: ['./intial-estimates.component.css']
})
export class IntialEstimatesComponent implements OnInit {
  @Input() parentForm: UntypedFormGroup;
  dateFormat = 'dd/MM/yyyy';

  constructor() { }

  ngOnInit() {
  }

  get brewDate(): AbstractControl {
    return this.parentForm.controls['brewDate'];
  }

  get estimatedOriginalGravity(): AbstractControl {
    return this.parentForm.controls['estimatedOriginalGravity'];
  }

  get estimatedPreboilGravity(): AbstractControl {
    return this.parentForm.controls['estimatedPreboilGravity'];
  }

  get estimatedFinalGravity(): AbstractControl {
    return this.parentForm.controls['estimatedFinalGravity'];
  }

  get estimatedFermentVolume(): AbstractControl {
    return this.parentForm.controls['estimatedFermentVolume'];
  }

  get estimatedBottleVolume(): AbstractControl {
    return this.parentForm.controls['estimatedBottleVolume']
  }

}
