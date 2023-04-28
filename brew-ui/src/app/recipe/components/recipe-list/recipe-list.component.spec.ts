import { NgxsModule, Store } from '@ngxs/store'
import { RecipeListComponent } from '@app/recipe/components/recipe-list/recipe-list.component'
import { NzMessageService } from 'ng-zorro-antd/message'
import { NzModalService } from 'ng-zorro-antd/modal'
import { RecipeService } from '@app/recipe/services/recipe.service'
import { AuthService } from '@app/auth/services/auth.service'
import { UiModule } from '@app/common/ui/ui.module'
import { ImportRecipeComponent } from '@app/recipe/components/import-recipe/import-recipe.component'
import { RecipeDetailComponent } from '@app/recipe/components/recipe-detail/recipe-detail.component'
import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service'
import {
  mockAuthService,
  MockAuthState,
  mockRecipe,
  mockRecipes,
  MockRecipeService,
  MockRecipeState
} from '@app/recipe/spec/mock.recipe.components'
import userEvent from '@testing-library/user-event'
import { fireEvent, render, RenderResult, screen, within } from '@testing-library/angular'
import { HttpClientModule } from '@angular/common/http'
import { ComponentFixture, TestBed } from '@angular/core/testing'
import { Location } from '@angular/common'
import { DebugElement } from '@angular/core'
import { of } from 'rxjs';

describe('RecipeListComponent', () => {
  let component: RenderResult<RecipeListComponent>
  let location: Location
  let fixture: ComponentFixture<RecipeListComponent>
  let debugElement: DebugElement
  let instance: RecipeListComponent

  const renderOptions = {
    imports: [
      NgxsModule.forRoot([MockRecipeState, MockAuthState]),
      UiModule,
      HttpClientModule,
    ],
    routes: [

      {
        path: 'main',
        children: [
          {
            path: 'recipes',
            children: [
              { path: '', component: RecipeListComponent, },
              { path: 'import', component: ImportRecipeComponent, },
              { path: ':id', component: RecipeDetailComponent, resolve: {recipe: RecipeResolverService}}
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
    ],
  }

  beforeEach(async() => {
    component = await render(RecipeListComponent, renderOptions)
  })

  beforeEach(() => {
    fixture = component.fixture
    instance = fixture.componentInstance as RecipeListComponent
    debugElement = fixture.debugElement
    location = TestBed.inject(Location)
    spyOnProperty(instance, 'recipes$').and.returnValue(of(mockRecipes));
  })

  it('should create the component', async() => {
   expect(component).toBeTruthy()
  })

  it('should render the list of recipes', async() => {
    expect(screen.getByText(/My First Recipe/i)).toBeTruthy()
    expect(screen.getByText(/My Fifth Recipe/i)).toBeTruthy()
    const recipeTable = component.getByRole(/table/)
    const [columnNames, ...rows] = within(recipeTable).getAllByRole('row')
    expect(rows.length).toEqual(mockRecipes.length)
    expect(columnNames.innerText).toContain('Recipe Name')
    expect(columnNames.innerText).toContain('Style')
    expect(columnNames.innerText).toContain('Type')
    expect(columnNames.innerText).toContain('Estimated ABV')
  })

  it('should navigate to import recipe when import button clicked', async() => {
    const componentSpy = spyOn(instance, 'importRecipes').and.callThrough()
    await userEvent.click(component.getByRole('button', { name: 'Import Recipe'}))
    expect(componentSpy).toHaveBeenCalled()
    expect(location.path()).toBe('/main/recipes/import')
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
  });

  it('should trigger refresh when refresh button clicked', async() => {
    const refreshSpy = spyOn(instance, 'refresh').and.callThrough()
    userEvent.click(component.getByRole('button', { name: 'Refresh' }))
    expect(refreshSpy).toHaveBeenCalled()
  })

  it('should delete selected recipes', async() => {
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

})
