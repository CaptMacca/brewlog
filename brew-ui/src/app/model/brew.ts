import { Measurement } from '@app/model/measurement';
import { Recipe } from '@app/model/recipe';
import { User } from '@app/model/user';

export class Brew {
  id: number;
  brewDate: Date;
  score: number;
  spargeVol: number;
  totalWater: number;
  fermenterVol: number;
  estimatedOriginalGravity: number;
  measuredOriginalGravity: number;
  estimatedPreboilGravity: number;
  measuredPreboilGravity: number;
  estimatedFinalGravity: number;
  measuredFinalGravity: number;
  estimatedFermentVolume: number;
  measuredFermentVolume: number;
  estimatedBottleVolume: number;
  measuredBottleVolume: number;
  notes: string;
  tastingNotes: string;
  recipe: Recipe;
  user: User;
  measurements: Measurement[];
}
