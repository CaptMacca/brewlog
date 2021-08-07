import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service';
import { ActivatedRouteSnapshot, convertToParamMap } from '@angular/router';
import { NgxsModule, Store } from '@ngxs/store';
import { TestBed } from '@angular/core/testing';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { AuthState } from '@app/auth/state/auth.state';
import { mockAuthService, mockRecipe, mockRecipes } from '@app/recipe/spec/mock.recipe.components';
import { of } from 'rxjs';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AuthService } from '@app/auth/services/auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { Location } from '@angular/common';
import { RecipeListComponent } from '@app/recipe/components/recipe-list/recipe-list.component';

describe('RecipeResolverService', () => {

  let resolver: RecipeResolverService;
  let route: ActivatedRouteSnapshot;
  let store: Store;
  let recipeService: RecipeService;
  let location: Location;

  const routes = [
    {
      path: 'main',
      children: [
        {
          path: 'recipes', component: RecipeListComponent
        }
      ]
    }
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes(routes),
        NgxsModule.forRoot([AuthState, RecipeState]),
        HttpClientTestingModule,
      ],
      providers: [
        RecipeService,
        RecipeResolverService,
        {
          provide: AuthService,
          useValue: mockAuthService
        },
      ]
    })

    route = new ActivatedRouteSnapshot();
    resolver = TestBed.inject(RecipeResolverService);
    store = TestBed.inject(Store);
    recipeService = TestBed.inject(RecipeService);
    location = TestBed.inject(Location);

    store.reset({
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

  it('should resolve the recipe from the supplied id', () => {
    const recipeSpy = spyOn(recipeService, 'getRecipe').and.returnValue(of(mockRecipe));
    route.params = convertToParamMap({ id: mockRecipe.id });
    resolver.resolve(route).subscribe(recipe => {
      expect(recipe).toBeTruthy();
      expect(recipe).toEqual(mockRecipe);
      }
    );
    expect(recipeSpy).toHaveBeenCalledTimes(1);
  });
})
