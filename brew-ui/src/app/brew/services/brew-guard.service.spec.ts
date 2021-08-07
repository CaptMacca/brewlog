import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { BrewEditGuard } from '@app/brew/services/brew-guard.service';
import { TestBed } from '@angular/core/testing';
import { NgxsModule, Store } from '@ngxs/store';
import { AuthState } from '@app/auth/state/auth.state';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { BrewState } from '@app/brew/state/brew.state';
import { BrewService } from '@app/brew/services/brew.service';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { MeasurementService } from '@app/brew/services/measurement.service';
import { AuthService } from '@app/auth/services/auth.service';
import { mockAuthService, mockRecipe } from '@app/recipe/spec/mock.recipe.components';
import { mockBrew } from '@app/brew/spec/mock.brews.components';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';
import { RouterTestingModule } from '@angular/router/testing';
import { BrewListComponent } from '@app/brew/components/brew-list/brew-list.component';
import { NzModalService } from 'ng-zorro-antd/modal';
import { NzMessageService } from 'ng-zorro-antd/message';
import { UiModule } from '@app/common/ui/ui.module';

const routes = [
  {
    path: 'main',
    children: [
      {
        path: 'brews', component: BrewListComponent
      }
    ]
  }
];

describe('BrewGuardService', () => {

  let brewDetailComponent: BrewDetailComponent;
  let service: BrewEditGuard;
  let store: Store;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        ReactiveFormsModule,
        NgxsModule.forRoot([AuthState, RecipeState, BrewState]),
        RouterTestingModule.withRoutes(routes),
        HttpClientTestingModule,
        RxReactiveFormsModule,
        UiModule
      ],
      providers: [
        BrewEditGuard,
        BrewDetailComponent,
        BrewService,
        RecipeService,
        MeasurementService,
        NzModalService,
        NzMessageService,
        {
          provide: AuthService,
          useValue: mockAuthService
        },
      ]
    })

    service = TestBed.inject(BrewEditGuard);
    store = TestBed.inject(Store);
    brewDetailComponent = TestBed.inject(BrewDetailComponent);
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
      },
      brews: {
        brew: mockBrew,
      }
    });

  });

  it('should route if unguarded', () => {
    spyOn(brewDetailComponent, 'confirmUnsaved').and.stub()
    brewDetailComponent.brewForm.markAsPristine();
    service.canDeactivate(brewDetailComponent).subscribe(response => {
      expect(response).toEqual(true);
    });
    // Should not prompt the user if form is pristine
    expect(brewDetailComponent.confirmUnsaved).toHaveBeenCalledTimes(0);
  });

  it('should deactivate if guarded and user confirms abandon edits', () => {
    spyOn(brewDetailComponent, 'confirmUnsaved').and.returnValue(of(true));
    brewDetailComponent.brewForm.markAsDirty();
    service.canDeactivate(brewDetailComponent).subscribe(response => {
      expect(response).toEqual(true);
    });
    expect(brewDetailComponent.confirmUnsaved).toHaveBeenCalled();
  });

  it('should not deactivate if guarded and user rejects abandon edits', () => {
    spyOn(brewDetailComponent, 'confirmUnsaved').and.returnValue(of(false));
    brewDetailComponent.brewForm.markAsDirty();
    service.canDeactivate(brewDetailComponent).subscribe(response => {
      expect(response).toEqual(false);
    });
    expect(brewDetailComponent.confirmUnsaved).toHaveBeenCalled();
  });

});
