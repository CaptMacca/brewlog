import { State, Action, StateContext, Selector } from '@ngxs/store';
import { patch, removeItem, append } from '@ngxs/store/operators';

import { Recipe } from '@app/model';
import { LoadRecipes, LoadRecipe, RemoveRecipe, SelectRecipe, AddRecipe } from '@app/recipe/state/recipe.actions';

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

  constructor() { }

  @Action(LoadRecipes)
  LoadRecipes(ctx: StateContext<RecipeStateModel>, action: LoadRecipes) {
    return ctx.setState(patch({
      recipes: action.payload
    }));
  }

  @Action(LoadRecipe)
  LoadRecipe(ctx: StateContext<RecipeStateModel>, action: LoadRecipe) {
    return ctx.setState(patch({
      recipe: action.payload
    }));
  }

  @Action(RemoveRecipe)
  RemoveRecipe(ctx: StateContext<RecipeStateModel>, { payload }: RemoveRecipe) {
    return ctx.setState(patch({
      recipe: new Recipe(),
      recipes: removeItem(id => id === payload)
    }));
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
