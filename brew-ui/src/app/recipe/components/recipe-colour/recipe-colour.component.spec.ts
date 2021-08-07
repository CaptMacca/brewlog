import { RecipeColourComponent } from './recipe-colour.component';
import { render } from '@testing-library/angular';

describe('RecipeColourComponent', () => {

  it('should create', async() => {
    const component = await render( RecipeColourComponent, {
      componentProperties: {
        recipeColour: '12.0 EBC',
        imgWidth: '80',
        imgHeight: '150',
      }
    });
    expect(component).toBeTruthy();
  });

  it('should parse 12.0 EBC as srm_6', async() => {
    const component = await render( RecipeColourComponent, {
      componentProperties: {
        recipeColour: '12.0 EBC',
        imgWidth: '80',
        imgHeight: '150',
      }
    });
    expect(component.fixture.componentInstance.cssValue).toEqual('srm_6');
  });

  it('should parse 5 as srm_5', async() => {
    const component = await render(RecipeColourComponent, {
      componentProperties: {
        recipeColour: '5',
        imgWidth: '80',
        imgHeight: '150',
      }
    });
    expect(component.fixture.componentInstance.cssValue).toEqual('srm_5');
  });

  it('should round 5.61 to srm_5_6', async() => {
    const component = await render( RecipeColourComponent, {
      componentProperties: {
        recipeColour: '5.61',
        imgWidth: '80',
        imgHeight: '150',
      }
    });
    expect(component.fixture.componentInstance.cssValue).toEqual('srm_5_6');
  });
});

