import { Action, Selector, State, StateContext, Store } from '@ngxs/store';
import { patch, removeItem } from '@ngxs/store/operators';

import { Measurement } from '@app/model';
import { DeleteMeasurement, LoadMeasurements, RemoveMeasurement, SaveMeasurements } from '@app/brew/state/measurement.actions';
import { MeasurementService } from '@app/brew/services/measurement.service';
import { tap } from 'rxjs/operators';

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

  constructor(private readonly store: Store, private readonly measurementService: MeasurementService) {}

  @Action(LoadMeasurements)
  LoadMeasurements(ctx: StateContext<MeasurementStateModel>, { payload }: LoadMeasurements) {
    return ctx.setState(
      patch({
        measurements: payload
      })
    );
  }

  @Action(DeleteMeasurement)
  DeleteMeasurement(ctx: StateContext<MeasurementStateModel>, { payload }: DeleteMeasurement) {
    return this.measurementService.deleteMeasurement(payload).pipe(
      tap(measurement => ctx.setState(patch({
        measurements: removeItem(m => m === measurement)
      })))
    );
  }

  @Action(RemoveMeasurement)
  RemoveMeasurement(ctx: StateContext<MeasurementStateModel>, { payload}: RemoveMeasurement) {
    ctx.setState(patch({
      measurements: removeItem(m => m === payload)
    }));
  }

  @Action(SaveMeasurements)
  SaveMeasurement(ctx: StateContext<MeasurementStateModel>, { payload }: SaveMeasurements) {
    return this.measurementService.saveMeasurements(payload).pipe(
      tap(m => {
        ctx.setState(patch({
          measurements: m
        }));
      })
    );
  }
}
