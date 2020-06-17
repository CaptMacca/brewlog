import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Recipe } from '@app/model';

@Component({
  selector: 'app-select-recipe',
  templateUrl: './select-recipe.component.html',
  styleUrls: ['./select-recipe.component.css']
})
export class SelectRecipeComponent implements OnInit {

  mapOfCheckedId: { [key: string]: boolean } = {};

  @Input()
  recipes: Recipe[];

  loading: boolean;
  @Output() selectRecipe = new EventEmitter<Recipe>();
  @Output() unselectRecipe = new EventEmitter<Recipe>();

  @Input()
  selectedRecipe: Recipe;

  constructor() { }

  ngOnInit() {
    if (this.selectedRecipe) {
      const idx = this.selectedRecipe.id;
      this.mapOfCheckedId[idx] = true;
    }
  }

  check(value: boolean, recipe: Recipe): void {
    this.mapOfCheckedId = {};
    this.mapOfCheckedId[recipe.id] = value;
    if (value) {
      this.selectedRecipe = recipe;
      this.selectRecipe.emit(recipe);
    } else {
      this.selectedRecipe = undefined;
      this.unselectRecipe.emit(recipe);
    }
  }

}
