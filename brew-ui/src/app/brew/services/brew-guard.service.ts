import { Injectable } from '@angular/core';
import { CanDeactivate } from '@angular/router';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { NzModalService } from 'ng-zorro-antd';
import { Observable, of, ReplaySubject } from 'rxjs';

@Injectable()
export class BrewEditGuard implements CanDeactivate<BrewDetailComponent> {

  constructor(private readonly modalService: NzModalService) {}

  canDeactivate(component: BrewDetailComponent): Observable<boolean> {
    if (component.brewForm.dirty) {
      // Form is dirty so present confirm modal, subscribe to selection and return
      // for deactivate
      const modalResponse = new ReplaySubject<boolean>();
      this.modalService.confirm({
        nzTitle: 'Are you sure you want to abandon edits to the selected brew session ?',
        nzOkText: 'Yes',
        nzOkType: 'danger',
        nzOnOk: () => modalResponse.next(true),
        nzCancelText: 'No',
        nzOnCancel: () => modalResponse.next(false),
      });

      return modalResponse;
    } else {
      // Form is not dirty or submitted so just allow deactivate
      return of(true);
    }
  }

}
