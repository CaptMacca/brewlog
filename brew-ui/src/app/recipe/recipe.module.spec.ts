import { TestBed } from '@angular/core/testing';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service';
import { RecipeModule } from '@app/recipe/recipe.module';

describe('Recipe Module', () => {

  let recipeModule: RecipeModule;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
      providers: [
        RecipeService, RecipeResolverService
      ]
    });

    recipeModule = new RecipeModule();
  });

  it('should create', () => {
    expect(recipeModule).toBeTruthy();
  });
});
