import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

import { Observable } from 'rxjs';
import { Select, Store } from '@ngxs/store';
import { ToastrService } from 'ngx-toastr';

import { Brew, Measurement, NewBrewRequest } from '@app/model';
import { BrewState } from '@app/brew/state/brew.state';
import { LoadMeasurements } from '@app/measurement/state/measurement.actions';
import { MeasurementState } from '@app/measurement/state/measurement.state';
import { SaveBrew, UpdateBrew } from '@app/brew/state/brew.actions';
import * as moment from 'moment';
import { AuthState } from '@app/auth/state/auth.state';

const now = new Date();

@Component({
  selector: 'app-brew-detail',
  templateUrl: './brew-detail.component.html',
  styleUrls: ['./brew-detail.component.css']
})
export class BrewDetailComponent implements OnInit {

  @Select(BrewState.getBrew) brew$: Observable<Brew>;
  brewId: number;
  @Select(MeasurementState.getMeasurements) measurements$: Observable<Measurement[]>;

  brewForm: FormGroup;
  submitted: boolean;

  constructor(
    private store: Store,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {
    this.initForm();
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.brewId = +params['id'];
    });

    this.populateForm();
  }

  public get brewDate(): AbstractControl {
    return this.brewForm.get('brewDate');
  }

  public get score(): AbstractControl {
    return this.brewForm.get('score');
  }

  public get spargeVol(): AbstractControl {
    return this.brewForm.get('spargeVol');
  }

  public get totalWater(): AbstractControl {
    return this.brewForm.get('totalWater');
  }

  public get fermenterVol(): AbstractControl {
    return this.brewForm.get('fermenterVol');
  }

  public get estimatedOriginalGravity(): AbstractControl {
    return this.brewForm.get('estimatedOriginalGravity');
  }

  public get measuredOriginalGravity(): AbstractControl {
    return this.brewForm.get('measuredOriginalGravity');
  }

  public get estimatedPreboilGravity(): AbstractControl {
    return this.brewForm.get('estimatedPreboilGravity');
  }

  public get measuredPreboilGravity(): AbstractControl {
    return this.brewForm.get('measuredPreboilGravity');
  }

  public get estimatedFinalGravity(): AbstractControl {
    return this.brewForm.get('estimatedFinalGravity');
  }

  public get measuredFinalGravity(): AbstractControl {
    return this.brewForm.get('measuredFinalGravity');
  }

  public get estimatedFermentVolume(): AbstractControl {
    return this.brewForm.get('estimatedFermentVolume');
  }

  public get measuredFermentVolume(): AbstractControl {
    return this.brewForm.get('measuredFermentVolume');
  }

  public get estimatedBottleVolume(): AbstractControl {
    return this.brewForm.get('estimatedBottleVolume');
  }

  public get measuredBottleVolume(): AbstractControl {
    return this.brewForm.get('measuredBottleVolume');
  }

  public get notes(): AbstractControl {
    return this.brewForm.get('notes');
  }

  public get tastingNotes(): AbstractControl {
    return this.brewForm.get('tastingNotes');
  }

  private initForm() {
    this.brewForm = this.fb.group({
      brewDate: [now, Validators.required],
      score: [''],
      spargeVol: [''],
      totalWater: [''],
      fermenterVol: [''],
      estimatedOriginalGravity: [''],
      measuredOriginalGravity: [''],
      estimatedPreboilGravity: [''],
      measuredPreboilGravity: [''],
      estimatedFinalGravity: [''],
      measuredFinalGravity: [''],
      estimatedFermentVolume: [''],
      measuredFermentVolume: [''],
      estimatedBottleVolume: [''],
      measuredBottleVolume: [''],
      notes: [''],
      tastingNotes: ['']
    });
  }

  private populateForm() {
    const brew = this.store.selectSnapshot(BrewState.getBrew);
    if (this.brewForm) {
      this.brewForm.reset();
    }

    if (brew) {
      if (brew.id > 0) {
        const brewDate = moment(brew.brewDate);
        this.brewForm.patchValue({
          brewDate: brewDate.toDate(),
          score: brew.score,
          spargeVol: brew.spargeVol,
          totalWater: brew.totalWater,
          fermenterVol: brew.fermenterVol,
          estimatedOriginalGravity: brew.estimatedBottleVolume,
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
          tastingNotes: brew.tastingNotes
        });
      } else {
        this.brewForm.patchValue({
          brewDate: now
        });
      }
    }
    this.store.dispatch('brews.brewsForm');
  }

  public gotoMeasurements() {
    const brew = this.store.selectSnapshot(BrewState.getBrew);
    this.store.dispatch(new LoadMeasurements(brew.measurements)).subscribe(
      () => {
        this.router.navigate(['/brews/' + this.brewId + '/measurements']);
      });
  }

  public save() {
    this.submitted = true;
    if (this.brewForm.dirty && this.brewForm.valid) {
      const brew = this.store.selectSnapshot(BrewState.getBrew);

      brew.brewDate = this.brewDate.value;
      brew.score = this.score.value;
      brew.spargeVol = this.spargeVol.value;
      brew.totalWater = this.totalWater.value;
      brew.estimatedOriginalGravity = this.estimatedOriginalGravity.value;
      brew.measuredOriginalGravity = this.measuredOriginalGravity.value;
      brew.estimatedPreboilGravity = this.estimatedPreboilGravity.value;
      brew.measuredPreboilGravity = this.measuredPreboilGravity.value;
      brew.estimatedFinalGravity = this.estimatedFinalGravity.value;
      brew.measuredFinalGravity = this.measuredFinalGravity.value;
      brew.estimatedBottleVolume = this.estimatedBottleVolume.value;
      brew.measuredBottleVolume = this.measuredBottleVolume.value;
      brew.estimatedFermentVolume = this.estimatedFermentVolume.value;
      brew.measuredFermentVolume = this.measuredFermentVolume.value;
      brew.notes = this.notes.value;
      brew.tastingNotes = this.tastingNotes.value;

      if (brew.id === 0) {
        const username = this.store.selectSnapshot(AuthState.getUsername);
        const newBrew: NewBrewRequest = {
          username: username,
          brew: brew
        };
        this.store.dispatch(new SaveBrew(newBrew)).subscribe(
          () => {
            this.toastr.success('Saved the brew', 'Success');
            this.router.navigate(['/brews']);
          });
      } else {
        this.store.dispatch(new UpdateBrew(brew)).subscribe(
          () => {
            this.toastr.success('Updated the brew', 'Success');
            this.router.navigate(['/brews']);
          },
          err => this.toastr.error('An Error has occurred', 'Error'));
      }
    }
  }
}
