import { Recipe, RecipeRating } from '@app/recipe/model';
import { Observable, of } from 'rxjs';
import { State } from '@ngxs/store';
import { AuthState, AuthStateModel } from '@app/auth/state/auth.state';
import { Injectable } from '@angular/core';
import { RecipeState, RecipeStateModel } from '@app/recipe/state/recipe.state';
import { HttpResponse } from '@angular/common/http';
import { RecipeService } from '@app/recipe/services/recipe.service';

export const mockRecipe: Recipe = {
  id: 1,
  name: 'My First Recipe',
  rating: 2,
  type: 'All Grain',
  style: 'IPA',
  estimatedAbv: '6.5',
  estimatedColour: '10 EBC',
  estimatedIbu: '60',
  batchSize: '21L',
  boilTime: '60mins',
  finalGravity: '1014',
  originalGravity: '1060',
  notes: 'My brew notes',
  fermentables: [
    {
      id: 1,
      name: 'Pale Malt',
      amount: 5.6,
      type: 'grain',
      addAfterBoil: false
    },
    {
      id: 2,
      name: 'Crystal Malt',
      amount: 0.6,
      type: 'grain',
      addAfterBoil: false
    },
  ],
  hops: [
    {
      id: 1,
      name: 'Centennial',
      addAfterBoil: false,
      additionTime: '60',
      alpha: 12.0,
      amount: 30,
      hopUsage: 'Boil',
      type: 'Pellet'
    },
    {
      id: 2,
      name: 'Centennial',
      addAfterBoil: false,
      additionTime: '15',
      alpha: 12.0,
      amount: 30,
      hopUsage: 'Boil',
      type: 'Pellet'
    },
    {
      id: 3,
      name: 'Centennial',
      addAfterBoil: false,
      additionTime: '0',
      alpha: 12.0,
      amount: 60,
      hopUsage: 'Whirlpool',
      type: 'Pellet'
    },
  ],
  yeasts: [
    {
      id: 1,
      type: 'Dry',
      amount: 11,
      name: 'US-05'
    }
  ],
  mashes: [
    {
      id: 1,
      name: 'Mash in',
      stepTemp: '64',
      stepTime: '60'
    },
    {
      id: 2,
      name: 'Mash out',
      stepTemp: '78.4',
      stepTime: '10'
    },
  ]
}

export const mockRecipes: Recipe[] = [
  {...mockRecipe, id: 1, name: 'My First Recipe', rating: 4, style: 'IPA', type: 'All Grain', estimatedAbv: '6.5'},
  {...mockRecipe, id: 2, name: 'My Second Recipe', rating: 2, style: 'Pils', type: 'All Grain', estimatedAbv: '5'},
  {...mockRecipe, id: 3, name: 'My Third Recipe', rating: 5, style: 'XPA', type: 'All Grain', estimatedAbv: '5.5'},
  {...mockRecipe, id: 4, name: 'My Fourth Recipe', rating: 1, style: 'Mild', type: 'All Grain', estimatedAbv: '3.5'},
  {...mockRecipe, id: 5, name: 'My Fifth Recipe', rating: 3, style: 'Stout', type: 'All Grain', estimatedAbv: '5'},
];


@Injectable()
export class MockRecipeService extends RecipeService {

  getRecipe(id: number): Observable<Recipe> {
    return of(mockRecipe);
  };

  updateRecipeRating(updateRating: RecipeRating): Observable<Recipe> {
    mockRecipe.rating = updateRating.rating
    return of(mockRecipe);
  };

  deleteRecipe(recipe: Recipe) {
    return of(new HttpResponse({ status: 204 }));
  };
}

@Injectable()
export class MockAuthService {
  isAuthenticated(token) {
    return true;
  }
}

export const mockAuthService = new MockAuthService();

@State<AuthStateModel>({
  name: 'auth',
  defaults: {
    loggedIn: true,
    accessToken: 'token',
    username: 'user',
    authorities: []
  }
})
@Injectable()
export class MockAuthState extends AuthState {
}

@State<RecipeStateModel>({
  name: 'recipes',
  defaults: {
    top5Recipes: [],
    recipes: mockRecipes,
    recipe: mockRecipe,
  }
})
@Injectable()
export class MockRecipeState extends RecipeState {
}
