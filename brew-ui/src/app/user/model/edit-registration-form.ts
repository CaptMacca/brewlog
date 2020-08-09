import { maxLength, required, email, minLength } from '@rxweb/reactive-form-validators';

export class EditRegistrationForm {
  @required({message: 'Please provide a first name !'})
  @maxLength({message: 'The first name cannot exceed 50 characters!', value: 50})
  firstName: string;
  @required({message: 'Please provide a surname !'})
  @maxLength({message: 'The first name cannot exceed 50 characters!', value: 50})
  surname: string;
  @required({message: 'Please provide an email address !'})
  @email({message: 'Please enter a valid email address !'})
  email: string;
  @required({message: 'Please provide a username !'})
  @minLength({message: 'The username must be at least 3 characters!', value: 3})
  @maxLength({message: 'The username cannot exceed 20 characters!', value: 20})
  username: string;
}
