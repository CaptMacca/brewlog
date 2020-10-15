import { Measurement } from '@app/brew/model/measurement';
import { UserDetails } from '@app/user/model/user-details';
import { greaterThanEqualTo, prop, propArray } from '@rxweb/reactive-form-validators';
import { CandidateBrew } from '@app/brew/model/candidate-brew';

export class Brew extends CandidateBrew {
  @prop()
  id: number;

  @prop()
  score: number;

  @greaterThanEqualTo({value: 0, message: 'Sparge volume must be greater than 0'})
  spargeVol: number;

  @greaterThanEqualTo({value: 0, message: 'Total water must be greater than 0'})
  totalWater: number;

  @greaterThanEqualTo({value: 0, message: 'Fermenter volume must be greater than 0'})
  fermenterVol: number;

  @greaterThanEqualTo({value: 0, message: 'Measured original gravity must be greater than 0'})
  measuredOriginalGravity: number;

  @greaterThanEqualTo({value: 0, message: 'Measured pre boil gravity must be greater than 0'})
  measuredPreboilGravity: number;

  @greaterThanEqualTo({value: 0, message: 'Measured final gravity must be greater than 0'})
  measuredFinalGravity: number;

  @greaterThanEqualTo({value: 0, message: 'Measured fermenter must be greater than 0'})
  measuredFermentVolume: number;

  @greaterThanEqualTo({value: 0, message: 'Measured bottling volume must be greater than 0'})
  measuredBottleVolume: number;

  @prop()
  notes: string;

  @prop()
  tastingNotes: string;

  @prop()
  user: UserDetails;

  @propArray(Measurement)
  measurements: Measurement[];

  @prop()
  versionId: number;
}
