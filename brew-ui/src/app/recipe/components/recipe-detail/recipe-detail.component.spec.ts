import { RecipeDetailComponent } from './recipe-detail.component';
import { NgxsModule, Store } from '@ngxs/store';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { mockAuthService, MockAuthState, mockRecipe, MockRecipeService, MockRecipeState } from '@app/recipe/spec/mock.recipe.components';
import { fireEvent, render, RenderResult, screen, within } from '@testing-library/angular';
import { RecipeListComponent } from '@app/recipe/components/recipe-list/recipe-list.component';
import { UiModule } from '@app/common/ui/ui.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ImportRecipeComponent } from '@app/recipe/components/import-recipe/import-recipe.component';
import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { AuthService } from '@app/auth/services/auth.service';
import { AbvGaugeComponent } from '@app/recipe/components/abv-gauge/abv-gauge.component';
import { IbuGaugeComponent } from '@app/recipe/components/ibu-gauge/ibu-gauge.component';
import { IbuFormatPipe } from '@app/recipe/pipes/ibu-format.pipe';
import { AbvFormatPipe } from '@app/recipe/pipes/abv-format.pipe';
import { RecipeColourComponent } from '@app/recipe/components/recipe-colour/recipe-colour.component';
import { NgxGaugeModule } from 'ngx-gauge';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { NzTableComponent } from 'ng-zorro-antd/table';
import { of } from 'rxjs';
import { Location } from '@angular/common';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import userEvent from '@testing-library/user-event';
import { BrewListComponent } from '@app/brew/components/brew-list/brew-list.component';
import { BrewAddComponent } from '@app/brew/components/brew-add/brew-add.component';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { BrewResolverService } from '@app/brew/services/brew-resolver.service';
import { BrewEditGuard } from '@app/brew/services/brew-guard.service';
import { DebugElement } from '@angular/core';
import { NzRateComponent } from 'ng-zorro-antd/rate';
import { NgMathPipesModule } from 'ngx-pipes';
import { NzRowDirective } from 'ng-zorro-antd/grid';

describe('RecipeDetailComponent', () => {
  let component: RenderResult<RecipeDetailComponent>;
  let location: Location;
  let fixture: ComponentFixture<RecipeDetailComponent>;
  let debugElement: DebugElement;
  let instance: RecipeDetailComponent;
  let container: Element;

  const renderOptions = {
    detectChanges: true,
    imports: [
      NgxsModule.forRoot([MockRecipeState, MockAuthState]),
      UiModule,
      HttpClientTestingModule,
      FormsModule,
      NgxGaugeModule,
      NgMathPipesModule,
    ],
    routes: [
      {
        path: 'main',
        children: [
          {
            path: 'recipes',
            children: [
              {path: '', component: RecipeListComponent },
              {path: 'import', component: ImportRecipeComponent },
              {path: ':id', component: RecipeDetailComponent, resolve: {recipe: RecipeResolverService }}
            ]
          },
          {
            path: 'brews',
            children: [
              {path: '', component: BrewListComponent },
              {path: 'add', component: BrewAddComponent },
              {path: ':id', component: BrewDetailComponent, resolve: {brew: BrewResolverService}, canDeactivate: [BrewEditGuard]},
            ]
          }
        ]
      }
    ],
    providers: [
      NzMessageService,
      NzModalService,
      {
        provide: AuthService,
        useValue: mockAuthService
      },
      {
        provide: RecipeService,
        useClass: MockRecipeService
      },
      Store,
      RecipeResolverService,
    ],
    declarations: [
      AbvGaugeComponent,
      IbuGaugeComponent,
      IbuFormatPipe,
      AbvFormatPipe,
      RecipeColourComponent,
    ],
    componentProperties: {
      rating: 0
    }
  };

  beforeEach(async () => {
    component = await render(RecipeDetailComponent, renderOptions);
  });

  beforeEach(() => {
    fixture = component.fixture;
    debugElement = fixture.debugElement;
    instance = fixture.componentInstance as RecipeDetailComponent;
    container = component.container;
    location = TestBed.inject(Location);
    spyOnProperty(instance, 'recipe$').and.returnValue(of(mockRecipe));
  });

  it('should create the component', async() => {
    expect(component).toBeTruthy();
  });

  it('should render the loaded recipe', async() => {
    expect(screen.getByText(mockRecipe.name)).toBeTruthy();
    expect(screen.getByText(mockRecipe.type)).toBeTruthy();
  });

  describe('render tabs', () => {

    let tabs, columns, rows, table;

    beforeEach(() => {
      tabs = screen.getAllByRole('tab')
    })

    it('should render all the tabs', async() => {
      expect(tabs.length).toBe(5);
      expect(tabs[0].innerText).toContain('Fermentables')
      expect(tabs[1].innerText).toContain('Hops');
      expect(tabs[2].innerText).toContain('Yeast');
      expect(tabs[3].innerText).toContain('Mash Schedule');
      expect(tabs[4].innerText).toContain('Notes');
    });

    it('should render the fermentables tab', async() => {
      fireEvent.click(tabs[0]);
      [columns, ...rows] = screen.getAllByRole('row');
      expect(rows.length).toEqual(mockRecipe.fermentables.length);
      expect(columns.innerText).toContain('Name');
      expect(columns.innerText).toContain('Amount');
    });

    it('should render the hops tab', async() => {
      fireEvent.click(tabs[1]);
      [columns, ...rows] = screen.getAllByRole('row');
      expect(rows.length).toEqual(mockRecipe.hops.length);
      expect(columns.innerText).toContain('Name');
      expect(columns.innerText).toContain('Amount');
      expect(columns.innerText).toContain('Alpha');
      expect(columns.innerText).toContain('Addition Time');
    });

    it('should render the yeast tab', async() => {
      fireEvent.click(tabs[2]);
      [columns, ...rows] = screen.getAllByRole('row');
      expect(rows.length).toEqual(mockRecipe.yeasts.length);
      expect(columns.innerText).toContain('Name');
      expect(columns.innerText).toContain('Amount');
    });

    it('should render the mash tab', async() => {
      fireEvent.click(tabs[3]);
      [columns, ...rows] = screen.getAllByRole('row');
      expect(rows.length).toEqual(mockRecipe.mashes.length);
      expect(columns.innerText).toContain('Name');
      expect(columns.innerText).toContain('Step Temp');
      expect(columns.innerText).toContain('Step Time');
    });

    it('should render the notes tab', async() => {
      await fireEvent.click(tabs[4]);
      expect(screen.getByText(mockRecipe.notes)).toBeTruthy();
    });
  });

  describe('modify recipe', () => {
    it('should update recipe rating', async() => {
      const componentSpy = spyOn(instance, 'updateRating');
      const rate = debugElement.query(By.directive(NzRateComponent));
      // Select the 4th star on the rate component and click it
      rate.nativeElement.firstElementChild.children[3].firstElementChild.firstElementChild.click();
      expect(instance.rating).toEqual(4);
      expect(componentSpy).toHaveBeenCalled();
    });

    it('should delete a recipe', async() => {
      const componentSpy = spyOn(instance, 'confirm').and.callThrough();
      fireEvent.click(screen.getByRole('button', { name: 'Delete Recipe' }));
      // Wait for the confirm dialog to appear
      await screen.findByRole('dialog');
      const yesBtn = await screen.findByRole('button', { name: 'Yes'});
      fireEvent.click(yesBtn.closest('button'));
      expect(componentSpy).toHaveBeenCalled();
    });
  });

  describe('navigation', () => {
    it('should navigate to brew recipe', async() => {
      const componentSpy = spyOn(instance, 'brewRecipe').and.callThrough();
      await userEvent.click(screen.getByRole('button', { name: 'Brew Recipe' }));
      expect(componentSpy).toHaveBeenCalled();
      expect(location.path()).toBe('/main/brews/add');
    });

    it('should navigate to view recipes', async() => {
      const componentSpy = spyOn(instance, 'gotoRecipes').and.callThrough();
      await userEvent.click(screen.getByRole('button', { name: 'View Recipes' }));
      expect(componentSpy).toHaveBeenCalled();
      expect(location.path()).toBe('/main/recipes');
    });
  });
});
