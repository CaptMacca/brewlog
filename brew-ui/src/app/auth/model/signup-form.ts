import { maxLength, minLength, required, email, compare } from '@rxweb/reactive-form-validators';

export class SignUpForm {
  @required({message: 'Please provide a first name !'})
  @maxLength({message: 'The first name cannot exceed 50 characters!', value: 50})
  firstName: string;
  @required({message: 'Please provide a surname !'})
  @maxLength({message: 'The first name cannot exceed 50 characters!', value: 50})
  surname: string;
  @required({message: 'Please provide a username !'})
  @minLength({message: 'The username must be at least 3 characters!', value: 3})
  @maxLength({message: 'The username cannot exceed 20 characters!', value: 20})
  username: string;
  @required({message: 'Please provide an email address !'})
  @email({message: 'Please enter a valid email address !'})
  email: string;
  @required({message: 'Please provide a password !'})
  @minLength({message: 'The password must be at least 3 characters!', value: 3})
  password: string;
  @required({message: 'Please provide a password !'})
  @minLength({message: 'The password must be at least 3 characters!', value: 3})
  @compare({message: 'The confirm password does not match the password', fieldName: 'password'})
  confirm: string;
}
