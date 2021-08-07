import { render, RenderResult, within } from '@testing-library/angular';
import { mockAuthService, MockAuthState, mockRecipes, MockRecipeService, MockRecipeState } from '@app/recipe/spec/mock.recipe.components';
import { UiModule } from '@app/common/ui/ui.module';
import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Top5recipesComponent } from '@app/recipe/components/top5recipes/top5recipes.component';
import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service';
import userEvent from '@testing-library/user-event';
import { Location } from '@angular/common';
import { NgxsModule, Store } from '@ngxs/store';
import { AuthService } from '@app/auth/services/auth.service';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Top5RecipesComponent', () => {

  let component: RenderResult<Top5recipesComponent>;
  let fixture: ComponentFixture<Top5recipesComponent>;
  let debugElement: DebugElement;
  let instance: Top5recipesComponent;
  let container: Element;
  let location: Location;
  let recipeComponentSpy;

  beforeEach(async() => {
    recipeComponentSpy = jasmine.createSpy();
    component = await render(Top5recipesComponent, {
      detectChanges: true,
      imports: [
        NgxsModule.forRoot([MockRecipeState, MockAuthState]),
        UiModule,
        HttpClientTestingModule,
      ],
      componentProperties: {
        recipes: mockRecipes,
      },
      providers: [
        Store,
        RecipeResolverService,
        {
          provide: AuthService,
          useValue: mockAuthService
        },
        {
          provide: RecipeService,
          useClass: MockRecipeService
        },
      ],
      routes: [
        {
          path: 'main',
          children: [
            {
              path: 'recipes',
              children: [
                { path: ':id', component: recipeComponentSpy }
              ]
            }
          ]
        }
      ],
    })
  });

  beforeEach(() => {
    fixture = component.fixture;
    debugElement = fixture.debugElement;
    instance = fixture.componentInstance as Top5recipesComponent;
    container = component.container;
    location = TestBed.inject(Location);
  });

  afterEach(() => {
    recipeComponentSpy = jasmine.createSpy();
  })

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should render list of recipes', async () => {
    const [columnsNames, ...rows] = within(component.getByRole('table')).getAllByRole('row');
    expect(rows.length).toEqual(mockRecipes.length);
    expect(columnsNames.innerText).toContain('Name');
    expect(columnsNames.innerText).toContain('Style');
    expect(columnsNames.innerText).toContain('Rating');
  });

  it('should navigate to selected recipe', async () => {
    const componentSpy = spyOn(instance, 'viewRecipe').and.callThrough();
    const links = component.getAllByRole('link');
    await userEvent.click(links[0]);
    expect(componentSpy).toHaveBeenCalled();
    expect(location.path()).toEqual('/main/recipes/' + mockRecipes[0].id);
  });

});
