import { TestBed } from '@angular/core/testing';
import { NgxsModule, Store } from '@ngxs/store';
import { mockAuthService, mockRecipe, mockRecipes } from '@app/recipe/spec/mock.recipe.components';
import { LoadRecipe, LoadRecipes, LoadTop5Recipes, RemoveRecipe, UpdateRecipeRating } from '@app/recipe/state/recipe.actions';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { of } from 'rxjs';
import { AuthState } from '@app/auth/state/auth.state';
import { AuthService } from '@app/auth/services/auth.service';
import { RecipeRating } from '@app/recipe/model';

describe('RecipeState', () => {

  let store: Store;
  let recipeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        NgxsModule.forRoot([AuthState, RecipeState]),
        HttpClientTestingModule,
      ],
      providers: [
        RecipeService,
        {
          provide: AuthService,
          useValue: mockAuthService
        },
      ]
    });

    recipeService = TestBed.inject(RecipeService);
    store = TestBed.inject(Store);
    // Setup mock state
    store.reset( {
      ...store.snapshot(),
        auth: {
          loggedIn: true,
          accessToken: 'token',
          username: 'user',
          authorities: []
        },
        recipes: {
          recipe: mockRecipe,
          recipes: mockRecipes,
          top5Recipes: mockRecipes
        }
    });

  });

  it('should load a recipe by id', () => {
    const recipeSpy = spyOn(recipeService, 'getRecipe').and.returnValue(of(mockRecipe));
    const notesSpy = spyOn(recipeService, 'getRecipeNotes').and.returnValue(of('Notes'));
    store.dispatch(new LoadRecipe(mockRecipe.id));
    const recipe = store.selectSnapshot(RecipeState.getRecipe);

    expect(recipeSpy).toHaveBeenCalledTimes(1);
    expect(notesSpy).toHaveBeenCalledTimes(1);
    expect(recipe).toBeTruthy();
    expect(recipe.id).toEqual(mockRecipe.id);
  });

  it('should load all recipes for a user', () => {
    const spy = spyOn(recipeService, 'getRecipes').and.returnValue(of(mockRecipes));
    store.dispatch(new LoadRecipes('user'));
    const recipes = store.selectSnapshot(RecipeState.getRecipes);

    expect(spy).toHaveBeenCalledTimes(1);
    expect(recipes).toBeTruthy();
    expect(recipes.length).toEqual(mockRecipes.length);
  });

  it('should load top 5 recipes for a user', () => {
    const spy = spyOn(recipeService, 'getTop5RatedRecipes').and.returnValue(of(mockRecipes));
    store.dispatch(new LoadTop5Recipes('user'));
    const recipes = store.selectSnapshot(RecipeState.getTop5Recipe);

    expect(spy).toHaveBeenCalledTimes(1);
    expect(recipes).toBeTruthy();
    expect(recipes.length).toEqual(mockRecipes.length);
  });

  it('should remove a recipe', async() => {
    const spy = spyOn(recipeService, 'deleteRecipe').and.returnValue(of(null));
    await store.dispatch(new RemoveRecipe(mockRecipe));
    const recipesAfter = store.selectSnapshot(RecipeState.getRecipes);
    expect(spy).toHaveBeenCalledTimes(1);
    expect(recipesAfter.length).toEqual(mockRecipes.length - 1);
  })

  it('should update recipe rating', () => {
    const rating = 5
    const updatedRecipe = {
      ...mockRecipe,
      rating: 5
    }
    const recipeRating: RecipeRating = {
      rating: rating,
      recipe: updatedRecipe
    }
    const spy = spyOn(recipeService, 'updateRecipeRating').and.returnValue(of(updatedRecipe));
    store.dispatch(new UpdateRecipeRating(recipeRating));
    const recipe = store.selectSnapshot(RecipeState.getRecipe);
    const recipes = store.selectSnapshot(RecipeState.getRecipes);
    const recipeFromRecipes = recipes.find(r => r.id === updatedRecipe.id);
    expect(spy).toHaveBeenCalledTimes(1);
    expect(recipe.rating).toEqual(rating);
    expect(recipeFromRecipes).toBeTruthy();
    expect(recipeFromRecipes.rating).toEqual(rating);
  });

});
