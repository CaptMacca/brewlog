import { ComponentFixture } from '@angular/core/testing';

import { BrewNotesComponent } from '@app/brew/components/brew-detail/subcomponents/brew-notes/brew-notes.component';
import { logRoles, render, RenderResult, screen } from '@testing-library/angular';
import { UiModule } from '@app/common/ui/ui.module';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrewSessionComponent } from '@app/brew/components/brew-detail/subcomponents/brew-session/brew-session.component';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';

describe('BrewNotesComponent', () => {
  let component: RenderResult<BrewNotesComponent>;
  let fixture: ComponentFixture<BrewNotesComponent>;
  let instance: BrewNotesComponent
  let debugElement: DebugElement

  const parentForm = new FormGroup<any>({
    'notes': new FormControl(''),
    'tastingNotes': new FormControl(''),
  })

  const renderOptions = {
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
    component = await render(BrewNotesComponent, renderOptions);
    fixture = component.fixture
    instance = fixture.componentInstance as BrewNotesComponent
    debugElement = fixture.debugElement
  })

  it('should create', () => {
    expect(component).toBeTruthy();
  })

  it('should render the notes when form is populated', async() => {
    parentForm.patchValue({
      notes: 'test notes',
      tastingNotes: 'test tasting notes'
    })
    await component.rerender({
      parentForm: parentForm
    })
    expect(debugElement.query(By.css('#brewNotes')).nativeElement.value).toEqual('test notes')
    expect(debugElement.query(By.css('#tastingNotes')).nativeElement.value).toEqual('test tasting notes')
  })

})
