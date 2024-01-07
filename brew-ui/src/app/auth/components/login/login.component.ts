import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthLoginInfo } from '@app/auth/model/auth-login-info';
import { Login } from '@app/auth/state/auth.actions';
import { Store } from '@ngxs/store';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: UntypedFormGroup;
  submitted = false;

  constructor(
    private readonly store: Store,
    private readonly fb: RxFormBuilder,
    private readonly router: Router,
    private readonly message: NzMessageService
    ) { }

  ngOnInit() {
    const authLoginInfo = new AuthLoginInfo();
    this.form = this.fb.formGroup(authLoginInfo);
  }

  get username() {
    return this.form.get('username');
  }

  get password() {
    return this.form.get('password');
  }

  get remember() {
    return this.form.get('remember');
  }

  login() {
    this.submitted = true;

    if (this.form.dirty && this.form.valid) {
      const loginInfo = {
        username: this.username.value,
        password: this.password.value,
        remember: this.remember.value
      };
      this.store.dispatch(new Login(loginInfo)).subscribe({
        next: () => {
          this.message.success('Logged in successfully');
          this.router.navigate(['/main/dashboard']);
        },
        error: () => this.message.error('Login was unsuccessful'),
    });
    }
  }

  register() {
    this.router.navigate(['/welcome/signup']);
  }

}
