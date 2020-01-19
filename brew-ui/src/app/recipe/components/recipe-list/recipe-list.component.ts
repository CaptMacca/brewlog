import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthState } from '@app/auth/state/auth.state';
import { Recipe } from '@app/model';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { LoadRecipes } from '@app/recipe/state/recipe.actions';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { Select, Store } from '@ngxs/store';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-recipe-list',
  styleUrls: ['./recipe-list.component.css'],
  templateUrl: './recipe-list.component.html'
})
export class RecipeListComponent implements OnInit {
  @Select(RecipeState.getRecipes) recipes$: Observable<Recipe>;

  constructor(
    private store: Store,
    private router: Router,
    private toastr: ToastrService,
    private recipeService: RecipeService
  ) {}

  ngOnInit(): void {
    const username = this.store.selectSnapshot(AuthState.getUsername);
    if (username) {
      this.recipeService.getRecipes(username).subscribe(
        recipes => {
        this.store.dispatch(new LoadRecipes(recipes));
      });
    }

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
