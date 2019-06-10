import { Injectable } from '@angular/core';
import { CanDeactivate } from '@angular/router';
import { Observable, of, Subject } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { SimpleModalService } from 'ngx-simple-modal';

import { ConfirmComponent } from '@app/common/confirm/confirm.component';
import { MeasurementDetailComponent } from '@app/measurement/components/measurement-detail/measurement-detail.component';

@Injectable()
export class MeasurementEditGuard implements CanDeactivate<MeasurementDetailComponent> {
  constructor(private simpleModalService: SimpleModalService) {}

  canDeactivate(component: MeasurementDetailComponent): Observable<boolean> {
    if (component.form.dirty && !component.submitted) {
      const subject$ = new Subject<boolean>();
      this.simpleModalService
        .addModal(ConfirmComponent, {
          title: 'Confirm Unsaved Edit',
          message: 'Are you sure you wish to abandon edits to this measurement'
        }).subscribe(subject$);

        return subject$.pipe(
          switchMap(result => of(result))
        );
    }
    return of(true);
  }
}
