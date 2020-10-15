import { maxLength, required, email, prop } from '@rxweb/reactive-form-validators';

export class UserDetails {
  @prop()
  id: number;

  @required({message: 'Please provide a first name !'})
  @maxLength({message: 'The first name cannot exceed 50 characters!', value: 50})
  firstName: string;

  @required({message: 'Please provide a surname !'})
  @maxLength({message: 'The first name cannot exceed 50 characters!', value: 50})
  surname: string;

  @required({message: 'Please provide an email address !'})
  @email({message: 'Please enter a valid email address !'})
  email: string;
}
