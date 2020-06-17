import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { SignUpInfo } from '@app/auth/model/signup-info';
import { Signup } from '@app/auth/state/auth.actions';
import { Store } from '@ngxs/store';
import { RxFormBuilder, RxwebValidators } from '@rxweb/reactive-form-validators';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;

  submitted = false;

  constructor(
    private readonly store: Store,
    private readonly fb: RxFormBuilder,
    private readonly router: Router,
    private readonly message: NzMessageService) { }

  ngOnInit() {
    this.form = this.fb.group({
      firstName: ['', [
        RxwebValidators.required({message: 'Please provide a first name !'}),
        RxwebValidators.maxLength({message: 'The first name cannot exceed 50 characters!', value: 50}),
      ]],
      surname: ['', [
        RxwebValidators.required({message: 'Please provide a surname !'}),
        RxwebValidators.maxLength({message: 'The surname cannot exceed 50 characters!', value: 50}),
      ]],
      username: ['', [
        RxwebValidators.required({message: 'Please provide a username !'}),
        RxwebValidators.minLength({message: 'The username must be at least 3 characters!', value: 3}),
        RxwebValidators.maxLength({message: 'The username cannot exceed 20 characters!', value: 20}),
      ]],
      email: ['', [
        RxwebValidators.required({message: 'Please provide an email address !'}),
        RxwebValidators.email({message: 'Please enter a valid email address !'}),
      ]],
      password: ['', [
        RxwebValidators.required({message: 'Please provide a password !'}),
        RxwebValidators.minLength({message: 'The password must be at least 3 characters!', value: 3}),
      ]],
      confirm: ['', [
        RxwebValidators.required({message: 'Please provide a password !'}),
        RxwebValidators.minLength({message: 'The password must be at least 3 characters!', value: 3}),
        RxwebValidators.compare({message: 'The confirm password does not match the password', fieldName: 'password'}),
      ]],
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

   get confirm() {
    return this.form.get('confirm');
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
