import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Store, Select } from '@ngxs/store';
import { ToastrService } from 'ngx-toastr';
import { SimpleModalService } from 'ngx-simple-modal';
import { Observable } from 'rxjs';

import { Recipe } from '@app/model';

import { ConfirmComponent } from '@app/common/confirm/confirm.component';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { RemoveRecipe } from '@app/recipe/state/recipe.actions';
import { RecipeService } from '@app/recipe/services/recipe.service';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {
  @Select(RecipeState.getRecipe) recipe$: Observable<Recipe>;

  constructor(
    private store: Store,
    private router: Router,
    private toastr: ToastrService,
    private simpleModalService: SimpleModalService,
    private recipeService: RecipeService
  ) {}

  ngOnInit() {}

  gotoRecipes() {
    this.router.navigate(['/recipes']);
  }

  deleteRecipe(recipe: Recipe) {
    this.recipeService.deleteRecipe(recipe).subscribe(
      () => {
        this.store.dispatch(new RemoveRecipe(recipe));
        this.toastr.success('Recipe has been deleted.', 'Recipe');
        this.gotoRecipes();
      },
      err => this.toastr.error('Problem deleting the recipe', 'Recipe')
    );
  }

  confirmDelete(recipe: Recipe) {
    const disposable = this.simpleModalService
      .addModal(ConfirmComponent, {
        title: 'Confirm Delete',
        message: `Are you sure you wish to delete this recipe.
                      Note any associated brews will also be deleted.`
      })
      .subscribe(isConfirmed => {
        if (isConfirmed) {
          this.deleteRecipe(recipe);
        }
      });
    setTimeout(() => {
      disposable.unsubscribe();
    }, 10000);
  }
}
