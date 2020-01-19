import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Recipe } from '@app/model';
import { MenuItem } from 'primeng/components/common/menuitem';

@Component({
  selector: 'app-select-recipe',
  templateUrl: './select-recipe.component.html',
  styleUrls: ['./select-recipe.component.css']
})
export class SelectRecipeComponent implements OnInit {

  @Input()
  recipes: Recipe[];

  @Output() rowSelected = new EventEmitter<number>();
  @Output() rowUnselected = new EventEmitter<number>();
  @Output() toggleSelected = new EventEmitter<boolean>();
  @Output() viewRecipe = new EventEmitter<Recipe>();
  @Output() deleteRecipe = new EventEmitter<Recipe>();

  cols: any[];

  first = 0;

  selectedRecipes: Recipe[];

  menuItems: MenuItem[];
  itemRecipe: Recipe;

  constructor() { }

  ngOnInit() {

    this.cols = [
      {header: 'Name', field: 'name' },
      {header: 'Type', field: 'type'},
      {header: 'Style', field: 'style'},
      {header: 'ABV', field: 'estimatedAbv'},
      {header: 'Colour', field: 'estimatedColour'},
      {header: 'IBU', field: 'estimatedIbu'},
    ];

    this.menuItems = [
      {
        label: 'View Recipe',
        command: (event) => {
          if (this.itemRecipe) {
            this.viewRecipe.emit(this.itemRecipe);
          }
        },
      },
      {
        label: 'Delete Recipe',
        command: (event) => {
          if (this.itemRecipe) {
            this.deleteRecipe.emit(this.itemRecipe);
          }
        }
      }
    ];
  }

  reset() {
    this.first = 0;
  }

  selectRecipe(event: any) {
    const selectedRecipe = event.data;
    this.rowSelected.emit(selectedRecipe);
  }

  unselectRecipe(event: any) {
    const unselectedRecipe = event.data;
    this.rowUnselected.emit(unselectedRecipe);
  }

  toggleSelection(event: any) {
    const checked = event.checked;
    this.toggleSelected.emit(checked);
  }

  toggleMenu(menu, event, item) {
    this.itemRecipe = item;
    menu.toggle(event);
  }

}
