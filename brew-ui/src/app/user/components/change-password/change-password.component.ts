import { Component, OnInit } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { UntypedFormGroup } from '@angular/forms';
import { UpdatePassword } from '@app/user/state/user.actions';
import { AuthState } from '@app/auth/state/auth.state';
import { Observable } from 'rxjs';
import { UserPassword } from '@app/user/model';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  @Select(AuthState.getUsername) username$: Observable<string>;
  saving$: Observable<boolean>;
  username: string;
  form: UntypedFormGroup;

  constructor(
    private readonly store: Store,
    private readonly fb: RxFormBuilder,
    private readonly message: NzMessageService) {
    this.form = this.fb.formGroup(new UserPassword());
  }

  ngOnInit() {
    this.username$.subscribe(
      username => this.username = username
    );
  }

  get fc() {
    return this.form.controls;
  }


  update() {
    if (this.form.dirty && this.form.valid) {

      const updatePassword: UserPassword = {
        currentPassword: this.fc['currentPassword'].value,
        newPassword: this.fc['newPassword'].value,
        confirmPassword: this.fc['confirmPassword'].value
      };
      console.log(updatePassword)
      this.store.dispatch(new UpdatePassword(updatePassword)).subscribe(
        result => this.message.success('Your password has been successfully updated'),
        err => this.message.error('Your password could not be updated')
      );
    }
  }

  reset() {
    this.form.reset();
  }
}
