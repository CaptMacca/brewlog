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
import { BrewService } from '@app/brew/services/brew.service';

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
    private simpleModalService: SimpleModalService,
    private brewService: BrewService
  ) {}

  ngOnInit(): void {
    const username = this.store.selectSnapshot(AuthState.getUsername);
    if (username) {
      this.brewService.getBrews(username).subscribe(
        brews => this.store.dispatch(new LoadBrews(brews))
      );
    }

  }

  editBrew(selectedBrew: Brew): void {
    this.brewService.getBrew(selectedBrew.id).subscribe(
      brew => {
        this.store.dispatch(new LoadBrew(brew));
        this.router.navigate(['/brews/' + brew.id]);
      }
    );
  }

  private deleteBrew(brew: Brew): void {
    this.brewService.deleteBrew(brew.id).subscribe(
      () => this.store.dispatch(new RemoveBrew(brew))
    );
  }

  addBrew(): void {
    this.router.navigate(['/brews/add']);
  }

  confirmDelete(brew: Brew) {
    this.simpleModalService.addModal(ConfirmComponent, {
        title: 'Confirm Delete',
        message: 'Are you sure you wish to delete this brew.'
      }).subscribe(
        isConfirmed => isConfirmed ? this.deleteBrew(brew) : null
      );
  }
}
