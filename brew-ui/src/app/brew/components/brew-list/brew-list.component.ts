import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { SimpleModalService } from 'ngx-simple-modal';
import { Observable } from 'rxjs';
import { Select, Store } from '@ngxs/store';

import { ConfirmComponent } from '@app/common/confirm/confirm.component';
import { Brew } from '@app/model';
import { BrewState } from '@app/brew/state/brew.state';
import { LoadBrews, RemoveBrew, LoadBrew } from '@app/brew/state/brew.actions';
import { AuthState } from '@app/auth/state/auth.state';

@Component({
  selector: 'app-brew-list',
  templateUrl: './brew-list.component.html',
  styleUrls: ['./brew-list.component.css']
})
export class BrewListComponent implements OnInit {
  @Select(BrewState.getBrews) brews$: Observable<Brew[]>;

  constructor(
    private store: Store,
    private router: Router,
    private simpleModalService: SimpleModalService
  ) {}

  ngOnInit(): void {
    const username = this.store.selectSnapshot(AuthState.getUsername);
    this.store.dispatch(new LoadBrews(username));
  }

  editBrew(id: number): void {
    this.store.dispatch(new LoadBrew(id)).subscribe(
      () => {
        this.router.navigate(['/brews/' + id]);
      });
  }

  private deleteBrew(id: number): void {
    this.store.dispatch(new RemoveBrew(id));
  }

  addBrew(): void {
    this.router.navigate(['/brews/add']);
  }

  confirmDelete(id: number) {
    this.simpleModalService.addModal(ConfirmComponent, {
        title: 'Confirm Delete',
        message: 'Are you sure you wish to delete this brew.'
      }).subscribe(
        isConfirmed => isConfirmed ? this.deleteBrew(id) : null
      );
  }
}