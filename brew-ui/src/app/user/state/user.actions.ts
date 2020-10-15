import { UserDetails, UserPassword } from '@app/user/model';
import { UserRegistration } from '@app/user/model/user-registration';

export class GetCurrentUserDetails {
  static readonly type = '[User] Get Current User Details';
}

export class UpdateUserDetails {
  static readonly type = '[User] Update User Details';
  constructor(public payload: { username: string, userDetails: UserDetails}) {}
}

export class ClearUserDetails {
  static readonly type = '[User] Clear UserDetails';
}

export class UpdatePassword {
  static readonly type = '[User] Update Password';
  constructor(public payload: UserPassword) {}
}

export class SetSavingUser {
  static readonly type = '[User] Saving User';
  constructor(public payload: Boolean) {}
}

export class Signup {
  static readonly type = '[User] Signup';
  constructor(public payload: UserRegistration) {}
}

export class SignupSuccess {
  static readonly type = '[User] Signup Successful';
  constructor(public payload: UserRegistration) {}
}

export class SignupFailed {
  static readonly type = '[User] Signup Failed';
  constructor(public payload: string) {}
}
