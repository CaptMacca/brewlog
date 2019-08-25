import { Recipe } from '@app/model';

export class RemoveRecipe {
  static readonly type = '[Recipe] Remove Recipe';
  constructor(public payload: Recipe) {}
}

export class LoadRecipe {
  static readonly type = '[Recipe] Load Recipe';
  constructor(public payload: Recipe) {}
}

export class LoadRecipes {
  static readonly type = '[Recipe] Load Recipes';
  constructor(public payload: Recipe[]) {}
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
