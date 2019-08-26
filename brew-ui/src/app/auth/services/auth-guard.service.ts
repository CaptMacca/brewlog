import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';

import { Store } from '@ngxs/store';
import { AuthState } from '@app/auth/state/auth.state';
import { AuthService } from '@app/auth/services/auth.service';

@Injectable()
export class AuthGuardService implements CanActivate {

  constructor(private router: Router, private store: Store, private authService: AuthService) {}

  canActivate(): boolean {
    const token: string = this.store.selectSnapshot(AuthState.getAccessToken);
    if (!this.authService.isAuthenticated(token)) {
      this.router.navigate(['user/login']);
      return false;
    }
    return true;
  }
}
