import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Store } from '@ngxs/store';
import { UserDetails } from '@app/user/model';
import { GetCurrentUserDetails } from '@app/user/state/user.actions';

@Injectable()
export class UserDetailsResolverService  {

  constructor(private router: Router, private readonly store: Store) { }

  resolve(route: ActivatedRouteSnapshot): Observable<UserDetails> {
      return this.store.dispatch(new GetCurrentUserDetails());
  }
}
