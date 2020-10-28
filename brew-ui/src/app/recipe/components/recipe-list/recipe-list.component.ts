import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthState } from '@app/auth/state/auth.state';
import { Recipe } from '@app/recipe/model';
import { LoadRecipes, RemoveRecipe } from '@app/recipe/state/recipe.actions';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { Select, Store } from '@ngxs/store';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-recipe-list',
  styleUrls: ['./recipe-list.component.css'],
  templateUrl: './recipe-list.component.html'
})
export class RecipeListComponent implements OnInit {
  @Select(RecipeState.getRecipes) recipes$: Observable<Recipe[]>;
  loading = false;
  isAllDisplayDataChecked = false;
  isIndeterminate = false;
  selections: Recipe[] = [];
  mapOfCheckedId: { [key: string]: boolean } = {};

  constructor(
    private readonly store: Store,
    private readonly router: Router,
    private readonly message: NzMessageService,
    private readonly modalService: NzModalService,
  ) {}

  ngOnInit(): void {
    this.loadRecipes();
  }

  importRecipes(): void {
    this.router.navigate(['/main/recipes/import']);
  }

  viewRecipe(recipe: Recipe) {
    if (recipe) {
      this.router.navigate(['/main/recipes/' + recipe.id]);
    } else {
      this.message.error('Recipe id was not provided');
    }
  }

  refresh(): void {
    this.loadRecipes();
  }

  private loadRecipes(): void {
    const username = this.store.selectSnapshot(AuthState.getUsername);
    if (username) {
      this.loading = true;
      this.store.dispatch(new LoadRecipes(username)).pipe(
        finalize(() => this.loading = false )
      ).subscribe();
    }
  }

  confirm(): void {
    this.modalService.confirm({
      nzTitle: 'Are you sure delete the selected recipes?',
      nzOkText: 'Yes',
      nzOkType: 'danger',
      nzOnOk: () => this.deleteRecipes(),
      nzCancelText: 'No',
    });
  }

  private deleteRecipes(): void {
    if (this.selections) {
      this.selections.forEach(r => {
        this.store.dispatch(new RemoveRecipe(r));
      });
      this.message.success('Selected Recipes have been deleted');
    }
  }

  checkAll(value: boolean, recipes: Recipe[]): void {
    if (value) {
      recipes.forEach(r => this.check(value, r));
    } else {
      recipes.forEach(r => this.mapOfCheckedId[r.id] = value);
      this.selections = [];
    }
  }

  check(value: boolean, recipe: Recipe) {
    // TODO: untick the all selection checkbox if any of the recipes are unselected and its true
    this.mapOfCheckedId[recipe.id] = value;
    if (!value) {
      this.selections = this.selections.filter(r => r.id !== recipe.id)
      if (this.isAllDisplayDataChecked) {
        this.isAllDisplayDataChecked = false;
      }
    } else {
      if (!this.selections.find(r => r.id === recipe.id)) {
        this.selections.push(recipe);
      }
    }
  }

 }
