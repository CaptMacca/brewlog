import { UserDetails } from '@app/model';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { UserService } from '@app/user/services/user.service';
import { ClearUserDetails, GetCurrentUserDetails, SetSavingUser, UpdatePassword, UpdateUserDetails } from '@app/user/state/user.actions';
import { patch } from '@ngxs/store/operators';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UserPassword } from '@app/user/model';

export class UserStateModel {
  userDetails: UserDetails;
  savingUser: boolean;
}

@State<UserStateModel>({
  name: 'user',
  defaults: {
    userDetails: new UserDetails(),
    savingUser: false
  }
})

export class UserState {

  @Selector()
  static getUserDetails(state: UserStateModel) {
    return state.userDetails;
  }

  @Selector()
  static savingUser(state: UserStateModel) {
    return state.savingUser;
  }

  constructor(private readonly userService: UserService) {}

  @Action(GetCurrentUserDetails)
  getCurrentUserDetails(ctx: StateContext<UserStateModel>, getCurrentUserDetailsAction: GetCurrentUserDetails): Observable<UserDetails> {
    return this.userService.getCurrentUserDetails().pipe(
      tap(user => ctx.setState(patch({userDetails: user})))
    )
  }

  @Action(ClearUserDetails)
  unloadUserDetails(ctx: StateContext<UserStateModel>) {
    return ctx.setState(patch({
      userDetails: new UserDetails()
    }));
  }

  @Action(UpdateUserDetails)
  updateUserDetails(ctx: StateContext<UserStateModel>, { payload }:  UpdateUserDetails): Observable<UserDetails> {
    return this.userService.updateUserDetails(payload.username, payload.userDetails).pipe(
      tap(u => ctx.setState(patch({
        userDetails: u
      }))));
  }

  @Action(UpdatePassword)
  updatePassword(ctx: StateContext<UserStateModel>, { payload }: UpdatePassword): Observable<UserPassword> {
    return this.userService.updatePassword(payload);
  }

  @Action(SetSavingUser)
  setSavingUser(ctx: StateContext<UserStateModel>, saving: SetSavingUser) {
    ctx.setState(patch({savingUser: saving.payload}))
  }
}
