import { Brew } from '@app/model/brew';
import { Recipe } from '@app/model/recipe';

export interface CreateBrew {
  username: string;
  brew: Brew;
  recipe: Recipe;
}
