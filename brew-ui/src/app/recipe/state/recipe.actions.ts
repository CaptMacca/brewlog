import { Recipe, RecipeRating } from '@app/recipe/model';

export class RemoveRecipe {
  static readonly type = '[Recipe] Remove Recipe';
  constructor(public payload: Recipe) {}
}

export class LoadRecipe {
  static readonly type = '[Recipe] Load Recipe';
  constructor(public payload: number) {}
}

export class LoadRecipes {
  static readonly type = '[Recipe] Load Recipes';
  constructor(public payload: string) {}
}

export class LoadTop5Recipes {
  static readonly type = '[Recipe] Load Top 5 Recipes';
  constructor(public payload: string) {}
}

export class UpdateRecipeRating {
  static readonly type = '[Recipe] Update Recipe Rating';
  constructor(public payload: RecipeRating) {}
}
