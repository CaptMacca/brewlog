import { Mash } from './mash';
import { Fermentable } from './fermentable';
import { Hop } from './hop';
import { Yeast } from './yeast';
import { Ingredient } from './ingredient';

export class Recipe {
  id: number;
  name: string;
  type: string;
  style: string;
  estimatedIbu: string;
  estimatedAbv: string;
  estimatedColour: string;
  batchSize: string;
  originalGravity: string;
  finalGravity: string;
  boilTime: string;
  notes: string;
  rating: number;
  ingredients: Ingredient[];
  fermentables: Fermentable[];
  hops: Hop[];
  yeasts: Yeast[];
  mashes: Mash[];
}
