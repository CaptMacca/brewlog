import { Recipe } from '@app/model';
import {
  AddRecipe,
  LoadRecipe,
  LoadRecipes,
  LoadTop5Recipes,
  RemoveRecipe,
  SelectRecipe,
  UpdateRecipeRating
} from '@app/recipe/state/recipe.actions';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { append, patch, removeItem } from '@ngxs/store/operators';
import { RecipeService } from '../services/recipe.service';

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
      ctx.setState(
        patch({
          recipes: recipes
        })
      )
    );
  }

  @Action(LoadTop5Recipes)
  LoadTop5Recipes(ctx: StateContext<RecipeStateModel>, action: LoadRecipes) {
    return this.recipeService
      .getTop5RatedRecipes(action.payload)
      .subscribe(recipes =>
        ctx.setState(
          patch({
            top5Recipes: recipes
          })
        )
      );
  }

  @Action(LoadRecipe)
  LoadRecipe(ctx: StateContext<RecipeStateModel>, action: LoadRecipe) {
    return this.recipeService.getRecipe(+action.payload).subscribe(recipe =>
      ctx.setState(
        patch({
          recipe: recipe
        })
      )
    );
  }

  @Action(RemoveRecipe)
  RemoveRecipe(ctx: StateContext<RecipeStateModel>, { payload }: RemoveRecipe) {
    return this.recipeService.deleteRecipe(payload).subscribe(() => {
      return ctx.setState(
        patch({
          recipe: new Recipe(),
          recipes: removeItem(r => r === payload.id)
        })
      );
    });
  }

  @Action(SelectRecipe)
  SelectRecipe(ctx: StateContext<RecipeStateModel>, { payload }: SelectRecipe) {
    return ctx.setState(
      patch({
        recipe: payload
      })
    );
  }

  @Action(AddRecipe)
  AddRecipe(ctx: StateContext<RecipeStateModel>, { payload }: AddRecipe) {
    return ctx.setState(
      patch({
        recipes: append([payload])
      })
    );
  }

  @Action(UpdateRecipeRating)
  UpdateRecipeRating(ctx: StateContext<RecipeStateModel>, { payload }: UpdateRecipeRating) {
    return this.recipeService.updateRecipeRating(payload).subscribe(
      recipe => {
        return ctx.setState(
          patch({
            recipe: recipe
          })
        );
    });
  }
}
