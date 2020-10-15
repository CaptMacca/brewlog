import { AuthLoginInfo } from '@app/auth/model/auth-login-info';

export class Login {
  static readonly type = '[Auth] Login';
  constructor(public payload: AuthLoginInfo) {}
}

export class LoginSuccess {
  static readonly type = '[Auth] Login Successful';
  constructor(public payload: AuthLoginInfo) {}
}

export class LoginFailed {
  static readonly type = '[Auth] Login Failed';
  constructor(public payload: string) {}
}

export class Logout {
  static readonly type = '[Auth] Logout';
}


