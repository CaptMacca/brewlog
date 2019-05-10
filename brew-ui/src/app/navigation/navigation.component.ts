import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';

import { Store, Select } from '@ngxs/store';
import { Observable, Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

import { AuthState } from '@app/auth/state/auth.state';
import { Logout } from '@app/auth/state/auth.actions';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html'
})
export class NavigationComponent implements OnInit, OnDestroy {

  @Select(AuthState.isLoggedIn) isLoggedIn$: Observable<boolean>;
  authority: string;
  loggedInSubscription: Subscription;

  constructor(private store: Store, private router: Router, private toastr: ToastrService) {}

  ngOnInit() {

    this.loggedInSubscription = this.isLoggedIn$.subscribe(
      data => this.setAuthority()
    );
  }

  ngOnDestroy() {
    this.loggedInSubscription.unsubscribe();
  }

  private setAuthority() {
    this.authority = null;
    const token = this.store.selectSnapshot(AuthState.getAccessToken);
    const roles = this.store.selectSnapshot(AuthState.getAuthorities);
    if (token) {
      const isAdmin = roles.every(role => role === 'ROLE_ADMIN');
      if (isAdmin) {
        this.authority = 'admin';
      } else {
        this.authority = 'user';
      }
    }
  }

  logout() {
    this.store.dispatch(new Logout()).subscribe(
      () => {
        this.router.navigateByUrl('/');
        this.toastr.success('You have been logged out', 'Login');
      }
    );
  }

}
