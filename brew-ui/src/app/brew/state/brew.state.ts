import { BrewService } from '@app/brew/services/brew.service';
import { LoadBrew, LoadBrews, LoadRecent5Brews, RemoveBrew, SaveBrew, SetSavingBrew, UpdateBrew } from '@app/brew/state/brew.actions';
import { MeasurementState } from '@app/brew/state/measurement.state';
import { Brew } from '@app/model';
import { Action, Selector, State, StateContext, Store } from '@ngxs/store';
import { append, patch, removeItem, updateItem } from '@ngxs/store/operators';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { LoadMeasurements } from '@app/brew/state/measurement.actions';

export class BrewStateModel {
  recent5Brews: Brew[];
  brews: Brew[];
  brew: Brew;
  brewForm: any;
  savingBrew: boolean;
}

@State<BrewStateModel>({
  name: 'brews',
  defaults: {
    recent5Brews: [],
    brews: [],
    brew: new Brew(),
    savingBrew: false,
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

  @Selector()
  static getRecent5Brews(state: BrewStateModel) {
    return state.recent5Brews;
  }

  @Selector()
  static getSavingBrew(state: BrewStateModel) {
    return state.savingBrew;
  }

  constructor(private readonly store: Store, private readonly brewService: BrewService) {}

  @Action(LoadBrews)
  LoadBrews(ctx: StateContext<BrewStateModel>, { payload }: LoadBrews): Observable<Brew[]> {
    return this.brewService.getBrews(payload).pipe(
      tap(brews => {
        ctx.setState(
          patch({
            brews: brews
          })
        )
      })
    );
  }

  @Action(LoadRecent5Brews)
  LoadRecent5Brews(ctx: StateContext<BrewStateModel>, { payload }: LoadRecent5Brews): Observable<Brew[]> {
    return this.brewService.getTop5Recent(payload).pipe(
      tap(brews =>
        ctx.setState(
          patch({
            recent5Brews: brews
          })
        )
      )
    );
  }

  @Action(LoadBrew)
  async LoadBrew(ctx: StateContext<BrewStateModel>, { payload }: LoadBrew): Promise<Brew> {
     const brew = await this.brewService.getBrew(+payload).toPromise();
     ctx.setState(patch({
        brew: brew
      }));
      const measurements = this.store.dispatch(new LoadMeasurements(brew.measurements)).toPromise();
      return brew;
  }

  @Action(RemoveBrew)
  RemoveBrew(ctx: StateContext<BrewStateModel>, { payload }: RemoveBrew): Observable<Brew[]> {
    return this.brewService.deleteBrew(+payload.id).pipe(
      tap(() =>
        ctx.setState(
          patch({
            brew: new Brew(),
            brews: removeItem(b => b === payload)
          })
        )
      )
    );
  }

  @Action(SaveBrew)
  SaveBrew(ctx: StateContext<BrewStateModel>, { payload }: SaveBrew): Observable<Brew> {
    return this.brewService.saveBrew(payload).pipe(
      tap(brew =>
        ctx.setState(
          patch({
            brew: brew,
            brews: append([brew])
          })
        )
      )
    );
  }

  @Action(UpdateBrew)
  UpdateBrew(ctx: StateContext<BrewStateModel>, { payload }: UpdateBrew): Observable<Brew> {
    return this.brewService.updateBrew(payload).pipe(
      tap(brew =>
        ctx.setState(
          patch({
            brew: brew,
            brews: updateItem(b => b.id === payload.id, brew)
          })
        )
      )
    );
  }

  @Action(SetSavingBrew)
  SetSavingBrew(ctx: StateContext<BrewStateModel>, { payload }: SetSavingBrew): void {
    ctx.setState(
      patch({
        savingBrew: payload,
      })
    );
  }
}
