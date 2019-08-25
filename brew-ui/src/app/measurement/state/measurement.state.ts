import { State, Selector, StateContext, Action } from '@ngxs/store';
import { patch, removeItem, append, updateItem } from '@ngxs/store/operators';

import { Measurement } from '@app/model';
import { LoadMeasurement, NewMeasurement,
         RemoveMeasurement, AddMeasurement,
         UpdateMeasurement, LoadMeasurements } from '@app/measurement/state/measurement.actions';

export class MeasurementStateModel {
  measurements: Measurement[];
  measurement: Measurement;
}

@State<MeasurementStateModel>({
  name: 'measurements',
  defaults: {
    measurements: [],
    measurement: new Measurement()
  }
})
export class MeasurementState {
  @Selector()
  static getMeasurements(state: MeasurementStateModel) {
    return state.measurements;
  }

  @Selector()
  static getMeasurement(state: MeasurementStateModel) {
    return state.measurement;
  }

  constructor() {}

  @Action(NewMeasurement)
  NewMeasurement(ctx: StateContext<MeasurementStateModel>, action: NewMeasurement) {
    const measurement = new Measurement();
    measurement.id = 0;
    return ctx.setState(patch({
      measurement: measurement
    }));
  }

  @Action(LoadMeasurement)
  LoadMeasurement(ctx: StateContext<MeasurementStateModel>, { payload }: LoadMeasurement) {
    return ctx.setState(patch({
      measurement: payload
    }));
  }

  @Action(LoadMeasurements)
  LoadMeasurements(ctx: StateContext<MeasurementStateModel>, { payload }: LoadMeasurements) {
    return ctx.setState(
      patch({
        measurements: payload
      })
    );
  }

  @Action(RemoveMeasurement)
  RemoveMeasurement(ctx: StateContext<MeasurementStateModel>, { payload }: RemoveMeasurement) {
    return ctx.setState(patch({
      measurement: new Measurement(),
      measurements: removeItem(m => m === payload)
    }));
  }

  @Action(AddMeasurement)
  AddMeasurement(ctx: StateContext<MeasurementStateModel>, { payload }: AddMeasurement) {
    return ctx.setState(patch({
      measurement: payload,
      measurements: append([payload])
    }));
  }

  @Action(UpdateMeasurement)
  UpdateMeasurement(ctx: StateContext<MeasurementStateModel>, { payload }: UpdateMeasurement) {
    return ctx.setState(patch({
      measurement: payload,
      measurements: updateItem(m => m.id === payload.id, payload)
    }));
  }
}
