import { NgModule } from '@angular/core';
import { PreloadAllModules, NoPreloading, Routes, RouterModule } from '@angular/router';

import { WelcomeComponent } from '@app/welcome/welcome.component';
import { RecipeListComponent } from '@app/recipe/components/recipe-list/recipe-list.component';
import { RecipeDetailComponent } from '@app/recipe/components/recipe-detail/recipe-detail.component';
import { ImportRecipeComponent } from '@app/recipe/components/import-recipe/import-recipe.component';
import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service';
import { BrewListComponent } from '@app/brew/components/brew-list/brew-list.component';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { BrewAddComponent } from '@app/brew/components/brew-add/brew-add.component';
import { MeasurementDetailComponent } from '@app/measurement/components/measurement-detail/measurement-detail.component';
import { MeasurementEditGuard } from '@app/measurement/services/measurement-guard.service';
import { MeasurementListComponent } from '@app/measurement/components/measurement-list/measurement-list.component';
import { BrewEditGuard } from '@app/brew/services/brew-guard.service';
import { LoginComponent } from '@app/auth/components/login/login.component';
import { RegisterComponent } from '@app/auth/components/register/register.component';
import { AuthGuardService } from '@app/auth/services/auth-guard.service';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'welcome' },
  { path: 'welcome', component: WelcomeComponent },
  { path: 'user/login', component: LoginComponent },
  { path: 'user/signup', component: RegisterComponent },
  {
    path: 'recipes',
    children: [
      {
        path: '',
        component: RecipeListComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'import',
        component: ImportRecipeComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: ':id',
        component: RecipeDetailComponent,
        canActivate: [AuthGuardService],
        resolve: {
          recipe: RecipeResolverService
        }
      }
    ]
  },
  {
    path: 'brews',
    children: [
      {
        path: '',
        component: BrewListComponent,
        canActivate: [AuthGuardService]
      },
      { path: 'add',
        component: BrewAddComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: ':id',
        component: BrewDetailComponent,
        canActivate: [AuthGuardService],
        canDeactivate: [ BrewEditGuard ]
      },
      {
        path: ':id/measurements',
        component: MeasurementListComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: ':id/measurement/:measurementId',
        component: MeasurementDetailComponent,
        canActivate: [AuthGuardService],
        canDeactivate: [ MeasurementEditGuard ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {}
