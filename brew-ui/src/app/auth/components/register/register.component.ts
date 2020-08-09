import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { SignUpInfo } from '@app/auth/model/signup-info';
import { Signup } from '@app/auth/state/auth.actions';
import { Store } from '@ngxs/store';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { NzMessageService } from 'ng-zorro-antd';
import { SignUpForm } from '@app/auth/model/signup-form';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;
  submitted = false;
  readonly DEFAULT_ROLES = ['user'];

  constructor(
    private readonly store: Store,
    private readonly fb: RxFormBuilder,
    private readonly router: Router,
    private readonly message: NzMessageService) { }

  ngOnInit() {
    const signupForm = new SignUpForm();
    this.form = this.fb.formGroup(signupForm);
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
      const signupInfo: SignUpInfo = {
        firstName: this.firstName.value,
        surname: this.surname.value,
        username: this.username.value,
        email: this.email.value,
        password: this.password.value,
        roles: this.DEFAULT_ROLES
      };
      this.store.dispatch(new Signup(signupInfo)).subscribe(
        () => {
          this.message.success('Your account has been registered and you can now login !');
          this.backToWelcome();
        },
        () => {
          this.message.error('Your registration attempt has failed !');
        }
      );
    }
  }

  backToWelcome() {
    this.router.navigate(['/welcome/login']);
  }

}
