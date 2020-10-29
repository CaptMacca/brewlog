import { registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import en from '@angular/common/locales/en';
import { ErrorHandler, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from '@app/app-routing.module';
import { AppComponent } from '@app/app.component';
import { LoginComponent } from '@app/auth/components/login/login.component';
import { RegisterComponent } from '@app/user/components/register/register.component';
import { AuthService } from '@app/auth/services/auth.service';
import { BrewModule } from '@app/brew/brew.module';
import { NavigationComponent } from '@app/navigation/navigation.component';
import { RecipeModule } from '@app/recipe/recipe.module';
import { WelcomeComponent } from '@app/welcome/welcome.component';
import { environment } from '@env/environment.prod';
import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faArrowLeft,
  faBan,
  faBeer,
  faCheck,
  faDatabase,
  faEdit,
  faEraser,
  faExclamationCircle,
  faEye,
  faPlus,
  fas,
  faSave,
  faTrash,
  faUpload
} from '@fortawesome/free-solid-svg-icons';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { NgxsStoragePluginModule, StorageOption } from '@ngxs/storage-plugin';
import { NgxsModule } from '@ngxs/store';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import { NgxGaugeModule } from 'ngx-gauge';
import { AuthGuardService } from './auth/services/auth-guard.service';
import { httpInterceptorProviders } from './auth/services/auth-interceptor.service';
import { AuthState } from './auth/state/auth.state';
import { UserState } from './user/state/user.state';
import { DashboardComponent } from './dashboard/dashboard.component';
import { IconsProviderModule } from './icons-provider.module';
import { ErrorHandlerService } from '@app/common/error-handler/error-handler.service';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { LoginLayoutComponent } from './layouts/login-layout/login-layout.component';
import { UiModule } from '@app/common/ui/ui.module';
import { EditRegistrationComponent } from './user/components/edit-registration/edit-registration.component';
import { ChangePasswordComponent } from './user/components/change-password/change-password.component';
import { UserService } from '@app/user/services/user.service';
import { UserDetailsResolverService } from '@app/user/services/user-details-resolver.service';
import { NgUpperFirstPipeModule } from 'angular-pipes';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    NavigationComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    MainLayoutComponent,
    LoginLayoutComponent,
    EditRegistrationComponent,
    ChangePasswordComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    RecipeModule,
    BrewModule,
    HttpClientModule,
    AppRoutingModule,
    FontAwesomeModule,
    NgxsStoragePluginModule.forRoot({
      key: 'auth',
      storage: StorageOption.SessionStorage
    }),
    NgxsModule.forRoot([AuthState, UserState], {
      developmentMode: !environment.production
    }),
    NgxsReduxDevtoolsPluginModule.forRoot(),
    NgxsLoggerPluginModule.forRoot(),
    NgxsFormPluginModule.forRoot(),
    UiModule,
    IconsProviderModule,
    NgxGaugeModule,
    RxReactiveFormsModule,
    NgUpperFirstPipeModule,
  ],
  providers: [
    AuthGuardService,
    httpInterceptorProviders,
    AuthService,
    UserService,
    UserDetailsResolverService,
    { provide: NZ_I18N, useValue: en_US },
    { provide: ErrorHandler, useClass: ErrorHandlerService }
  ],
  bootstrap: [AppComponent],
  exports: [RecipeModule, BrewModule]
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas);
    library.addIcons(
      faEye, faDatabase, faArrowLeft, faTrash, faCheck, faBan, faExclamationCircle, faUpload,
      faEdit, faEraser, faPlus, faBeer, faSave
    );
  }
}
