import { async, TestBed } from '@angular/core/testing';

import { AppComponent } from './app.component';
import { NavigationComponent } from './navigation/navigation.component';
import { AppRoutingModule } from './app-routing.module';
import { WelcomeComponent } from './welcome/welcome.component';
import { BrewListComponent } from '@app/brew/components/brew-list/brew-list.component';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { BrewAddComponent } from '@app/brew/components/brew-add/brew-add.component';
import { RecipeListComponent } from '@app/recipe/components/recipe-list/recipe-list.component';
import { ImportRecipeComponent } from '@app/recipe/components/import-recipe/import-recipe.component';
import { RecipeDetailComponent } from '@app/recipe/components/recipe-detail/recipe-detail.component';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        NavigationComponent,
        WelcomeComponent,
        RecipeListComponent,
        ImportRecipeComponent,
        RecipeDetailComponent,
        BrewListComponent,
        BrewDetailComponent,
        BrewAddComponent,
      ],
      imports: [
        AppRoutingModule
      ],
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

});
