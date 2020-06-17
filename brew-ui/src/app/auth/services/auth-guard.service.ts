import { Injectable } from '@angular/core';
import { CanActivateChild, Router } from '@angular/router';
import { AuthService } from '@app/auth/services/auth.service';
import { AuthState } from '@app/auth/state/auth.state';
import { Store } from '@ngxs/store';
import { Logout } from '../state/auth.actions';


@Injectable()
export class AuthGuardService implements CanActivateChild {

  constructor(private readonly router: Router, private readonly store: Store, private readonly authService: AuthService) {}

  canActivateChild(): boolean {
    const token: string = this.store.selectSnapshot(AuthState.getAccessToken);
    if (!this.authService.isAuthenticated(token)) {
      this.store.dispatch(new Logout()).subscribe(
        () => {
         this.router.navigateByUrl('/');
         return false;
        }
      );
    }
    return true;
  }

}
