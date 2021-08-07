import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from '@app/recipe/model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-top5recipes',
  templateUrl: './top5recipes.component.html',
  styleUrls: ['./top5recipes.component.css']
})
export class Top5recipesComponent implements OnInit {

  @Input() recipes: Recipe[];

  constructor(private readonly router: Router) { }

  ngOnInit() {
  }

  viewRecipe(recipe: Recipe) {
    if (recipe) {
      console.log(recipe)
      this.router.navigate(['/main/recipes/' + recipe.id]);
    }
  }

}
