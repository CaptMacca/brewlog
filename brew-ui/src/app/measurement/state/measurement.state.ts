import { tap, catchError } from 'rxjs/operators';
import { State, Selector, StateContext, Action } from '@ngxs/store';
import { patch, removeItem, append, updateItem } from '@ngxs/store/operators';

import { MeasurementService } from '@app/measurement/services/measurement.service';
import { Measurement } from '@app/model';
import { LoadMeasurement, NewMeasurement,
         RemoveMeasurement, AddMeasurement,
         UpdateMeasurement, LoadMeasurementSuccess,
         LoadMeasurementError, LoadMeasurements } from '@app/measurement/state/measurement.actions';

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

  constructor(private measurementService: MeasurementService) {}

  @Action(NewMeasurement)
  NewMeasurement(ctx: StateContext<MeasurementStateModel>, action: NewMeasurement) {
    const measurement = new Measurement();
    measurement.id = 0;
    return ctx.setState(
      patch({
        measurement: measurement
      })
    );
  }

  @Action(LoadMeasurement)
  LoadMeasurement(ctx: StateContext<MeasurementStateModel>, { payload }: LoadMeasurement) {
    return this.measurementService.getMeasurement(+payload).pipe(
      tap(measurement => ctx.dispatch(new LoadMeasurementSuccess(measurement))),
      catchError(err => ctx.dispatch(new LoadMeasurementError(err)))
    );
  }

  @Action(LoadMeasurementSuccess)
  LoadMeasurementSuccess(ctx: StateContext<MeasurementStateModel>, { payload }: LoadMeasurementSuccess) {
    return ctx.setState(
      patch({
        measurement: payload
      }))
    ;
  }

  @Action(LoadMeasurementError)
  LoadMeasurementError(ctx: StateContext<MeasurementStateModel>, { payload }: LoadMeasurementError) {
    return ctx.setState(
      patch({
        measurement: null
      })
    );
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
    return this.measurementService.deleteMeasurement(+payload.id).pipe(
      tap(() => ctx.setState(
          patch({
            measurement: new Measurement(),
            measurements: removeItem(m => m === payload)
          })
      ))
    );
  }

  @Action(AddMeasurement)
  AddMeasurement(ctx: StateContext<MeasurementStateModel>, { payload }: AddMeasurement) {
    return this.measurementService.saveMeasurement(payload).pipe(
      tap(measurement => ctx.setState(
        patch({
          measurement: measurement,
          measurements: append([measurement])
        })
      ))
    );
  }

  @Action(UpdateMeasurement)
  UpdateMeasurement(ctx: StateContext<MeasurementStateModel>, { payload }: UpdateMeasurement) {
    return this.measurementService.updateMeasurement(payload).pipe(
      tap(measurement => ctx.setState(
        patch({
          measurement: measurement,
          measurements: updateItem(m => m.id === payload.id, measurement)
        })
      ))
    );
  }
}
