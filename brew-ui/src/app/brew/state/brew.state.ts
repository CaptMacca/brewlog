import { State, StateContext, Selector, Action } from '@ngxs/store';
import { patch, removeItem, append, updateItem } from '@ngxs/store/operators';

import { Brew } from '@app/model';
import { LoadBrews, RemoveBrew, LoadBrew, SaveBrew, UpdateBrew } from '@app/brew/state/brew.actions';
import { MeasurementState } from '@app/measurement/state/measurement.state';

export class BrewStateModel {
  brews: Brew[];
  brew: Brew;
  brewForm: any;
}

@State<BrewStateModel>({
  name: 'brews',
  defaults: {
    brews: [],
    brew: new Brew(),
    brewForm: {
      model: undefined,
      dirty: false,
      status: '',
      errors: {}
    }
  },
  children: [MeasurementState]
})
export class BrewState {
  @Selector()
  static getBrews(state: BrewStateModel) {
    return state.brews;
  }

  @Selector()
  static getBrew(state: BrewStateModel) {
    return state.brew;
  }

  constructor() {}

  @Action(LoadBrews)
  LoadBrews(ctx: StateContext<BrewStateModel>, { payload }: LoadBrews) {
    return ctx.setState(patch({
      brews: payload
    }));
  }

  @Action(LoadBrew)
  LoadBrew(ctx: StateContext<BrewStateModel>, { payload }: LoadBrew) {
    return ctx.setState(patch({
      brew: payload
    }));
  }

  @Action(RemoveBrew)
  RemoveBrew(ctx: StateContext<BrewStateModel>, { payload }: RemoveBrew) {
    return ctx.setState(patch({
      brew: new Brew(),
      brews: removeItem(id => id === payload)
    }));
  }

  @Action(SaveBrew)
  SaveBrew(ctx: StateContext<BrewStateModel>, { payload }: SaveBrew) {
    return ctx.setState(patch({
      brew: payload,
      brews: append([payload])
    }));
  }

  @Action(UpdateBrew)
  UpdateBrew(ctx: StateContext<BrewStateModel>, { payload }: UpdateBrew) {
    return ctx.setState(patch({
      brew: payload,
      brews: updateItem(b => b.id === payload.id, payload)
    }));
  }

}
