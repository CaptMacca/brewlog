import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthState } from '@app/auth/state/auth.state';
import { LoadBrews, RemoveBrew } from '@app/brew/state/brew.actions';
import { BrewState } from '@app/brew/state/brew.state';
import { Brew } from '@app/brew/model';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { finalize, startWith, tap } from 'rxjs/operators';

@Component({
  selector: 'app-brew-list',
  templateUrl: './brew-list.component.html',
  styleUrls: ['./brew-list.component.css']
})
export class BrewListComponent implements OnInit {

  @Select(BrewState.getBrews) brews$: Observable<Brew[]>
  @Select(AuthState.getUsername) username$: Observable<string>

  loading: Boolean
  // username: string;
  selections: Brew[] = []
  isAllDisplayDataChecked = false
  isIndeterminate = false
  mapOfCheckedId: { [key: string]: boolean } = {}

  constructor(
    private readonly store: Store,
    private readonly router: Router,
    private readonly message: NzMessageService,
    private readonly modalService: NzModalService,
  ) {}

  ngOnInit(): void {
    this.loadBrews()
  }

  refresh() {
    this.loadBrews()
  }

  editBrew(selectedBrew: Brew): void {
    if (selectedBrew) {
      this.router.navigate(['/main/brews/' + selectedBrew.id])
    }
  }

  private loadBrews() {
    const username = this.store.selectSnapshot(AuthState.getUsername);
    if (username) {
      this.store.dispatch(new LoadBrews(username)).pipe(
        startWith(this.loading = true),
        tap(() => console.log(`loading brews for user: ${username}`)),
        finalize(() => {
          this.loading = false
        })
      ).subscribe()
    }
  }

  private deleteBrew(brew: Brew): void {
    if (brew) {
      this.store.dispatch(new RemoveBrew(brew)).subscribe(
      state => this.message.success('Brew session has been successfully deleted.')
      )
    }
  }

  addBrew(): void {
    this.router.navigate(['/main/brews/add'])
  }

  confirm(): void {
    this.modalService.confirm({
      nzTitle: 'Are you sure delete the selected brews?',
      nzOkText: 'Yes',
      nzOkType: 'danger',
      nzOnOk: () => this.deleteBrews(),
      nzCancelText: 'No',
    })
  }

  private deleteBrews(): void {
    if (this.selections) {
      this.selections.forEach(b => {
        this.store.dispatch(new RemoveBrew(b));
      });
      this.message.success('Selected Recipes have been deleted')
    }
  }

  checkAll(value: boolean, brews: Brew[]): void {
    if (value) {
      brews.forEach(b => this.check(value, b))
    } else {
      brews.forEach(b => this.mapOfCheckedId[b.id] = value)
      this.selections = []
    }
  }

  check(value: boolean, brew: Brew) {
    this.mapOfCheckedId[brew.id] = value
    if (!value) {
      this.selections = this.selections.filter(b => b.id !== brew.id)
      if (this.isAllDisplayDataChecked) {
        this.isAllDisplayDataChecked = false
      }
    } else {
      if (!this.selections.find(b => b.id === brew.id)) {
        this.selections.push(brew)
      }
    }
  }
}
