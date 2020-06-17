import { Component, OnInit } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { LoadTop5Recipes } from '@app/recipe/state/recipe.actions';
import { LoadRecent5Brews } from '@app/brew/state/brew.actions';
import { AuthState } from '@app/auth/state/auth.state';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { BrewState } from '@app/brew/state/brew.state';
import { Observable } from 'rxjs';
import { Brew, Recipe } from '@app/model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  @Select(RecipeState.getTop5Recipe) recipes$: Observable<Recipe[]>;
  @Select(BrewState.getRecent5Brews) brews$: Observable<Brew[]>;
  @Select(AuthState.getUsername) username$: Observable<string>;

  constructor(private readonly store: Store) { }

  ngOnInit() {
    this.username$.subscribe(
      username => {
        this.store.dispatch([
          new LoadTop5Recipes(username),
          new LoadRecent5Brews(username)
        ]);
      }
    );
  }

}
