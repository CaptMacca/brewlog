import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthLoginInfo } from '@app/auth/model/login-info';
import { Login } from '@app/auth/state/auth.actions';
import { Store } from '@ngxs/store';
import { RxFormBuilder, RxwebValidators } from '@rxweb/reactive-form-validators';
import { NzMessageService } from 'ng-zorro-antd';

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

    this.form = this.fb.group({
      username: ['', [RxwebValidators.required({message: 'Please enter your username'})]],
      password: ['', [RxwebValidators.required({message: 'Please enter your password'})]],
      remember: ['false'],
    });
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
      const loginInfo = new AuthLoginInfo(this.username.value, this.password.value);
      this.store.dispatch(new Login(loginInfo)).subscribe(
        () => {
          this.message.success('Logged in successfully')
          this.router.navigate(['/main/dashboard']);
        },
        err => this.message.error('Login was unsuccessful')
      );
    }
  }

  register() {
    this.router.navigate(['/welcome/signup']);
  }

}
