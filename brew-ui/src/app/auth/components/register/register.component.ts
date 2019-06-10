import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { ToastrService } from 'ngx-toastr';
import { Store } from '@ngxs/store';

import { SignUpInfo } from '@app/auth/model/signup-info';
import { Signup } from '@app/auth/state/auth.actions';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;
  errorMessage = '';
  submitted = false;

  form_validation_messages = {
    firstName: [
      { type: 'required', message: 'First Name is required' },
      { type: 'minlength', message: 'First Name must contain at least 3 characters' },
      { type: 'maxlength', message: 'First Name cannot be more than 50 characters' }],
    surname: [
      { type: 'required', message: 'Surname is required' },
      { type: 'minlength', message: 'Surname must contain at least 3 characters' },
      { type: 'maxlength', message: 'Surname cannot be more than 50 characters' }],
    username: [
      { type: 'required', message: 'First Name is required' },
      { type: 'minlength', message: 'First Name must contain at least 3 characters' },
      { type: 'maxlength', message: 'First Name cannot be more than 50 characters' }],
    email: [
      { type: 'required', message: 'Email address is required' },
      { type: 'email', message: 'Email must be a valid email address' },
      { type: 'maxlength', message: 'Email cannot be more than 60 characters' }],
    password: [
      { type: 'required', message: 'Password is required' },
      { type: 'minlength', message: 'Password must contain at least 3 characters' },
      { type: 'maxlength', message: 'Password cannot be more than 40 characters' }]
  };

  constructor(
    private store: Store,
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.form = this.fb.group({
      firstName: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(50)])],
      surname: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(50)])],
      username: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(50)])],
      email: ['', Validators.compose([Validators.required, Validators.email, Validators.maxLength(60)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(40)])]
    });
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

  register() {
    this.submitted = true;
    if (this.form.dirty && this.form.valid) {
      const signupInfo: SignUpInfo = new SignUpInfo(
        this.firstName.value,
        this.surname.value,
        this.username.value,
        this.email.value,
        this.password.value);
      this.store.dispatch(new Signup(signupInfo)).subscribe(
        () => {
          this.toastr.success('Success', 'Your account has been registered and you can now login');
          this.router.navigate(['/user/login']);
        },
        () => {
          this.toastr.success('Error', 'Your registration has failed');
        }
      );
    }
  }

  backToWelcome() {
    this.router.navigate(['/welcome']);
  }

}
