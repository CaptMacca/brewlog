import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthState } from '@app/auth/state/auth.state';
import { SaveBrew } from '@app/brew/state/brew.actions';
import { BrewState } from '@app/brew/state/brew.state';
import { Recipe } from '@app/recipe/model';
import { LoadRecipes } from '@app/recipe/state/recipe.actions';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { NzMessageService, NzNotificationService } from 'ng-zorro-antd';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { AbstractControl, FormGroup } from '@angular/forms';
import * as moment from 'moment';
import { CandidateBrew, CreateBrew, Brew } from '@app/brew/model';
import { withLatestFrom } from 'rxjs/operators';

@Component({
  selector: 'app-brew-add',
  templateUrl: './brew-add.component.html',
  styleUrls: ['./brew-add.component.css']
})
export class BrewAddComponent implements OnInit {
  @Select(RecipeState.getRecipes) recipes$: Observable<Recipe[]>;
  @Select(AuthState.getUsername) username$: Observable<string>;
  selectedRecipe: Recipe;
  current = 0;
  brewForm: FormGroup;
  brew: Brew;

  constructor(
    private readonly store: Store,
    private readonly router: Router,
    private readonly fb: RxFormBuilder,
    private readonly message: NzMessageService,
    private readonly notification: NzNotificationService
  ) {}

  ngOnInit() {
    this.username$.pipe(
      withLatestFrom(this.recipes$)
    ).subscribe(([username, recipes]) => {
      if (recipes.length <= 0) {
        this.store.dispatch(new LoadRecipes(username));
      }
      this.brewForm = this.fb.formGroup(new CandidateBrew());
      this.populateForm();
    });
  }

  private populateForm() {
    if (this.brewForm) {
      this.brewForm.reset();
    }

    const brewDate = moment();
    this.brewForm.patchValue({
      brewDate: brewDate.toDate(),
      estimatedOriginalGravity: 0,
      estimatedPreboilGravity: 0,
      estimatedFinalGravity: 0,
      estimatedFermentVolume: 0,
      estimatedBottleVolume: 0
    });
    this.brewForm.markAsDirty();
    this.store.dispatch('brews.brewsForm');
  }

  get brewDate(): AbstractControl {
    return this.brewForm.controls['brewDate'];
  }
  get estimatedOriginalGravity(): AbstractControl {
    return this.brewForm.controls['estimatedOriginalGravity'];
  }
  get estimatedPreboilGravity(): AbstractControl {
    return this.brewForm.controls['estimatedPreboilGravity'];
  }
  get estimatedFinalGravity(): AbstractControl {
    return this.brewForm.controls['estimatedFinalGravity'];
  }
  get estimatedFermentVolume(): AbstractControl {
    return this.brewForm.controls['estimatedFermentVolume'];
  }
  get estimatedBottleVolume(): AbstractControl {
    return this.brewForm.controls['estimatedBottleVolume'];
  }

  selectRecipe(recipe: Recipe) {
    this.selectedRecipe = recipe;
  }

  unselectRecipe(recipe: Recipe): void {
    this.selectedRecipe = undefined;
  }

  saveBrew(saveIt: string): void {
    if (saveIt === 'yes' && this.brewForm.dirty && this.brewForm.valid) {
      if (this.selectedRecipe) {
        const intialBrew = new CandidateBrew();
        intialBrew.recipeId = this.selectedRecipe.id;
        intialBrew.recipeName = this.selectedRecipe.name;
        intialBrew.brewDate = this.brewDate.value;
        intialBrew.estimatedPreboilGravity = this.estimatedPreboilGravity.value;
        intialBrew.estimatedOriginalGravity = this.estimatedOriginalGravity.value;
        intialBrew.estimatedFinalGravity = this.estimatedFinalGravity.value;
        intialBrew.estimatedBottleVolume = this.estimatedBottleVolume.value;
        intialBrew.estimatedFermentVolume = this.estimatedFermentVolume.value;

        const createBrew: CreateBrew = {
          brew: intialBrew,
          recipe: this.selectedRecipe
        };

        this.store.dispatch(new SaveBrew(createBrew)).subscribe(
          state => {
            const brew = this.store.selectSnapshot(BrewState.getBrew);
            this.message.success('A new brew session has been saved');
            this.router.navigate(['/main/brews/' + brew.id]);
          },
          error => {
            this.message.error('Creation of brew session failed');
          }
        );
      }
    } else {
      this.backToBrewsList();
    }
  }

  next(): void {
    if (this.selectedRecipe) {
      if (this.brewForm.valid) {
        this.current++;
      } else {
        this.notification.create(
          'error',
          'Add Brew',
          'The initial values provided are not correct, please correct them before proceeding'
        );
      }
    }
  }

  prev(): void {
    if (this.current > 0) {
      this.current--;
    }
  }

  backToBrewsList(): void {
    this.router.navigate(['/main/brews']);
  }
}
