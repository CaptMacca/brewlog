import { compare, minLength, required } from '@rxweb/reactive-form-validators';

export class UserPassword {
  @required({ message: 'Please provide your current password'})
  currentPassword: string;
  @required({ message: 'Please provide the new password'})
  @compare({fieldName: 'confirmPassword', message: 'The passwords do not match'})
  @minLength({message: 'The password must be at least 3 characters!', value: 3})
  newPassword: string;
  @required({ message: 'Please provide the confirm new password'})
  @compare({fieldName: 'newPassword', message: 'The passwords do not match'})
  @minLength({message: 'The password must be at least 3 characters!', value: 3})
  confirmPassword: string;
}
