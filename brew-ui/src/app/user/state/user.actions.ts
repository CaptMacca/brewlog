import { UserPassword } from '@app/user/model';
import { UserDetails } from '@app/model';

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
