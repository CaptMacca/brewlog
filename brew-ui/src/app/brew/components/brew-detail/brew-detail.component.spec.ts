import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { render, RenderResult } from '@testing-library/angular';
import { Location } from '@angular/common';
import { DebugElement } from '@angular/core';
import { BrewService } from '@app/brew/services/brew.service';
import { NgxsModule, Store } from '@ngxs/store';
import { mockAuthService, MockAuthState, MockRecipeService, MockRecipeState } from '@app/recipe/spec/mock.recipe.components';
import { mockBrew, MockBrewService, MockBrewState } from '@app/brew/spec/mock.brews.components';
import { UiModule } from '@app/common/ui/ui.module';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';
import { MeasurementService } from '@app/brew/services/measurement.service';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { BrewResolverService } from '@app/brew/services/brew-resolver.service';
import { AuthService } from '@app/auth/services/auth.service';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import { BrewMeasurementsComponent } from '@app/brew/components/brew-detail/subcomponents/brew-measurements/brew-measurements.component';
import { BrewNotesComponent } from '@app/brew/components/brew-detail/subcomponents/brew-notes/brew-notes.component';
import { BrewSessionComponent } from '@app/brew/components/brew-detail/subcomponents/brew-session/brew-session.component';
import { of } from 'rxjs';

describe('BrewDetailComponent', () => {
  let component: RenderResult<BrewDetailComponent>
  let location: Location
  let fixture: ComponentFixture<BrewDetailComponent>
  let debugElement: DebugElement
  let instance: BrewDetailComponent
  let brewService: BrewService

  const renderOptions = {
    imports: [
      NgxsModule.forRoot([MockAuthState, MockRecipeState, MockBrewState]),
      UiModule,
      HttpClientModule,
      RouterTestingModule,
      ReactiveFormsModule,
      ReactiveFormsModule,
      RxReactiveFormsModule,
      UiModule,
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
      BrewMeasurementsComponent,
      BrewNotesComponent,
      BrewSessionComponent,
    ],
    exports: [
      BrewMeasurementsComponent,
      BrewNotesComponent,
      BrewSessionComponent,
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
    component = await render(BrewDetailComponent, renderOptions)
    fixture = component.fixture
    instance = fixture.componentInstance as BrewDetailComponent
    debugElement = fixture.debugElement
    location = TestBed.inject(Location)
    brewService = TestBed.inject(BrewService)
    spyOnProperty(instance, 'brew$').and.returnValue(of(mockBrew));
  })


  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
