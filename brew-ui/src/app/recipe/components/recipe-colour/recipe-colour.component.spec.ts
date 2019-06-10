import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeColourComponent } from './recipe-colour.component';


describe('RecipeColourComponent', () => {
  let component: RecipeColourComponent;
  let fixture: ComponentFixture<RecipeColourComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecipeColourComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeColourComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should parse 12.0 EBC as srm_6_0', () => {
      component.recipeColour = '12.0 EBC';
      component.ngOnInit();
      expect(component.cssValue).toEqual('srm_6_0');
  } );

  it('should parse 5 as srm_5_0', () => {
      component.recipeColour = '5 ';
      component.ngOnInit();
      expect(component.cssValue).toEqual('srm_5_0');
  });

  it('should round 5.61 to srm_5_6', () => {
      component.recipeColour = '5.61';
      component.ngOnInit();
      expect(component.cssValue).toEqual('srm_5_6');
  });
});
