import { async, ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SummaryComponent } from '@app/brew/components/brew-add/subcomponents/summary/summary.component';
import { render, RenderResult } from '@testing-library/angular';
import { UiModule } from '@app/common/ui/ui.module';
import { NgxsModule, Store } from '@ngxs/store';
import { mockAuthService, MockAuthState, MockRecipeService, MockRecipeState } from '@app/recipe/spec/mock.recipe.components';
import { MockBrewService, MockBrewState } from '@app/brew/spec/mock.brews.components';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { BrewMeasurementsComponent } from '@app/brew/components/brew-detail/subcomponents/brew-measurements/brew-measurements.component';
import { BrewNotesComponent } from '@app/brew/components/brew-detail/subcomponents/brew-notes/brew-notes.component';
import { BrewSessionComponent } from '@app/brew/components/brew-detail/subcomponents/brew-session/brew-session.component';
import { MeasurementService } from '@app/brew/services/measurement.service';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { BrewResolverService } from '@app/brew/services/brew-resolver.service';
import { AuthService } from '@app/auth/services/auth.service';
import { BrewService } from '@app/brew/services/brew.service';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import { BrewAddComponent } from '@app/brew/components/brew-add/brew-add.component';

describe('SummaryComponent', () => {
  let component: RenderResult<SummaryComponent>;
  let fixture: ComponentFixture<SummaryComponent>;

  const renderOptions = {
    imports: [
      NgxsModule.forRoot([MockAuthState, MockRecipeState, MockBrewState]),
      UiModule,
      HttpClientModule,
    ],
    routes: [
      {
        path: 'main',
        children: [
          {
            path: 'brews',
            children: [
              { path: ':id', component: BrewDetailComponent },
            ]
          }
        ]
      }
    ],
    declarations: [
      BrewAddComponent,
      SummaryComponent
    ],
    exports: [
      BrewAddComponent,
      SummaryComponent
    ],
    providers: [
      MeasurementService,
      {
        provide: RecipeService,
        useClass: MockRecipeService
      },
      BrewResolverService,
      {
        provide: AuthService,
        useValue: mockAuthService
      },
      {
        provide: BrewService,
        useClass: MockBrewService
      },
      { provide: NZ_I18N, useValue: en_US },
      Store,
    ],
  }

  beforeEach(async() => {
    component = await render(SummaryComponent, renderOptions)
    fixture = component.fixture
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
