import { Recipe } from '@app/model';
import { UpdateRating } from '@app/model/update-rating';

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

export class LoadAllRecipes {
  static readonly type = '[Recipe] Load All Recipes';
}

export class SelectRecipe {
  static readonly type = '[Recipe] Select Recipe';
  constructor(public payload: Recipe) {}
}

export class AddRecipe {
  static readonly type = '[Recipe] Add Recipe';
  constructor(public payload: Recipe) {}
}

export class UpdateRecipeRating {
  static readonly type = '[Recipe] Update Recipe Rating';
  constructor(public payload: UpdateRating) {}
}
