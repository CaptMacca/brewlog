import { Recipe } from '@app/recipe/model';
import { LoadRecipe, LoadRecipes, LoadTop5Recipes, RemoveRecipe, UpdateRecipeRating } from '@app/recipe/state/recipe.actions';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { patch, removeItem, updateItem } from '@ngxs/store/operators';
import { RecipeService } from '../services/recipe.service';
import { forkJoin, Observable } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { Injectable } from '@angular/core';

export class RecipeStateModel {
  top5Recipes: [];
  recipes: Recipe[];
  recipe: Recipe;
}

@State<RecipeStateModel>({
  name: 'recipes',
  defaults: {
    top5Recipes: [],
    recipes: [],
    recipe: new Recipe()
  }
})
@Injectable()
export class RecipeState {
  @Selector()
  static getRecipes(state: RecipeStateModel): Recipe[] {
    return state.recipes;
  }

  @Selector()
  static getRecipe(state: RecipeStateModel): Recipe {
    return state.recipe;
  }

  @Selector()
  static getTop5Recipe(state: RecipeStateModel): Recipe[] {
    return state.top5Recipes;
  }

  constructor(private readonly recipeService: RecipeService) {}

  @Action(LoadRecipes)
  LoadRecipes(ctx: StateContext<RecipeStateModel>, action: LoadRecipes) {
    return this.recipeService.getRecipes(action.payload).subscribe(recipes =>
      ctx.setState(patch({ recipes: recipes }))
    );
  }

  @Action(LoadTop5Recipes)
  LoadTop5Recipes(ctx: StateContext<RecipeStateModel>, action: LoadRecipes) {
    return this.recipeService.getTop5RatedRecipes(action.payload).subscribe(recipes =>
      ctx.setState(patch({ top5Recipes: recipes }))
    );
  }

  @Action(LoadRecipe)
  LoadRecipe(ctx: StateContext<RecipeStateModel>, action: LoadRecipe): Observable<Recipe> {

    const recipe$ = this.recipeService.getRecipe(action.payload);
    const notes$ = this.recipeService.getRecipeNotes(action.payload);

    return forkJoin([recipe$, notes$]).pipe(
      retry(1),
      map(results => {
        const recipeWithNotes = {
          ...results[0],
          notes: results[1],
        } as Recipe;
        ctx.setState(patch({
          recipe: recipeWithNotes
        }))
        return recipeWithNotes;
      }),
      catchError(err => {
        console.log(err);
        throw new Error(err);
      })
    );
  }

  @Action(RemoveRecipe)
  RemoveRecipe(ctx: StateContext<RecipeStateModel>, { payload }: RemoveRecipe) {
    return this.recipeService.deleteRecipe(payload).subscribe(() => {
      return ctx.setState(patch({
          recipe: new Recipe(),
          recipes: removeItem<Recipe>(r => r.id === payload.id)
        })
      );
    });
  }

  @Action(UpdateRecipeRating)
  UpdateRecipeRating(ctx: StateContext<RecipeStateModel>, { payload }: UpdateRecipeRating) {
    return this.recipeService.updateRecipeRating(payload).subscribe(
      recipe => ctx.setState(patch({
        recipe: recipe,
        recipes: updateItem<Recipe>(r => r.id === payload.recipe.id, payload.recipe)
      }))
    );
  }
}
