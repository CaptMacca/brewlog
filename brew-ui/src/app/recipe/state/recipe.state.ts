import { tap } from 'rxjs/operators';

import { State, Action, StateContext, Selector } from '@ngxs/store';
import { patch, removeItem, append } from '@ngxs/store/operators';

import { Recipe, Fermentable, Hop, Yeast } from '@app/model';
import { LoadRecipes, LoadRecipe, RemoveRecipe, SelectRecipe, AddRecipe } from '@app/recipe/state/recipe.actions';
import { RecipeService } from '@app/recipe/services/recipe.service';

export class RecipeStateModel {
  recipes: Recipe[];
  recipe: Recipe;
}

@State<RecipeStateModel>({
  name: 'recipes',
  defaults: {
    recipes: [],
    recipe: new Recipe()
  }
})

export class RecipeState {

  @Selector()
  static getRecipes(state: RecipeStateModel) {
      return state.recipes;
  }

  @Selector()
  static getRecipe(state: RecipeStateModel) {
      return state.recipe;
  }

  constructor(private recipeService: RecipeService) { }

  @Action(LoadRecipes)
  LoadRecipes(ctx: StateContext<RecipeStateModel>, action: LoadRecipes) {
    return this.recipeService.getRecipes(action.payload).subscribe(
      recipes => ctx.setState(patch({
        recipes: recipes
      }))
    );
  }

  @Action(LoadRecipe)
  LoadRecipe(ctx: StateContext<RecipeStateModel>, action: LoadRecipe) {
    return this.recipeService.getRecipe(+action.payload).pipe(
      tap(recipe => {
        if (recipe) {
            recipe.fermentables = recipe.ingredients.filter(i => i.type === 'Fermentable') as Fermentable[];
            recipe.hops = recipe.ingredients.filter(i => i.type === 'Hop') as Hop[];
            recipe.yeasts = recipe.ingredients.filter(i => i.type === 'Yeast') as Yeast[];
        } else {
            recipe = new Recipe();
        }
        ctx.setState(patch({
            recipe: recipe
        }));
      })
    );
  }

  @Action(RemoveRecipe)
  RemoveRecipe(ctx: StateContext<RecipeStateModel>, { payload }: RemoveRecipe) {
    return this.recipeService.deleteRecipe(+payload).pipe(
      tap(() => ctx.setState(patch({
        recipe: new Recipe(),
        recipes: removeItem(id => id === payload)
      }))
    ));
  }

  @Action(SelectRecipe)
  SelectRecipe(ctx: StateContext<RecipeStateModel>, { payload }: SelectRecipe) {
    return ctx.setState(patch({
      recipe: payload
    }));
  }

  @Action(AddRecipe)
  AddRecipe(ctx: StateContext<RecipeStateModel>, { payload }: AddRecipe) {
    return ctx.setState(patch({
      recipes: append([payload])
    }));
  }
}
