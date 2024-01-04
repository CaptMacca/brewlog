import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-brew-session',
  templateUrl: './brew-session.component.html',
  styleUrls: ['./brew-session.component.css']
})
export class BrewSessionComponent implements OnInit {

  @Input()
  parentForm: UntypedFormGroup;
  dateFormat = 'dd/MM/yyyy';

  constructor() { }

  ngOnInit() {
  }

  public get brewDate(): AbstractControl {
    return this.parentForm.get('brewDate');
  }

  public get score(): AbstractControl {
    return this.parentForm.get('score');
  }

  public get spargeVol(): AbstractControl {
    return this.parentForm.get('spargeVol');
  }

  public get totalWater(): AbstractControl {
    return this.parentForm.get('totalWater');
  }

  public get fermenterVol(): AbstractControl {
    return this.parentForm.get('fermenterVol');
  }

  public get estimatedOriginalGravity(): AbstractControl {
    return this.parentForm.get('estimatedOriginalGravity');
  }

  public get measuredOriginalGravity(): AbstractControl {
    return this.parentForm.get('measuredOriginalGravity');
  }

  public get estimatedPreboilGravity(): AbstractControl {
    return this.parentForm.get('estimatedPreboilGravity');
  }

  public get measuredPreboilGravity(): AbstractControl {
    return this.parentForm.get('measuredPreboilGravity');
  }

  public get estimatedFinalGravity(): AbstractControl {
    return this.parentForm.get('estimatedFinalGravity');
  }

  public get measuredFinalGravity(): AbstractControl {
    return this.parentForm.get('measuredFinalGravity');
  }

  public get estimatedFermentVolume(): AbstractControl {
    return this.parentForm.get('estimatedFermentVolume');
  }

  public get measuredFermentVolume(): AbstractControl {
    return this.parentForm.get('measuredFermentVolume');
  }

  public get estimatedBottleVolume(): AbstractControl {
    return this.parentForm.get('estimatedBottleVolume');
  }

  public get measuredBottleVolume(): AbstractControl {
    return this.parentForm.get('measuredBottleVolume');
  }

}
