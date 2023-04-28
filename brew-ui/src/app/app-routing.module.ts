import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from '@app/user/components/register/register.component';
import { AuthGuardService } from '@app/auth/services/auth-guard.service';
import { BrewAddComponent } from '@app/brew/components/brew-add/brew-add.component';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { BrewListComponent } from '@app/brew/components/brew-list/brew-list.component';
import { BrewEditGuard } from '@app/brew/services/brew-guard.service';
import { ImportRecipeComponent } from '@app/recipe/components/import-recipe/import-recipe.component';
import { RecipeDetailComponent } from '@app/recipe/components/recipe-detail/recipe-detail.component';
import { RecipeListComponent } from '@app/recipe/components/recipe-list/recipe-list.component';
import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service';
import { WelcomeComponent } from '@app/welcome/welcome.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MainLayoutComponent } from '@app/layouts/main-layout/main-layout.component';
import { LoginLayoutComponent } from '@app/layouts/login-layout/login-layout.component';
import { BrewResolverService } from '@app/brew/services/brew-resolver.service';
import { EditRegistrationComponent } from '@app/user/components/edit-registration/edit-registration.component';
import { ChangePasswordComponent } from '@app/user/components/change-password/change-password.component';
import { UserDetailsResolverService } from '@app/user/services/user-details-resolver.service';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'welcome/login' },
  { path: 'welcome', component: LoginLayoutComponent, children: [
    { path: 'login', component: WelcomeComponent },
    { path: 'signup', component: RegisterComponent },
  ]},
  { path: 'main', component: MainLayoutComponent,  canActivateChild: [AuthGuardService], children: [
    { path: 'dashboard', component: DashboardComponent,  },
    { path: 'edit-registration', component: EditRegistrationComponent,  resolve: { userDetails: UserDetailsResolverService}},
    { path: 'change-password', component: ChangePasswordComponent},
    { path: 'recipes',
      children: [
        { path: '', component: RecipeListComponent, },
        { path: 'import', component: ImportRecipeComponent, },
        { path: ':id', component: RecipeDetailComponent, resolve: { recipe: RecipeResolverService }}
      ]
    },
    {
      path: 'brews',
      children: [
        { path: '', component: BrewListComponent, },
        { path: 'add', component: BrewAddComponent, },
        { path: ':id', component: BrewDetailComponent, resolve: { brew: BrewResolverService }, canDeactivate: [BrewEditGuard] },
      ]
    }
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules, relativeLinkResolution: 'legacy' })],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {}
