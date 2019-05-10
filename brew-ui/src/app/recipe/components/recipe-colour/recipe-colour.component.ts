import { Component, OnInit, Input, OnChanges } from '@angular/core';

import { MathUtils } from '@app/common/utils/math';

@Component({
  selector: 'app-recipe-colour',
  templateUrl: './recipe-colour.component.html',
  styleUrls: ['./recipe-colour.component.css']
})
export class RecipeColourComponent implements OnInit, OnChanges {

  @Input() recipeColour: string;
  @Input() imgWidth = '80';
  @Input() imgHeight = '150';
  public cssValue: string;

  ngOnInit() {}

  ngOnChanges() {
    if (this.recipeColour) {
        this.cssValue = this.calculateCSS(this.parseSRM());
    }
  }

  private parseSRM(): number {
    let srm;
    const colourComponents = this.recipeColour.toLowerCase().split(' ');

    if ((colourComponents.length === 2) && (colourComponents[1] === 'ebc')) {
      srm = this.convertEBC2SRM(colourComponents[0]);
    } else {
      // Assume its a SRM colour
      srm = MathUtils.round(parseFloat(colourComponents[0]), 1);
    }

    return srm;
  }

  private convertEBC2SRM(ebc: string): number {
    return MathUtils.round(parseFloat(ebc) / 2, 1);
  }

  private calculateCSS(srm: number): string {
    let calc;
    if (srm <= 30) {
      calc = 'srm_' + srm.toString().replace('.', '_');
    } else {
      // SRM past 30 basically the same colour, can you have a blacker black.
      calc = 'srm_30+';
    }
    return calc;
  }

}
