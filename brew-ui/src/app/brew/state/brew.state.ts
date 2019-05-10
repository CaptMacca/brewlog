import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

import { State, StateContext, Selector, Action } from '@ngxs/store';

import { Brew } from '@app/model';
import { LoadBrews, RemoveBrew, LoadBrew, SaveBrew, UpdateBrew } from '@app/brew/state/brew.actions';
import { BrewService } from '@app/brew/services/brew.service';
import { MeasurementState } from '@app/measurement/state/measurement.state';
import { patch, removeItem, append, updateItem } from '@ngxs/store/operators';

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

  constructor(private brewService: BrewService) {}

  @Action(LoadBrews)
  LoadBrews(ctx: StateContext<BrewStateModel>, { payload }: LoadBrews) {
    return this.brewService.getBrews(payload).pipe(
      tap(brews => ctx.setState(
        patch({
          brews: brews
        })
      ))
    );
  }

  @Action(LoadBrew)
  LoadBrew(ctx: StateContext<BrewStateModel>, { payload }: LoadBrew) {
    return this.brewService.getBrew(+payload).pipe(
      tap(brew => ctx.setState(
        patch({
          brew: brew
        })
      ))
    );
  }

  @Action(RemoveBrew)
  RemoveBrew(ctx: StateContext<BrewStateModel>, { payload }: RemoveBrew) {
    return this.brewService.deleteBrew(+payload).pipe(
      tap(() => ctx.setState(
        patch({
          brew: new Brew(),
          brews: removeItem(id => id === payload)
        })
      ))
    );
  }

  @Action(SaveBrew)
  SaveBrew(ctx: StateContext<BrewStateModel>, { payload }: SaveBrew): Observable<Brew> {
    return this.brewService.saveBrew(payload).pipe(
      tap(brew => ctx.setState(
        patch({
          brew: brew,
          brews: append([brew])
        })
      ))
    );
  }

  @Action(UpdateBrew)
  UpdateBrew(ctx: StateContext<BrewStateModel>, { payload }: UpdateBrew) {
    return this.brewService.updateBrew(payload).pipe(
      tap(brew => ctx.setState(
        patch({
          brew: brew,
          brews: updateItem(b => b.id === payload.id, brew)
        })
      ))
    );
  }

}
