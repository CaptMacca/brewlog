import { AuthLoginInfo } from '@app/auth/model/login-info';
import { SignUpInfo } from '@app/auth/model/signup-info';

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

export class Signup {
  static readonly type = '[Auth] Signup';
  constructor(public payload: SignUpInfo) {}
}

export class SignupSuccess {
  static readonly type = '[Auth] Signup Successful';
  constructor(public payload: SignUpInfo) {}
}

export class SignupFailed {
  static readonly type = '[Auth] Login';
  constructor(public payload: string) {}
}

