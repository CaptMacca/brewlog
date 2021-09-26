import { BrewResolverService } from '@app/brew/services/brew-resolver.service';
import { ActivatedRouteSnapshot, convertToParamMap } from '@angular/router';
import { BrewService } from '@app/brew/services/brew.service';
import { NgxsModule, Store } from '@ngxs/store';
import { Location } from '@angular/common';
import { BrewListComponent } from '@app/brew/components/brew-list/brew-list.component';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { BrewState } from '@app/brew/state/brew.state';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AuthService } from '@app/auth/services/auth.service';
import { AuthState } from '@app/auth/state/auth.state';
import { mockAuthService, mockRecipe, mockRecipes } from '@app/recipe/spec/mock.recipe.components';
import { mockBrew, mockBrews } from '@app/brew/spec/mock.brews.components';
import { of } from 'rxjs';
import { MeasurementService } from '@app/brew/services/measurement.service';

describe('BrewResolverService', () => {

  let resolver: BrewResolverService;
  let route: ActivatedRouteSnapshot;
  let store: Store;
  let brewService: BrewService;
  let location: Location;

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

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes(routes),
        NgxsModule.forRoot([AuthState, BrewState]),
        HttpClientTestingModule,
      ],
      providers: [
        BrewService,
        MeasurementService,
        BrewResolverService,
        {
          provide: AuthService,
          useValue: mockAuthService
        },
      ]
    });

    route = new ActivatedRouteSnapshot();
    resolver = TestBed.inject(BrewResolverService);
    store = TestBed.inject(Store);
    brewService = TestBed.inject(BrewService);
    location = TestBed.inject(Location);
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
      },
      brews: {
        brew: mockBrew,
        brews: mockBrews,
        recent5Brews: mockBrews
      }
    });
  })

  it('should resolve the brew from the supplied id', () => {
    const brewSpy = spyOn(brewService, 'getBrew').and.returnValue(of(mockBrew));
    route.params = convertToParamMap({ id: mockBrew.id });
    resolver.resolve(route).subscribe(brew => {
      expect(brew).toBeTruthy();
      expect(brew).toEqual(mockBrew);
    });
    expect(brewSpy).toHaveBeenCalled();
  });
});
