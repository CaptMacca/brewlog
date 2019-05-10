import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';

import { Store } from '@ngxs/store';
import { AuthState } from '../state/auth.state';

@Injectable()
export class AuthGuardService implements CanActivate {

  constructor(private router: Router, private store: Store) {}

  canActivate(): boolean {
    const isLoggedIn = this.store.selectSnapshot(AuthState.isLoggedIn);
    if (!isLoggedIn) {
      this.router.navigate(['user/login']);
      return false;
    }
    return true;
  }
}
