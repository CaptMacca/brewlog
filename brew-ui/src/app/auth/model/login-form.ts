import { prop, required } from '@rxweb/reactive-form-validators';

export class LoginForm {
  @required({message: 'Please enter your username'})
  username: string;
  @required({message: 'Please enter your password'})
  password: string;
  @prop()
  remember: boolean;
}
