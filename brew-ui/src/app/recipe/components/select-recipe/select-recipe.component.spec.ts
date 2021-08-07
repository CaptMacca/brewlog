import { fireEvent, render, RenderResult, within } from '@testing-library/angular';
import { SelectRecipeComponent } from '@app/recipe/components/select-recipe/select-recipe.component';
import { mockRecipes } from '@app/recipe/spec/mock.recipe.components';
import { UiModule } from '@app/common/ui/ui.module';
import { DebugElement } from '@angular/core';
import { ComponentFixture } from '@angular/core/testing';

describe('SelectRecipeComponent', () => {

  let component: RenderResult<SelectRecipeComponent>;
  let fixture: ComponentFixture<SelectRecipeComponent>;
  let debugElement: DebugElement;
  let instance: SelectRecipeComponent;
  let container: Element;
  let spyOnSelectRecipe = jasmine.createSpy();
  let spyOnUnSelectRecipe = jasmine.createSpy();

  beforeEach(async() => {

    component = await render(SelectRecipeComponent, {
      detectChanges: true,
      imports: [
        UiModule,
      ],
      componentProperties: {
        recipes: mockRecipes,
        selectRecipe: {
          emit: spyOnSelectRecipe
        } as any,
        unselectRecipe: {
          emit: spyOnUnSelectRecipe
        } as any,
      }
    })
  });

  beforeEach(() => {
    fixture = component.fixture;
    debugElement = fixture.debugElement;
    instance = fixture.componentInstance as SelectRecipeComponent;
    container = component.container;
  });

  afterEach(() => {
    spyOnSelectRecipe = jasmine.createSpy();
    spyOnUnSelectRecipe = jasmine.createSpy();
  });

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should render list of recipes', async () => {
    const [columnsNames, ...rows] = within(component.getByRole('table')).getAllByRole('row');
    expect(rows.length).toEqual(mockRecipes.length);
    expect(columnsNames.innerText).toContain('Recipe Name');
    expect(columnsNames.innerText).toContain('Style');
    expect(columnsNames.innerText).toContain('Type');
  });

  it('should emit selected recipes', async () => {
    const checkBoxes = component.getAllByRole('checkbox');
    await fireEvent.click(checkBoxes[0]);
    expect(spyOnSelectRecipe).toHaveBeenCalledTimes(1);
    expect(spyOnSelectRecipe).toHaveBeenCalledWith(mockRecipes[0]);
  });

  it('should emit unselected recipes', async () => {
    const checkBoxes = component.getAllByRole('checkbox');
    await fireEvent.click(checkBoxes[0]);
    await fireEvent.click(checkBoxes[0]);
    expect(spyOnUnSelectRecipe).toHaveBeenCalledTimes(1);
    expect(spyOnUnSelectRecipe).toHaveBeenCalledWith(mockRecipes[0]);
  });
});
