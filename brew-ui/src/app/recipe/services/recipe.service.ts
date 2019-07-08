import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '@env/environment';

import { Recipe } from '@app/model';

@Injectable()
export class RecipeService {

  private recipeApi = environment.recipeApiUrl;

  constructor(private http: HttpClient) {}

  getRecipes(username: string): Observable<Recipe[]> {
    const params = new HttpParams().set('username', username);
    return this.http.get<Recipe[]>(this.recipeApi, {
      headers: new HttpHeaders({
        Accept: 'application/json'
      }),
      params: params
    });
  }

  getRecipe(id: number): Observable<Recipe> {
    return this.http.get<Recipe>(this.recipeApi + '/' + id, {
      headers: new HttpHeaders({
        Accept: 'application/json'
      })
    });
  }

  deleteRecipe(recipe: Recipe) {
    return this.http.delete(this.recipeApi + '/' + recipe.id);
  }
}
