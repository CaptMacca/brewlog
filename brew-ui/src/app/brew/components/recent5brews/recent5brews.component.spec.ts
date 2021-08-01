import { render, RenderResult, within } from '@testing-library/angular';
import { Location } from '@angular/common';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { Store } from '@ngxs/store';
import { mockAuthService } from '@app/recipe/spec/mock.recipe.components';
import { UiModule } from '@app/common/ui/ui.module';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '@app/auth/services/auth.service';
import { Recent5brewsComponent } from '@app/brew/components/recent5brews/recent5brews.component';
import { BrewResolverService } from '@app/brew/services/brew-resolver.service';
import { mockBrews } from '@app/brew/spec/mock.brews.components';
import { BrewService } from '@app/brew/services/brew.service';
import { MeasurementService } from '@app/brew/services/measurement.service';
import userEvent from '@testing-library/user-event';

describe('Recent5BrewsComponent', () => {

  let component: RenderResult<Recent5brewsComponent>;
  let location: Location;
  let fixture: ComponentFixture<Recent5brewsComponent>;
  let debugElement: DebugElement;
  let instance: Recent5brewsComponent;
  let brewComponentSpy;

  beforeEach(async() => {
    brewComponentSpy = jasmine.createSpy();
    component = await render(Recent5brewsComponent, {
      imports: [
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
                {path: ':id', component: brewComponentSpy}
              ]
            }
          ]
        }
      ],
      providers: [
        BrewService,
        MeasurementService,
        BrewResolverService,
        {
          provide: AuthService,
          useValue: mockAuthService
        },
        Store,
      ],
      componentProperties: {
        brews: mockBrews
      }
    });
  });

  afterEach(() => {
    brewComponentSpy = jasmine.createSpy();
  });

  beforeEach(() => {
    fixture = component.fixture;
    instance = fixture.componentInstance as Recent5brewsComponent;
    debugElement = fixture.debugElement;
    location = TestBed.inject(Location);
  });

  it('should create component', async() => {
    expect(component).toBeTruthy();
  });

  it('should display recent 5 brews', async() => {
    const [columnsNames, ...rows] = within(component.getByRole('table')).getAllByRole('row');
    expect(rows.length).toEqual(mockBrews.length);
    expect(columnsNames.innerText).toContain('Name');
    expect(columnsNames.innerText).toContain('Date');
    expect(columnsNames.innerText).toContain('Rating');
  });

  it('should navigate to selected brew', async() => {
    const componentSpy = spyOn(instance, 'viewBrew').and.callThrough();
    const links = component.getAllByRole('link');
    await userEvent.click(links[0]);
    expect(componentSpy).toHaveBeenCalled();
    expect(location.path()).toEqual('/main/brews/' + mockBrews[0].id);
  });
});
