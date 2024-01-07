import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserRegistration } from '@app/user/model/user-registration';
import { Store } from '@ngxs/store';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Signup } from '@app/user/state/user.actions';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: UntypedFormGroup;
  submitted = false;
  readonly DEFAULT_ROLES = ['user'];

  constructor(
    private readonly store: Store,
    private readonly fb: RxFormBuilder,
    private readonly router: Router,
    private readonly message: NzMessageService) { }

  ngOnInit() {
    const userRegistration = new UserRegistration();
    this.form = this.fb.formGroup(userRegistration);
   }

   get firstName() {
     return this.form.get('firstName');
   }

   get surname() {
     return this.form.get('surname');
   }

   get username() {
     return this.form.get('username');
   }

   get email() {
     return this.form.get('email');
   }

   get password() {
     return this.form.get('password');
   }

   get confirm() {
    return this.form.get('confirm');
  }

  register() {
    this.submitted = true;
    if (this.form.dirty && this.form.valid) {
      const userRegistration: UserRegistration = {
        id: undefined,
        firstName: this.firstName.value,
        surname: this.surname.value,
        username: this.username.value,
        email: this.email.value,
        password: this.password.value,
        confirm: this.confirm.value,
        roles: this.DEFAULT_ROLES
      };
      this.store.dispatch(new Signup(userRegistration)).subscribe({
        next: ()=> {
          this.message.success('Your account has been registered and you can now login !');
          this.backToWelcome();
        },
        error: () => {
          this.message.error('Your registration attempt has failed !');
        }
      });
    }
  }

  backToWelcome() {
    this.router.navigate(['/welcome/login']);
  }

}
