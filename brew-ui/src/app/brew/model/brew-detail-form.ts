import { greaterThanEqualTo, prop, required } from '@rxweb/reactive-form-validators';

export class BrewDetailForm {
  @required({message: 'The brew date is required.'})
  brewDate: Date;

  @prop()
  score: string;

  @greaterThanEqualTo({value: 0, message: 'Sparge volume must be greater than 0'})
  spargeVol: string;

  @greaterThanEqualTo({value: 0, message: 'Total water must be greater than 0'})
  totalWater: string;

  @greaterThanEqualTo({value: 0, message: 'Fermenter volume must be greater than 0'})
  fermenterVol: string;

  @greaterThanEqualTo({value: 0, message: 'Estimated original gravity must be greater than 0'})
  estimatedOriginalGravity: string

  @greaterThanEqualTo({value: 0, message: 'Measured original gravity must be greater than 0'})
  measuredOriginalGravity: string;

  @greaterThanEqualTo({value: 0, message: 'Estimated pre boil gravity must be greater than 0'})
  estimatedPreboilGravity: string;

  @greaterThanEqualTo({value: 0, message: 'Measured pre boil gravity must be greater than 0'})
  measuredPreboilGravity: string;

  @greaterThanEqualTo({value: 0, message: 'Estimated final gravity must be greater than 0'})
  estimatedFinalGravity: string;

  @greaterThanEqualTo({value: 0, message: 'Measured final gravity must be greater than 0'})
  measuredFinalGravity: string;

  @greaterThanEqualTo({value: 0, message: 'Estimated fermenter volume must be greater than 0'})
  estimatedFermentVolume: string;

  @greaterThanEqualTo({value: 0, message: 'Measured fermenter must be greater than 0'})
  measuredFermentVolume: string;

  @greaterThanEqualTo({value: 0, message: 'Estimated bottling volume must be greater than 0'})
  estimatedBottleVolume: string;

  @greaterThanEqualTo({value: 0, message: 'Measured bottling volume must be greater than 0'})
  measuredBottleVolume: string;

  @prop()
  notes: string;

  @prop()
  tastingNotes: string;
}
