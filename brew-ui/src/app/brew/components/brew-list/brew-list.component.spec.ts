import { async, ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing'

import { BrewListComponent } from './brew-list.component'
import { fireEvent, render, RenderResult, screen, within } from '@testing-library/angular'
import { Location } from '@angular/common'
import { DebugElement } from '@angular/core'
import { UiModule } from '@app/common/ui/ui.module'
import { HttpClientModule } from '@angular/common/http'
import { BrewService } from '@app/brew/services/brew.service'
import { MeasurementService } from '@app/brew/services/measurement.service'
import { BrewResolverService } from '@app/brew/services/brew-resolver.service'
import { AuthService } from '@app/auth/services/auth.service'
import { mockAuthService, MockAuthState, MockRecipeService, MockRecipeState } from '@app/recipe/spec/mock.recipe.components'
import { NgxsModule, Store } from '@ngxs/store'
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component'
import { RecipeService } from '@app/recipe/services/recipe.service'
import { mockBrew, mockBrews, MockBrewService, MockBrewState } from '@app/brew/spec/mock.brews.components'
import { RouterTestingModule } from '@angular/router/testing'
import userEvent from '@testing-library/user-event';
import { BrewAddComponent } from '@app/brew/components/brew-add/brew-add.component';


describe('BrewListComponent', () => {

  let component: RenderResult<BrewListComponent>
  let location: Location
  let fixture: ComponentFixture<BrewListComponent>
  let debugElement: DebugElement
  let instance: BrewListComponent
  let brewService: BrewService

  const renderOptions = {
    imports: [
      NgxsModule.forRoot([MockAuthState, MockRecipeState, MockBrewState]),
      UiModule,
      HttpClientModule,
      RouterTestingModule,
    ],
    routes: [
      {
        path: 'main',
        children: [
          {
            path: 'brews',
            children: [
              { path: ':id', component: BrewDetailComponent },
              { path: 'add', component: BrewAddComponent },
            ]
          }
        ]
      }
    ],
    providers: [
      MeasurementService,
      BrewResolverService,
      {
        provide: AuthService,
        useValue: mockAuthService
      },
      {
        provide: BrewService,
        useClass: MockBrewService
      },
      {
        provide: RecipeService,
        useClass: MockRecipeService
      },
      Store,
    ],
  }

  beforeEach(waitForAsync(async() => {
    component = await render(BrewListComponent, renderOptions)
  }))

  beforeEach(() => {
    fixture = component.fixture
    instance = fixture.componentInstance as BrewListComponent
    debugElement = fixture.debugElement
    location = TestBed.inject(Location)
    brewService = TestBed.inject(BrewService)
  })

  it('should create component', async () => {
    expect(component).toBeTruthy()
  })

  it('should list brews for a user', async () => {
    expect(screen.getByText(/My Recipe 1/i))
    expect(screen.getByText(/My Recipe 2/i))
    expect(screen.getByText(/My Recipe 5/i))
    const brewsTable = component.getByRole(/table/)
    const [columnNames, ...rows] = within(brewsTable).getAllByRole('row')
    expect(rows.length).toEqual(mockBrews.length)
    expect(columnNames.innerText).toContain('Recipe Name')
    expect(columnNames.innerText).toContain('Brew Date')
  })

  it('should enable delete btn when row checked', async() => {
    const delBtn = component.getByRole('button', { name: 'Delete Selected' })
    const checkBoxes = component.getAllByRole('checkbox')
    expect(delBtn.closest('button').disabled).toBeTrue()
    userEvent.click(checkBoxes[1])
    expect(delBtn.closest('button').disabled).toBeFalse()
  })

  it('should disable delete btn when row unchecked', async() => {
    const delBtn = component.getByRole('button', { name: 'Delete Selected' })
    const checkBoxes = component.getAllByRole('checkbox')
    expect(delBtn.closest('button').disabled).toBeTrue()
    // check
    userEvent.click(checkBoxes[1])
    // uncheck
    userEvent.click(checkBoxes[1])
    expect(delBtn.closest('button').disabled).toBeTrue()
  })

  it('should trigger refresh when refresh button clicked', async() => {
    const refreshSpy = spyOn(instance, 'refresh').and.callThrough()
    userEvent.click(component.getByRole('button', { name: 'Refresh' }))
    expect(refreshSpy).toHaveBeenCalled()
  })

  it('should delete selected brews', async() => {
    const clickSpy = spyOn(instance, 'check').and.callThrough()
    const confirmSpy = spyOn(instance, 'confirm').and.callThrough()
    await userEvent.click(component.getAllByRole('checkbox')[2])
    await userEvent.click(component.getByText('Delete Selected').closest('button'))
    expect(clickSpy).toHaveBeenCalledTimes(1)
    expect(confirmSpy).toHaveBeenCalledTimes(1)
    // Wait for the confirm dialog to appear, call screen since launched in cdk
    await screen.findByRole('dialog')
    const yesBtn = await screen.findByRole('button', { name: 'Yes'})
    fireEvent.click(yesBtn)
  })

  it('should navigate to add brew when btn clicked', async() => {
    const clickSpy = spyOn(instance, 'addBrew').and.callThrough()
    await userEvent.click(component.getByText('Add Brew').closest('button'))
    expect(clickSpy).toHaveBeenCalledTimes(1)
    expect(location.path()).toBe('/main/brews/add')
  })

  it('should navigate to brew detail when link clicked', async() => {
    const clickSpy = spyOn(instance, 'editBrew').and.callThrough()
    await userEvent.click(component.getAllByRole('link')[0])
    expect(location.path()).toBe('/main/brews/' + mockBrew.id)
    expect(clickSpy).toHaveBeenCalled()
  })

})


