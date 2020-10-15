import { Recipe } from '@app/recipe/model/recipe';
import { CandidateBrew } from '@app/brew/model/candidate-brew';

export interface CreateBrew {
  brew: CandidateBrew;
  recipe: Recipe;
}
