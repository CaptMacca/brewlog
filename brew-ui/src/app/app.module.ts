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
import { RegisterComponent } from '@app/auth/components/register/register.component';
import { AuthService } from '@app/auth/services/auth.service';
import { BrewModule } from '@app/brew/brew.module';
import { NavigationComponent } from '@app/navigation/navigation.component';
import { RecipeModule } from '@app/recipe/recipe.module';
import { WelcomeComponent } from '@app/welcome/welcome.component';
import { environment } from '@env/environment.prod';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
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
import { en_US, NgZorroAntdModule, NZ_I18N } from 'ng-zorro-antd';
import { NgxGaugeModule } from 'ngx-gauge';
import { AuthGuardService } from './auth/services/auth-guard.service';
import { httpInterceptorProviders } from './auth/services/auth-interceptor.service';
import { AuthState } from './auth/state/auth.state';
import { DashboardComponent } from './dashboard/dashboard.component';
import { IconsProviderModule } from './icons-provider.module';
import { ErrorHandlerService } from '@app/common/error-handler/error-handler.service';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { LoginLayoutComponent } from './layouts/login-layout/login-layout.component';

library.add(faEye, faDatabase, faArrowLeft, faTrash);
library.add(faCheck, faBan, faExclamationCircle, faUpload);
library.add(faEdit, faEraser, faPlus, faBeer, faSave);

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
    LoginLayoutComponent
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
    NgxsModule.forRoot([AuthState], {
      developmentMode: !environment.production
    }),
    NgxsReduxDevtoolsPluginModule.forRoot(),
    NgxsLoggerPluginModule.forRoot(),
    NgxsFormPluginModule.forRoot(),
    NgZorroAntdModule,
    IconsProviderModule,
    NgxGaugeModule,
    RxReactiveFormsModule
  ],
  providers: [
    AuthGuardService,
    httpInterceptorProviders,
    AuthService,
    { provide: NZ_I18N, useValue: en_US },
    { provide: ErrorHandler, useClass: ErrorHandlerService }
  ],
  bootstrap: [AppComponent],
  exports: [RecipeModule, BrewModule]
})
export class AppModule {}
