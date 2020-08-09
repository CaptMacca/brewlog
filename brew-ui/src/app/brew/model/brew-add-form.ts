import { required, lessThanEqualTo } from '@rxweb/reactive-form-validators';

export class BrewAddForm {
  @required({ message: 'Please set the date of the brew session' })
  brewDate: Date;

  @required({ message: 'Please provide an estimated original gravity' })
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
