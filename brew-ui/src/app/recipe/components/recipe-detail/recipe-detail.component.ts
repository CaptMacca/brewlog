import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { AuthState } from '@app/auth/state/auth.state';
import { Recipe } from '@app/recipe/model';
import { LoadRecipes, RemoveRecipe, UpdateRecipeRating } from '@app/recipe/state/recipe.actions';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { Select, Store } from '@ngxs/store';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { Observable } from 'rxjs';
import { filter } from 'rxjs/operators';
import { RecipeRating } from '@app/recipe/model/recipe-rating';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {
  @Select(RecipeState.getRecipe) recipe$: Observable<Recipe>;
  rating: number;

  constructor(
    private readonly store: Store,
    private readonly router: Router,
    private readonly message: NzMessageService,
    private readonly modalService: NzModalService,
  ) { }

  ngOnInit() {
    this.router.events.pipe(
      filter((e): e is NavigationEnd => e instanceof NavigationEnd)
    ).subscribe(
      (event) => {
        const recipe = this.recipe$.subscribe((r: Recipe) => this.rating = r.rating);
      }
    );
  }

  gotoRecipes(): void {
    this.router.navigate(['/main/recipes']);
  }

  private deleteRecipe(recipe: Recipe): void {
    const username = this.store.selectSnapshot(AuthState.getUsername);
    this.store.dispatch([
      new RemoveRecipe(recipe),
      new LoadRecipes(username)
    ]).subscribe(
      state => {
        this.message.success('Recipe has been deleted.');
        this.gotoRecipes();
      },
      err => this.message.error('Problem deleting the recipe')
    );
  }

  confirm(recipe: Recipe): void {
    this.modalService.confirm({
      nzTitle: 'Are you sure you want to delete the recipe?',
      nzOkText: 'Yes',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzOnOk: () => this.deleteRecipe(recipe),
      nzCancelText: 'No',
    });
  }

  brewRecipe(recipe: Recipe): void {
    this.router.navigate(['/main/brews/add']);
  }

  updateRating(recipe: Recipe): void {
    const updateRating = new RecipeRating();
    updateRating.recipe = recipe;
    updateRating.rating = this.rating;
    this.store.dispatch(new UpdateRecipeRating(updateRating));
  }
}
