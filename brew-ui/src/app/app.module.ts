import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from '@app/app-routing.module';
import { AppComponent } from '@app/app.component';
import { LoginComponent } from '@app/auth/components/login/login.component';
import { RegisterComponent } from '@app/auth/components/register/register.component';
import { AuthService } from '@app/auth/services/auth.service';
import { BrewModule } from '@app/brew/brew.module';
import { ConfirmComponent } from '@app/common/confirm/confirm.component';
import { MeasurementModule } from '@app/measurement/measurement.module';
import { NavigationComponent } from '@app/navigation/navigation.component';
import { RecipeModule } from '@app/recipe/recipe.module';
import { WelcomeComponent } from '@app/welcome/welcome.component';
import { environment } from '@env/environment.prod';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faArrowLeft, faBan, faBeer, faCheck, faDatabase, faEdit, faEraser, faExclamationCircle, faEye, faPlus, faSave, faTrash, faUpload } from '@fortawesome/free-solid-svg-icons';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { NgxsStoragePluginModule, StorageOption } from '@ngxs/storage-plugin';
import { NgxsModule } from '@ngxs/store';
import { en_US, NgZorroAntdModule, NZ_I18N } from 'ng-zorro-antd';
import { SimpleModalModule } from 'ngx-simple-modal';
import { ToastrModule } from 'ngx-toastr';
import { AuthGuardService } from './auth/services/auth-guard.service';
import { httpInterceptorProviders } from './auth/services/auth-interceptor.service';
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
    NgxsModule.forRoot([AuthState], {
      developmentMode: !environment.production
    }),
    NgxsReduxDevtoolsPluginModule.forRoot(),
    NgxsLoggerPluginModule.forRoot(),
    NgxsFormPluginModule.forRoot(),
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-top-left',
      preventDuplicates: true
    }),
    NgZorroAntdModule,
  ],
  entryComponents: [ConfirmComponent],
  providers: [
    AuthGuardService,
    httpInterceptorProviders,
    AuthService,
    { provide: NZ_I18N, useValue: en_US}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
