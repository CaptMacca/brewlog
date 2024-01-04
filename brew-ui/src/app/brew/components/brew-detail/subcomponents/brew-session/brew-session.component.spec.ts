import { ComponentFixture } from '@angular/core/testing';

import { BrewSessionComponent } from '@app/brew/components/brew-detail/subcomponents/brew-session/brew-session.component';
import { render, RenderResult } from '@testing-library/angular';
import { UiModule } from '@app/common/ui/ui.module';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import { DebugElement } from '@angular/core';


describe('BrewSessionComponent', () => {
  let component: RenderResult<BrewSessionComponent>;
  let fixture: ComponentFixture<BrewSessionComponent>;
  let instance: BrewSessionComponent;
  let debugElement: DebugElement;

  const parentForm: FormGroup<any> = new FormGroup<any>({
    'brewDate': new FormControl(new Date()),
    'score': new FormControl(0),
    'spargeVol': new FormControl(''),
    'totalWater': new FormControl(''),
    'fermenterVol': new FormControl(''),
    'estimatedOriginalGravity': new FormControl(0),
    'measuredOriginalGravity': new FormControl(0),
    'estimatedFinalGravity': new FormControl(0),
    'measuredFinalGravity': new FormControl(0),
    'estimatedPreboilGravity': new FormControl(0),
    'measuredPreboilGravity': new FormControl(0),
    'estimatedFermentVolume': new FormControl(0),
    'measuredFermentVolume': new FormControl(0),
    'estimatedBottleVolume': new FormControl(0),
    'measuredBottleVolume': new FormControl(0),
  })

  const renderOptions= {
    imports: [
      UiModule,
      FormsModule,
      ReactiveFormsModule
    ],
    declarations: [
      BrewSessionComponent
    ],
    exports: [
      BrewSessionComponent
    ],
    providers: [
      { provide: NZ_I18N, useValue: en_US }
    ],
    componentProperties: {
      parentForm: parentForm
    }
  }

  beforeEach(async() => {
    component = await render(BrewSessionComponent, renderOptions)
    fixture = component.fixture
    instance = fixture.componentInstance
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
