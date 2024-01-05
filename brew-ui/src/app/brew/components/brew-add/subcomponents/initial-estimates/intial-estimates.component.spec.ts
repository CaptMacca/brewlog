import { ComponentFixture } from '@angular/core/testing';

import { IntialEstimatesComponent } from '@app/brew/components/brew-add/subcomponents/initial-estimates/intial-estimates.component';
import { UiModule } from '@app/common/ui/ui.module';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { prettyDOM, render, RenderResult, screen } from '@testing-library/angular';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

describe('IntialEstimatesComponent', () => {
  let component: RenderResult<IntialEstimatesComponent>;
  let fixture: ComponentFixture<IntialEstimatesComponent>;
  let instance: IntialEstimatesComponent;
  let debugElement: DebugElement;

  const parentForm: FormGroup<any> = new FormGroup<any>({
    'brewDate': new FormControl(new Date()),
    'estimatedOriginalGravity': new FormControl(0),
    'estimatedPreboilGravity': new FormControl(0),
    'estimatedFinalGravity': new FormControl(0),
    'estimatedFermentVolume': new FormControl(0),
    'estimatedBottleVolume': new FormControl(0)
  })

  const renderOptions = {
    imports: [
      UiModule,
      FormsModule,
      ReactiveFormsModule
    ],
    declarations: [
      IntialEstimatesComponent
    ],
    exports: [
      IntialEstimatesComponent
    ],
    providers: [
      { provide: NZ_I18N, useValue: en_US }
    ],
    componentProperties: {
      parentForm: parentForm
    }
  }

  beforeEach(async() => {
    component = await render(IntialEstimatesComponent, renderOptions)
    fixture = component.fixture
    instance = component.fixture.componentInstance as IntialEstimatesComponent
    debugElement = component.fixture.debugElement
  })

  it('should create', () => {
    expect(component).toBeTruthy();
  })

  it('should render the initial estimates when form is populated', async() => {
    const currentDate = new Date();
    parentForm.patchValue({
      brewDate: currentDate,
      estimatedOriginalGravity: 1.050,
      estimatedPreboilGravity: 1.040,
      estimatedFinalGravity: 1.010,
      estimatedFermentVolume: 5,
      estimatedBottleVolume: 4
    })
    await component.rerender({
      componentProperties: {
        parentForm: parentForm
      }
    })
    expect(debugElement.query(By.css('#brewDate')).query(By.css('input')).nativeElement.value).toEqual(currentDate.toLocaleDateString('en-AU'))
    expect(debugElement.query(By.css('#estimatedPreBoilGravity')).query(By.css('input')).nativeElement.value).toEqual('1.04')
    expect(debugElement.query(By.css('#estimatedOriginalGravity')).query(By.css('input')).nativeElement.value).toEqual('1.05')
    expect(debugElement.query(By.css('#estimatedFinalGravity')).query(By.css('input')).nativeElement.value).toEqual('1.01')
    expect(debugElement.query(By.css('#estimatedFermentVolume')).query(By.css('input')).nativeElement.value).toEqual('5')
    expect(debugElement.query(By.css('#estimatedBottleVolume')).query(By.css('input')).nativeElement.value).toEqual('4')
  })
});
