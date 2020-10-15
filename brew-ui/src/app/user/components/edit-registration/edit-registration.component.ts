import { Component, OnInit } from '@angular/core';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { Select, Store } from '@ngxs/store';
import { FormGroup } from '@angular/forms';
import { UserState } from '@app/user/state/user.state';
import { UserDetails } from '@app/user/model';
import { Observable } from 'rxjs';
import { SetSavingUser, UpdateUserDetails } from '@app/user/state/user.actions';
import { catchError, finalize } from 'rxjs/operators';
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
    this.form = this.fb.formGroup(new UserDetails());
  }

  get fc() {
    return this.form.controls;
  }

  ngOnInit() {
    this.loadForm();
  }

  private loadForm() {
    this.userDetails$.pipe(
      catchError(err => {
        console.log(err);
        throw err;
      })).subscribe(
      user =>  {
          this.form.patchValue({
            id: user.id,
            firstName: user.firstName,
            surname: user.surname,
            email: user.email,
          });
          this.form.markAsPristine();
      });
  }

  save(): void {
    if (this.form.dirty && this.form.valid) {
      const user: UserDetails = {
        id: this.fc['id'].value,
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
          () => {
            this.loadForm();
            this.message.success('User Registration has been update');
          }
        );
      });
    }
  }

  reset() {
    this.userDetails$.subscribe(user => {
      this.form.reset({ value: {
          id: user.id,
          firstName: user.firstName,
          surname: user.surname,
          email: user.email,
      }})
    });
  }
}
