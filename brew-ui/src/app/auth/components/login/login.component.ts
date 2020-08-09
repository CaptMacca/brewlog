import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthLoginInfo } from '@app/auth/model/login-info';
import { Login } from '@app/auth/state/auth.actions';
import { Store } from '@ngxs/store';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { NzMessageService } from 'ng-zorro-antd';
import { LoginForm } from '@app/auth/model/login-form';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  submitted = false;

  constructor(
    private readonly store: Store,
    private readonly fb: RxFormBuilder,
    private readonly router: Router,
    private readonly message: NzMessageService
    ) { }

  ngOnInit() {
    const loginForm = new LoginForm();
    this.form = this.fb.formGroup(loginForm);
  }

  get username() {
    return this.form.get('username');
  }

  get password() {
    return this.form.get('password');
  }

  login() {
    this.submitted = true;

    if (this.form.dirty && this.form.valid) {
      const loginInfo = new AuthLoginInfo(this.username.value, this.password.value);
      this.store.dispatch(new Login(loginInfo)).subscribe(
        () => {
          this.message.success('Logged in successfully');
          this.router.navigate(['/main/dashboard']);
        },
        err => this.message.error('Login was unsuccessful'),
        );
    }
  }

  register() {
    this.router.navigate(['/welcome/signup']);
  }

}
