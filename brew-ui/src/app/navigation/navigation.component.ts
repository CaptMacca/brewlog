import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthState } from '@app/auth/state/auth.state';
import { Select, Store } from '@ngxs/store';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit, OnDestroy {

  @Select(AuthState.isLoggedIn) isLoggedIn$: Observable<boolean>;
  authority: string;
  loggedInSubscription: Subscription;
  isCollapsed: false;

  constructor(private readonly store: Store) {}

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

}
