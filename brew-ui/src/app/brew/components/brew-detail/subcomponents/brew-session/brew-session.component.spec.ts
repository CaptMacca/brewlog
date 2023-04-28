import { async, ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BrewSessionComponent } from './brew-session.component';

describe('BrewSessionComponent', () => {
  let component: BrewSessionComponent;
  let fixture: ComponentFixture<BrewSessionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BrewSessionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrewSessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
