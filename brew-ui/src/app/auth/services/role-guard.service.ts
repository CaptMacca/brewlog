import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot } from '@angular/router';

import { Store } from '@ngxs/store';

import { AuthService } from '@app/auth/services/auth.service';
import { AuthState } from '@app/auth/state/auth.state';

@Injectable()
export class RoleGuardService implements CanActivate {

  constructor(public auth: AuthService, private store: Store, public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    /* this will be passed from the route config on the data property i.e.
    /*
    {
      path: 'admin',
      component: AdminComponent,
      canActivate: [RoleGuard],
      data: {
        expectedRole: 'admin'
      }
    }
    */
    const expectedRole = route.data.expectedRole;
    const token: string = this.store.selectSnapshot(AuthState.getAccessToken);
    const roles: string[] = this.store.selectSnapshot(AuthState.getAuthorities);
    // decode the token to get its payload
    if (!this.auth.isAuthenticated(token) || roles.includes(expectedRole)) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
