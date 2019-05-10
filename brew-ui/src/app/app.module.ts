import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { ToastrModule } from 'ngx-toastr';
import { SimpleModalModule } from 'ngx-simple-modal';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
  faEye,
  faDatabase,
  faArrowLeft,
  faTrash,
  faCheck,
  faBan,
  faExclamationCircle,
  faUpload,
  faEdit,
  faEraser,
  faPlus,
  faBeer,
  faSave
} from '@fortawesome/free-solid-svg-icons';
import { NgxsModule } from '@ngxs/store';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';

import { AppComponent } from '@app/app.component';
import { WelcomeComponent } from '@app/welcome/welcome.component';
import { ConfirmComponent } from '@app/common/confirm/confirm.component';
import { ErrorHandlerService } from '@app/common/error-handler/error-handler.service';
import { NavigationComponent } from '@app/navigation/navigation.component';
import { RecipeModule } from '@app/recipe/recipe.module';
import { BrewModule } from '@app/brew/brew.module';
import { MeasurementModule } from '@app/measurement/measurement.module';
import { AppRoutingModule } from '@app/app-routing.module';
import { AuthService } from '@app/auth/services/auth.service';
import { RegisterComponent } from '@app/auth/components/register/register.component';
import { LoginComponent } from '@app/auth/components/login/login.component';
import { httpInterceptorProviders } from './auth/services/auth-interceptor.service';
import { AuthGuardService } from './auth/services/auth-guard.service';
import { NgxsStoragePluginModule, StorageOption } from '@ngxs/storage-plugin';
import { AuthState } from './auth/state/auth.state';

library.add(faEye, faDatabase, faArrowLeft, faTrash);
library.add(faCheck, faBan, faExclamationCircle, faUpload);
library.add(faEdit, faEraser, faPlus, faBeer, faSave);

@NgModule({
  declarations:
    [ AppComponent, WelcomeComponent, ConfirmComponent, NavigationComponent,
      LoginComponent, RegisterComponent],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    // AuthModule,
    RecipeModule,
    BrewModule,
    MeasurementModule,
    HttpClientModule,
    AppRoutingModule,
    SimpleModalModule,
    FontAwesomeModule,
    NgxsStoragePluginModule.forRoot({
      key: 'auth',
      storage: StorageOption.SessionStorage
    }),
    NgxsModule.forRoot([AuthState]),
    NgxsReduxDevtoolsPluginModule.forRoot(),
    NgxsLoggerPluginModule.forRoot(),
    NgxsFormPluginModule.forRoot(),
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-top-left',
      preventDuplicates: true
    })
  ],
  entryComponents: [ConfirmComponent],
  providers: [AuthGuardService, httpInterceptorProviders, AuthService],
  bootstrap: [AppComponent]
})
export class AppModule {}
