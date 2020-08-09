import { Measurement } from '@app/model/measurement';
import { Recipe } from '@app/model/recipe';
import { UserDetails } from '@app/model/user-details';

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
  user: UserDetails;
  measurements: Measurement[];
}
