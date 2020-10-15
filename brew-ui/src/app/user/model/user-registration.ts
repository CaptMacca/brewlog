import { UserDetails } from '@app/user/model/user-details';
import { compare, maxLength, minLength, prop, required } from '@rxweb/reactive-form-validators';

export class UserRegistration extends UserDetails {
  @required({message: 'Please provide a username !'})
  @minLength({message: 'The username must be at least 3 characters!', value: 3})
  @maxLength({message: 'The username cannot exceed 20 characters!', value: 20})
  username: string;
  @prop()
  roles: string[];
  @required({message: 'Please provide a password !'})
  @minLength({message: 'The password must be at least 3 characters!', value: 3})
  password: string;
  @required({message: 'Please provide a password !'})
  @minLength({message: 'The password must be at least 3 characters!', value: 3})
  @compare({message: 'The confirm password does not match the password', fieldName: 'password'})
  confirm: string;
}
