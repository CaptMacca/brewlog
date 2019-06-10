import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CalendarModule } from 'primeng/calendar';
import { NgxsModule } from '@ngxs/store';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';

import { BrewListComponent } from '@app/brew/components/brew-list/brew-list.component';
import { BrewAddComponent } from '@app/brew/components/brew-add/brew-add.component';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { BrewEditGuard } from '@app/brew/services/brew-guard.service';
import { BrewService } from '@app/brew/services/brew.service';
import { MeasurementModule } from '@app/measurement/measurement.module';
import { BrewState } from '@app/brew/state/brew.state';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CalendarModule,
    MeasurementModule,
    FontAwesomeModule,
    NgxsModule.forFeature([BrewState]),
    NgxsFormPluginModule
  ],
  declarations: [
    BrewListComponent,
    BrewDetailComponent,
    BrewAddComponent
  ],
  providers: [
    BrewService,
    BrewEditGuard
  ],
  exports: [
    BrewListComponent,
    BrewDetailComponent,
    BrewAddComponent
  ]
})
export class BrewModule { }
