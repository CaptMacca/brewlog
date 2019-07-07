import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ToastrService } from 'ngx-toastr';
import { Store, Select } from '@ngxs/store';
import { Observable } from 'rxjs';

import { Brew, Recipe, User, CreateBrew } from '@app/model';
import { LoadRecipes } from '@app/recipe/state/recipe.actions';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { SaveBrew } from '@app/brew/state/brew.actions';
import { BrewState } from '@app/brew/state/brew.state';
import { AuthState } from '@app/auth/state/auth.state';

@Component({
  selector: 'app-brew-add',
  templateUrl: './brew-add.component.html',
  styleUrls: ['./brew-add.component.css']
})
export class BrewAddComponent implements OnInit {

  @Select(RecipeState.getRecipes) recipes$: Observable<Recipe[]>;

  constructor(
    private store: Store,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    const username: string = this.store.selectSnapshot(AuthState.getUsername);
    this.store.dispatch(new LoadRecipes(username));
  }

  saveBrew(recipe: Recipe) {
    if (recipe) {
      const brew = this.newBrew(recipe);
      const username: string = this.store.selectSnapshot(AuthState.getUsername);
      const createBrew: CreateBrew = {
        username: username,
        brew: brew,
        recipe
      };

      this.store.dispatch([new SaveBrew(createBrew)]).subscribe(
        () => {
          const createdBrew = this.store.selectSnapshot(BrewState.getBrew);
          this.toastr.success('A new brew session has been created', 'Success');
          this.router.navigate(['/brews/' + createdBrew.id]);
        });
    }
  }

  backToBrewsList() {
    this.router.navigate(['/brews']);
  }

  private newBrew(recipe: Recipe): Brew {
    const brew = new Brew();
    brew.id = 0;
    brew.brewDate = new Date();
    brew.score = 0;
    brew.recipe = recipe;
    return brew;
  }
}
