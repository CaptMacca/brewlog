import { Injectable } from '@angular/core';
import { CanDeactivate } from '@angular/router';

import { SimpleModalService } from 'ngx-simple-modal';

import { ConfirmComponent } from '@app/common/confirm/confirm.component';
import { Observable, of, Subject } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';

@Injectable()
export class BrewEditGuard implements CanDeactivate<BrewDetailComponent> {
  constructor(private simpleModalService: SimpleModalService) {}

  canDeactivate(component: BrewDetailComponent): Observable<boolean> {
    if (component.brewForm.dirty && !component.submitted) {
      const subject$ = new Subject<boolean>();
      this.simpleModalService.addModal(ConfirmComponent, {
          title: 'Confirm Unsaved Edit',
          message: 'Are you sure you wish to abandon edits to this brew'
      }).subscribe(subject$);

      return subject$.pipe(
        switchMap(result => of(result))
      );
    }
    return of(true);
  }
}
