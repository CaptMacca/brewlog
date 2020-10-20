import { BrewService } from '@app/brew/services/brew.service';
import {
  LoadBrew,
  LoadBrews,
  LoadRecent5Brews,
  RemoveBrew,
  RemoveMeasurement,
  SaveBrew,
  SetSavingBrew,
  UpdateBrew
} from '@app/brew/state/brew.actions';
import { Brew } from '@app/brew/model';
import { Action, Selector, State, StateContext, Store } from '@ngxs/store';
import { append, patch, removeItem, updateItem } from '@ngxs/store/operators';
import { catchError, map, retry, tap } from 'rxjs/operators';
import { forkJoin, Observable, of } from 'rxjs';
import { MeasurementService } from '@app/brew/services/measurement.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ConcurrentUpdateError } from '@app/common/errors/optimistic-lock-error';

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
  }
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

  constructor(
    private readonly store: Store,
    private readonly brewService: BrewService,
    private readonly measurementService: MeasurementService) {}

  @Action(LoadBrews)
  LoadBrews(ctx: StateContext<BrewStateModel>, { payload }: LoadBrews): Observable<Brew[]> {
    return this.brewService.getBrews(payload).pipe(
      tap(brews =>
        ctx.setState(patch({ brews: brews }))
      )
    );
  }

  @Action(LoadRecent5Brews)
  LoadRecent5Brews(ctx: StateContext<BrewStateModel>, { payload }: LoadRecent5Brews): Observable<Brew[]> {
    return this.brewService.getTop5Recent(payload).pipe(
      tap(brews =>
        ctx.setState(patch({ recent5Brews: brews }))
      )
    );
  }

  @Action(LoadBrew)
  LoadBrew(ctx: StateContext<BrewStateModel>, { payload }: LoadBrew): Observable<Brew> {
    const brew$ = this.brewService.getBrew(+payload);
    const notes$ = this.brewService.getBrewNotes(+payload);
    const tastingNotes$ = this.brewService.getBrewTastingNotes(+payload);

    return forkJoin([brew$, notes$, tastingNotes$]).pipe(
      retry(1),
      map(results => {
        const brewWithNotes = {
          ...results[0],
          notes: results[1],
          tastingNotes: results[2]
        };
        ctx.setState(patch({
          brew: brewWithNotes
        }))
        return brewWithNotes;
      }),
      catchError(err => {
        console.log(err);
        throw new Error(err);
      })
    );
  }

  @Action(RemoveBrew)
  RemoveBrew(ctx: StateContext<BrewStateModel>, { payload }: RemoveBrew): Observable<Brew[]> {
    return this.brewService.deleteBrew(+payload.id).pipe(
      tap(() =>
        ctx.setState(patch({
          brew: new Brew(),
          brews: removeItem(b => b === payload)
        }))
      )
    );
  }

  @Action(SaveBrew)
  SaveBrew(ctx: StateContext<BrewStateModel>, { payload }: SaveBrew): Observable<Brew> {
    return this.brewService.saveBrew(payload).pipe(
      tap(brew =>
        ctx.setState(patch({
          brew: brew,
          brews: append([brew])
        }))
      )
    );
  }

  @Action(UpdateBrew)
  UpdateBrew(ctx: StateContext<BrewStateModel>, { payload }: UpdateBrew): Observable<Brew> {
    return this.brewService.updateBrew(payload.id, payload).pipe(
      tap(brew =>
        ctx.setState(patch({
          brew: brew,
          brews: updateItem(b => b.id === payload.id, brew)
        }))
      ),
      catchError( (err: HttpErrorResponse) => {
        if (err.status === 409) {
          // Optimistic Lock Exception
          throw new ConcurrentUpdateError(err.message);
        } else {
          console.log(err);
          throw err;
        }
      })
    );
  }

  @Action(SetSavingBrew)
  SetSavingBrew(ctx: StateContext<BrewStateModel>, { payload }: SetSavingBrew): void {
    ctx.setState(patch({ savingBrew: payload }));
  }

  @Action(RemoveMeasurement)
  RemoveMeasurement(ctx: StateContext<BrewStateModel>, { payload }: RemoveMeasurement) {
    const measurement = payload;
    const brew = this.store.selectSnapshot(BrewState.getBrew);
    return this.measurementService.deleteMeasurement(measurement.id).pipe(
      tap(() => {
        brew.measurements = brew.measurements.filter(m => m !== measurement);
        ctx.setState(patch({
            brew: brew
        }))
      })
    );
  }
}
