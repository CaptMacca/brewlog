import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';
import { Recipe, RecipeRating } from '@app/recipe/model';

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

  getTop5RatedRecipes(username: string): Observable<Recipe[]> {
    const params = new HttpParams().set('username', username);
    return this.http.get<Recipe[]>(this.recipeApi + '/top5', {
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

  getRecipeNotes(id: number): Observable<string> {
    return this.http.get(this.recipeApi + '/' + id + '/notes', {
      headers: new HttpHeaders({
        Accept: 'application/json'
      }),
      responseType: 'text',
    });
  }

  deleteRecipe(recipe: Recipe) {
    return this.http.delete(this.recipeApi + '/' + recipe.id);
  }

  updateRecipe(recipe: Recipe) {
    return this.http.put(this.recipeApi + '/' + recipe.id, recipe, {
      headers: new HttpHeaders({
        Accept: 'application/json'
      })
    });
  }

  updateRecipeRating(updateRating: RecipeRating) {
    return this.http.put(this.recipeApi + '/' + updateRating.id + '/rating', updateRating, {
      headers: new HttpHeaders({
        ContentType: 'application/json',
      })
    });
  }
}
