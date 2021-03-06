import { Action, Selector, State, StateContext } from '@ngxs/store';
import { patch } from '@ngxs/store/operators';

import { AuthService } from '@app/auth/services/auth.service';
import { Login, Logout } from '@app/auth/state/auth.actions';
import { tap } from 'rxjs/operators';
import { ClearUserDetails, GetCurrentUserDetails } from '@app/user/state/user.actions';
import { Injectable } from '@angular/core';

export class AuthStateModel {
  loggedIn: any;
  accessToken: string;
  username: string;
  authorities: string[];
}

@State<AuthStateModel>({
  name: 'auth',
  defaults: {
    loggedIn: false,
    accessToken: '',
    username: '',
    authorities: []
  }
})
@Injectable()
export class AuthState {

  @Selector()
  static isLoggedIn(state: AuthStateModel) {
    return state.loggedIn;
  }

  @Selector()
  static getAccessToken(state: AuthStateModel) {
    return state.accessToken;
  }

  @Selector()
  static getUsername(state: AuthStateModel) {
    return state.username;
  }

  @Selector()
  static getAuthorities(state: AuthStateModel) {
    return state.authorities;
  }

  constructor(private authService: AuthService) {}

  @Action(Login)
  Login(ctx: StateContext<AuthStateModel>, { payload }: Login) {
    return this.authService.attemptAuth(payload).pipe(
      tap(data => {
        ctx.setState(patch({
          loggedIn: true,
          accessToken: data.accessToken,
          username: data.username,
          authorities: data.authorities
        }));
        ctx.dispatch(new GetCurrentUserDetails());
      })
    );
  }

  @Action(Logout)
  Logout(ctx: StateContext<AuthStateModel>) {
    const roles: string[] = [];
    return ctx.dispatch(new ClearUserDetails()).pipe(
      tap(() => ctx.setState(patch({
        loggedIn: false,
        accessToken: '',
        username: '',
        authorities: roles
      }))));
  }

}
