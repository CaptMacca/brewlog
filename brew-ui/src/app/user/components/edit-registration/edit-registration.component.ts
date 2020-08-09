import { Component, OnInit } from '@angular/core';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { Select, Store } from '@ngxs/store';
import { FormGroup } from '@angular/forms';
import { EditRegistrationForm } from '@app/user/model/edit-registration-form';
import { UserState } from '@app/user/state/user.state';
import { UserDetails } from '@app/model';
import { Observable } from 'rxjs';
import { GetCurrentUserDetails, SetSavingUser, UpdateUserDetails } from '@app/user/state/user.actions';
import { finalize } from 'rxjs/operators';
import { NzMessageService } from 'ng-zorro-antd';
import { AuthState } from '@app/auth/state/auth.state';

@Component({
  selector: 'app-edit-registration',
  templateUrl: './edit-registration.component.html',
  styleUrls: ['./edit-registration.component.css']
})
export class EditRegistrationComponent implements OnInit {

  @Select(UserState.getUserDetails) userDetails$: Observable<UserDetails>;
  @Select(UserState.savingUser) saving$: Observable<boolean>;
  @Select(AuthState.getUsername) username$: Observable<string>;

  form: FormGroup;

  constructor(
    private store: Store,
    private readonly fb: RxFormBuilder,
    private readonly message: NzMessageService) {
    this.form = this.fb.formGroup(new EditRegistrationForm());
  }

  get fc() {
    return this.form.controls;
  }

  ngOnInit() {
    this.store.dispatch(new GetCurrentUserDetails()).subscribe(
      ctx => this.userDetails$.subscribe(user => this.loadForm(user))
    );
  }

  private loadForm(user: UserDetails) {
    this.form.patchValue({
      firstName: user.firstName,
      surname: user.surname,
      email: user.email,
    });
    this.form.markAsPristine();
  }

  save(): void {
    if (this.form.dirty && this.form.valid) {
      const user: UserDetails = {
        firstName: this.fc['firstName'].value,
        surname: this.fc['surname'].value,
        email: this.fc['email'].value,
      };
      this.username$.subscribe(username => {
        this.store.dispatch([
          new SetSavingUser(true),
          new UpdateUserDetails({ username: username, userDetails: user}),
        ]).pipe(
          finalize(() => this.store.dispatch(new SetSavingUser(false)))
        ).subscribe(
          savedUser => {
            console.log(savedUser);
            this.loadForm(savedUser);
            this.message.success('User Registration has been update');
          }
        );
      });
    }
  }

  reset() {
    this.userDetails$.subscribe(user => {
      this.form.reset({ value: {
          firstName: user.firstName,
          surname: user.surname,
          email: user.email,
      }})
    });
  }
}
