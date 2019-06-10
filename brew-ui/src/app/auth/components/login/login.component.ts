import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { Store, Select } from '@ngxs/store';
import { ToastrService } from 'ngx-toastr';

import { AuthLoginInfo } from '@app/auth/model/login-info';
import { Login } from '@app/auth/state/auth.actions';
import { AuthState } from '@app/auth/state/auth.state';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  errorMessage = '';
  @Select(AuthState.getAuthorities) roles$;
  submitted = false;

  form_validation_messages = {
    username: [
      { type: 'required', message: 'First Name is required' },
      { type: 'minlength', message: 'First Name must contain at least 3 characters' },
      { type: 'maxlength', message: 'First Name cannot be more than 50 characters' }],
    password: [
      { type: 'required', message: 'Password is required' },
      { type: 'minlength', message: 'Password must contain at least 3 characters' },
      { type: 'maxlength', message: 'Password cannot be more than 40 characters' }]
  };

  constructor(private store: Store,
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService
    ) { }

  ngOnInit() {

    this.form = this.fb.group({
      username: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(50)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(40)])]
    });
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
          this.toastr.success('Logged in successfully', 'Login')
          this.router.navigateByUrl('/');
        },
        err => this.toastr.error('Login was unsuccessful', 'Login')
      );
    }
  }

  backToWelcome() {
    this.router.navigate(['/welcome']);
  }

  signUp() {
    this.router.navigate(['/user/signup']);
  }


}
