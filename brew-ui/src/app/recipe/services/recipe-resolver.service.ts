import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Recipe } from '@app/recipe/model';
import { LoadRecipe } from '@app/recipe/state/recipe.actions';
import { Store } from '@ngxs/store';
import { Observable, of } from 'rxjs';

@Injectable()
export class RecipeResolverService  {
  constructor(
    private router: Router,
    private store: Store,
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<Recipe> {
    const id = +route.paramMap.get('id');
    if (isNaN(+id)) {
      this.router.navigate(['/main/recipes']);
      return of(null);
    } else {
      return this.store.dispatch(new LoadRecipe(+id));
    }
   }
}
