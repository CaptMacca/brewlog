import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrewAddComponent } from '@app/brew/components/brew-add/brew-add.component';
import { render, RenderResult, screen, within } from '@testing-library/angular';
import { Location } from '@angular/common';
import { DebugElement } from '@angular/core';
import { BrewService } from '@app/brew/services/brew.service';
import { NgxsModule, Store } from '@ngxs/store';
import { mockAuthService, MockAuthState, mockRecipes, MockRecipeState } from '@app/recipe/spec/mock.recipe.components';
import { MockBrewService, MockBrewState } from '@app/brew/spec/mock.brews.components';
import { UiModule } from '@app/common/ui/ui.module';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { MeasurementService } from '@app/brew/services/measurement.service';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { BrewResolverService } from '@app/brew/services/brew-resolver.service';
import { AuthService } from '@app/auth/services/auth.service';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';
import { ReactiveFormsModule } from '@angular/forms';
import { IntialEstimatesComponent } from '@app/brew/components/brew-add/subcomponents/initial-estimates/intial-estimates.component';
import { SummaryComponent } from '@app/brew/components/brew-add/subcomponents/summary/summary.component';
import { SelectRecipeComponent } from '@app/recipe/components/select-recipe/select-recipe.component';
import userEvent from '@testing-library/user-event';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';

describe('BrewAddComponent', () => {
  let component: RenderResult<BrewAddComponent>
  let location: Location
  let fixture: ComponentFixture<BrewAddComponent>
  let debugElement: DebugElement
  let instance: BrewAddComponent
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
      IntialEstimatesComponent,
      SummaryComponent,
      SelectRecipeComponent,
    ],
    exports: [
      IntialEstimatesComponent,
      SummaryComponent,
    ],
    providers: [
      MeasurementService,
      RecipeService,
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
    component = await render(BrewAddComponent, renderOptions)
    fixture = component.fixture
    instance = fixture.componentInstance as BrewAddComponent
    debugElement = fixture.debugElement
    location = TestBed.inject(Location)
    brewService = TestBed.inject(BrewService)
  })

  it('should create component', () => {
    expect(component).toBeTruthy()
  })

  it('should enable next button when recipe is selected on page 1', () => {
    const nextBtn = component.getByRole('button', { name: 'Next' })
    const prevBtn = component.getByRole('button', { name: 'Prev' })
    const checkBoxes = component.getAllByRole('checkbox')
    expect(nextBtn.closest('button').disabled).toBeTrue()
    userEvent.click(checkBoxes[0])
    expect(nextBtn.closest('button').disabled).toBeFalse()
    expect(prevBtn.closest('button').disabled).toBeTrue()
  })

  it('should disable next button when recipe is unselected on page 1', () => {
    const nextBtn = component.getByRole('button', { name: 'Next' })
    const prevBtn = component.getByRole('button', { name: 'Prev' })
    const checkBoxes = component.getAllByRole('checkbox')
    userEvent.click(checkBoxes[0])
    expect(nextBtn.closest('button').disabled).toBeFalse()
    userEvent.click(checkBoxes[0])
    expect(nextBtn.closest('button').disabled).toBeTrue()
    expect(prevBtn.closest('button').disabled).toBeTrue()
  })

  it('should display the list of recipes on first page', () => {
    const brewsTable = component.getByRole(/table/)
    const [columnNames, ...rows] = within(brewsTable).getAllByRole('row')
    expect(columnNames.innerText).toContain('Recipe Name')
    expect(columnNames.innerText).toContain('Style')
    expect(columnNames.innerText).toContain('Type')
    expect(rows.length).toEqual(mockRecipes.length)
    expect(screen.getByText(/My First Recipe/i))
    expect(screen.getByText(/My Second Recipe/i))
    expect(screen.getByText(/My Third Recipe/i))
  })

  it('should allow progress to summary when recipe selected and all initial details entered', () => {
    userEvent.click(component.getAllByRole('checkbox')[0])
    userEvent.click(component.getByRole('button', { name: 'Next' }))
    expect(instance.selectedRecipe.name).toEqual('My First Recipe')
    // const brewDate = debugElement.query(By.directive(NzDatePickerComponent)).nativeNode.children[0].children[0].firstChild
    // console.log(brewDate)
    // const estPreBoilGravity = debugElement.queryAll(By.directive(NzInputNumberComponent))[0].nativeElement
    // const estOriginalGravity = debugElement.queryAll(By.directive(NzInputNumberComponent))[1].nativeElement
    // const estFinalGravity = debugElement.queryAll(By.directive(NzInputNumberComponent))[2].nativeElement
    // const estFermentVol = debugElement.queryAll(By.directive(NzInputNumberComponent))[3].nativeElement
    // const estBottleVol = debugElement.queryAll(By.directive(NzInputNumberComponent))[4].nativeElement;
    // const inputValues = {
    //   brewDate: '26/06/2021',
    //   estPreBoilGravity: '1040',
    //   estOriginalGravity: '1052',
    //   estFinalGravity: '1012',
    //   estFermentVol: '21L',
    //   estBottleVol: '19L'
    // }
    // userEvent.paste(brewDate, inputValues.brewDate)
    // // userEvent.type(estPreBoilGravity, inputValues.estPreBoilGravity)
    // // userEvent.type(estOriginalGravity, inputValues.estOriginalGravity)
    // // userEvent.type(estFinalGravity, inputValues.estFinalGravity)
    // // userEvent.type(estFermentVol, inputValues.estFermentVol)
    // // userEvent.type(estBottleVol, inputValues.estBottleVol)
    // userEvent.click(component.getByRole('button', { name: 'Next' }))
    // expect(screen.getByText(/Summary/));
  })
})
