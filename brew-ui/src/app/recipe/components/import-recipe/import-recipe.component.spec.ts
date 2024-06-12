import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportRecipeComponent } from './import-recipe.component';
import { fireEvent, render, RenderResult, screen } from '@testing-library/angular';
import { UiModule } from '@app/common/ui/ui.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgxsModule, Store } from '@ngxs/store';
import { mockAuthService, MockAuthState, MockRecipeService, MockRecipeState } from '@app/recipe/spec/mock.recipe.components';
import { DebugElement } from '@angular/core';
import { AuthService } from '@app/auth/services/auth.service';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { Location } from '@angular/common';
import userEvent from '@testing-library/user-event';

describe('ImportRecipeComponent', () => {
  let component: RenderResult<ImportRecipeComponent>;
  let location: Location;
  let fixture: ComponentFixture<ImportRecipeComponent>;
  let debugElement: DebugElement;
  let instance: ImportRecipeComponent;
  let recipeComponentSpy;

  beforeEach(async () => {
    recipeComponentSpy = jasmine.createSpy();
    component = await render(ImportRecipeComponent, {
      imports: [
        NgxsModule.forRoot([MockRecipeState, MockAuthState]),
        UiModule,
        HttpClientTestingModule,
      ],
      routes: [
        {
          path: 'main',
          children: [
            {
              path: 'recipes',
              children: [
                { path: ':id', component: recipeComponentSpy }
              ]
            }
          ]
        }
      ],
      providers: [
        Store,
        {
          provide: AuthService,
          useValue: mockAuthService
        },
        {
          provide: RecipeService,
          useClass: MockRecipeService
        },
      ]
    });
  });

  beforeEach(() => {
    fixture = component.fixture;
    debugElement = fixture.debugElement;
    location = TestBed.inject(Location);
    instance = fixture.componentInstance as ImportRecipeComponent;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  // TODO: Get upload tests to work
  it('should upload a single recipe', async () => {
    const componentSpy = spyOn(instance, 'handleUpload').and.callThrough();
    const uploadSpy = spyOn(instance, 'beforeUpload').and.callThrough();
    // const uploader = debugElement.query(By.directive(NzUploadBtnComponent)).nativeElement;
    pageObject = new NzUploadPageObject();
    instance.beforeUpload()fileList= [{
      file: [new File(['(⌐□_□)'], 'mybeer.xml', { type: 'application/xml' })]
    ]
    const uploader = component.getAllByRole('button', { name: 'Select File' })[0];
    // const uploader = screen.getByText('Click or drag file to this area to upload')
    //   .closest('input');
    fireEvent.change(uploader, {
      target: {
        file: [new File(['(⌐□_□)'], 'mybeer.xml', { type: 'application/xml' })]
      }
    });
    console.log(uploader);
    userEvent.click(screen.getByRole('button', { name: 'Upload' }));
    console.log(fixture.componentInstance.fileList);
    console.log(uploader);
    expect(componentSpy).toHaveBeenCalled();
    expect(uploadSpy).toHaveBeenCalled();
  });

  // it('should navigate to the list of recipes', async () => {
  //   const componentSpy = spyOn(instance, 'gotoRecipes').and.callThrough();
  //   await userEvent.click(screen.getByRole('button', { name: 'View Recipes' }));
  //   expect(componentSpy).toHaveBeenCalled();
  //   expect(location.path()).toBe('/main/recipes');
  // });
});
