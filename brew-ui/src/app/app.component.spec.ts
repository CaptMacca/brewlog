import { async, TestBed, waitForAsync } from '@angular/core/testing';

import { AppComponent } from './app.component';
import { AppRoutingModule } from '@app/app-routing.module';

describe('AppComponent', () => {

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [
      ],
      imports: [
        AppRoutingModule,
      ],
    }).compileComponents();
  }));

  it('should create the app', waitForAsync(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

});
