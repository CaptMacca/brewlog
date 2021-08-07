import { Injectable } from '@angular/core';
import { CanDeactivate } from '@angular/router';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { Observable, of } from 'rxjs';

@Injectable()
export class BrewEditGuard implements CanDeactivate<BrewDetailComponent> {

  constructor() {}

  canDeactivate(component: BrewDetailComponent): Observable<boolean> {
    if (component.brewForm.dirty) {
      // Form is dirty so present confirm modal, subscribe to selection and return
      // for deactivate
      return component.confirmUnsaved();
    } else {
      // Form is not dirty or submitted so just allow deactivate
      return of(true);
    }
  }

}
