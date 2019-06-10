import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Store, Select } from '@ngxs/store';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';

import { Recipe } from '@app/model';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { LoadRecipes } from '@app/recipe/state/recipe.actions';
import { AuthState } from '@app/auth/state/auth.state';

@Component({
  selector: 'app-recipe-list',
  styleUrls: ['./recipe-list.component.css'],
  templateUrl: './recipe-list.component.html'
})
export class RecipeListComponent implements OnInit {
  @Select(RecipeState.getRecipes) recipes$: Observable<Recipe>;

  constructor(private store: Store, private router: Router, private toastr: ToastrService) {}

  ngOnInit(): void {
    const username = this.store.selectSnapshot(AuthState.getUsername);
    this.store.dispatch(new LoadRecipes(username));
  }

  importRecipes() {
    this.router.navigate(['/recipes/import']);
  }

  viewRecipe(recipe: Recipe) {
    if (recipe) {
      this.router.navigate(['/recipes/' + recipe.id]);
    } else {
      this.toastr.error('No id passed', 'Error');
    }
  }
}