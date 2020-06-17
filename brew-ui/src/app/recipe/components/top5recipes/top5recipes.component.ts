import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from '@app/model';

@Component({
  selector: 'app-top5recipes',
  templateUrl: './top5recipes.component.html',
  styleUrls: ['./top5recipes.component.css']
})
export class Top5recipesComponent implements OnInit {

  @Input() recipes: Recipe[];

  constructor() { }

  ngOnInit() {
  }

}
