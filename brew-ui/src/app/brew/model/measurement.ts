import { prop, required } from '@rxweb/reactive-form-validators';

export class Measurement {
  @prop()
  id: number;
  @required({message: 'The Measurement Date is required'})
  measurementDate: Date;
  @required({message: 'The Measurement value is required'})
  value: number;
  @prop()
  versionId: number;
}
