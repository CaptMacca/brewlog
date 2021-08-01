import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing'
import { RecipeService } from '@app/recipe/services/recipe.service';
import { mockRecipe, mockRecipes } from '@app/recipe/spec/mock.recipe.components';
import { environment } from '@env/environment';
import { RecipeRating } from '@app/recipe/model';

describe('RecipeService', () => {

  let httpTestingController: HttpTestingController;
  let service: RecipeService;
  const recipeApi = environment.recipeApiUrl;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        RecipeService
      ]
    });

    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(RecipeService);
  });

  afterEach(() => {
    httpTestingController.verify();
  })

  it('should create', () => {
    expect(service).toBeTruthy();
  });

  it('should return a recipe', (done) => {
    service.getRecipe(mockRecipe.id).subscribe(data => {
      expect(data).toEqual(mockRecipe);
      done();
    });

    const testRequest = httpTestingController.expectOne(recipeApi + '/' + mockRecipe.id);
    testRequest.flush(mockRecipe);
  });

  it('should return recipes for a user', (done) => {
    service.getRecipes('user').subscribe(data => {
      expect(data).toEqual(mockRecipes);
      done();
    });

    const testRequest = httpTestingController.expectOne(recipeApi + '?username=user');
    testRequest.flush(mockRecipes);
  });

  it('should return recipes notes for a recipe', (done) => {
    const testNote = 'My Notes';
    mockRecipe.notes = testNote
    service.getRecipeNotes(mockRecipe.id).subscribe(data => {
      expect(data).toEqual(testNote);
      done();
    });

    const testRequest = httpTestingController.expectOne(recipeApi + '/' + mockRecipe.id + '/notes');
    testRequest.flush(testNote);
  });

  it('should return top 5 recipes for a user', (done) => {
    service.getTop5RatedRecipes('user').subscribe(data => {
      expect(data).toEqual(mockRecipes);
      done();
    });

    const testRequest = httpTestingController.expectOne(recipeApi + '/top5?username=user');
    testRequest.flush(mockRecipes);
  });

  it('should delete a recipe', (done) => {
    service.deleteRecipe(mockRecipe).subscribe(
      () => {done()},
      error => { fail(error.message)}
    );

    const testRequest = httpTestingController.expectOne(recipeApi + '/' + mockRecipe.id);
    expect(testRequest.request.method).toBe('DELETE');
    testRequest.flush(null);
  });

  it('should update a recipes rating', (done) => {
    const recipeRating: RecipeRating = {
      rating: 5,
      recipe: mockRecipe
    }
    const updatedRecipe = {
      ...mockRecipe,
      rating: 5
    }
    service.updateRecipeRating(recipeRating).subscribe(data => {
      expect(data).toEqual(updatedRecipe);
      done();
    });

    const testRequest = httpTestingController.expectOne(recipeApi + '/' + mockRecipe.id + '/rating');
    expect(testRequest.request.method).toBe('PUT');
    testRequest.flush(updatedRecipe);
  })
})
