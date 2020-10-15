import { required, lessThanEqualTo, prop } from '@rxweb/reactive-form-validators';

export class CandidateBrew {

  @prop()
  recipeId: number;

  @prop()
  recipeName: string;

  @required({ message: 'The date of the brew session is required' })
  brewDate: Date;

  @required({ message: 'The estimated original gravity is required' })
  estimatedOriginalGravity: string;

  @required({ message: 'Please provide an estimated final gravity' })
  @lessThanEqualTo({
    fieldName: 'estimatedOriginalGravity',
    message: 'Estimated Final Gravity should be less than or equal to Estimated Original Gravity'
  })
  estimatedFinalGravity: string;

  @required({ message: 'Please provide an estimated pre-boil gravity' })
  @lessThanEqualTo({
    fieldName: 'estimatedOriginalGravity',
    message: 'Estimated Pre-Boil Gravity should be less than or equal to Estimated Original Gravity'
  })
  estimatedPreboilGravity: string;

  @required({ message: 'Please provide an Estimated Fermentation Volume' })
  estimatedFermentVolume: string;

  @required({ message: 'Please provide an Estimated Bottling Volume'})
  @lessThanEqualTo({
    fieldName: 'estimatedFermentVolume',
    message: 'Estimated Bottling Volume should be less than or equal to the Estimated Fermentation Volume'
  })
  estimatedBottleVolume: string;
}
