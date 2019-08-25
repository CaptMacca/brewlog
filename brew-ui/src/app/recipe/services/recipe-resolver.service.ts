import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';

import { Observable, of } from 'rxjs';
import { Store } from '@ngxs/store';

import { Recipe } from '@app/model';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { LoadRecipe } from '@app/recipe/state/recipe.actions';
import { RecipeService } from './recipe.service';

@Injectable()
export class RecipeResolverService implements Resolve<Recipe> {
  constructor(
    private router: Router,
    private store: Store,
    private recipeService: RecipeService
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Recipe> {
    const id = +route.paramMap.get('id');
    if (isNaN(id)) {
      this.router.navigate(['/recipes']);
      return of(null);
    } else {
      this.recipeService.getRecipe(id).subscribe(
        recipe => this.store.dispatch(new LoadRecipe(recipe))
      );
      return this.store.selectOnce(RecipeState.getRecipe);
    }
   }
}
