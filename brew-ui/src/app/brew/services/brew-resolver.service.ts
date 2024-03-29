import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Brew } from '@app/brew/model';
import { Observable, of } from 'rxjs';
import { Store } from '@ngxs/store';
import { LoadBrew } from '@app/brew/state/brew.actions';

@Injectable()
export class BrewResolverService  {

  constructor(private router: Router, private readonly store: Store) { }

  resolve(route: ActivatedRouteSnapshot): Observable<Brew> {
    const id = +route.paramMap.get('id');
    if (isNaN(id)) {
      this.router.navigate(['/main/brews']);
      return of(null);
    } else {
      return this.store.dispatch(new LoadBrew(id));
    }
  }
}
