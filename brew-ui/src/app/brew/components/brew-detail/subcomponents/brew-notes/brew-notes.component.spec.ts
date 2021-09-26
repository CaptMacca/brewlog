import { async, ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BrewNotesComponent } from './brew-notes.component';

describe('BrewNotesComponent', () => {
  let component: BrewNotesComponent;
  let fixture: ComponentFixture<BrewNotesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BrewNotesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrewNotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
