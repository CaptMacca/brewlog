import { async, ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { IntialEstimatesComponent } from './intial-estimates.component';

describe('IntialEstimatesComponent', () => {
  let component: IntialEstimatesComponent;
  let fixture: ComponentFixture<IntialEstimatesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ IntialEstimatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IntialEstimatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
