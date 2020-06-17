import { Component, OnInit } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { AuthState } from '@app/auth/state/auth.state';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { Logout } from '@app/auth/state/auth.actions';

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  styles: []
})
export class MainLayoutComponent implements OnInit {

  @Select(AuthState.isLoggedIn) isLoggedIn$;
  isCollapsed = false;

  constructor(private readonly store: Store, private readonly router: Router, private readonly message: NzMessageService) { }

  ngOnInit() {
  }

  toggleCollapsed(): void {
    this.isCollapsed = !this.isCollapsed;
  }

  logout() {
    this.store.dispatch(new Logout()).subscribe(
      () => {
        this.router.navigateByUrl('/');
        this.message.success('You have been logged out');
      }
    );
  }
}
