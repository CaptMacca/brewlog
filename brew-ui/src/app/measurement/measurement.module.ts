import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CalendarModule } from 'primeng/calendar';
import { NgxsModule } from '@ngxs/store';

import { MeasurementDetailComponent } from '@app/measurement/components/measurement-detail/measurement-detail.component';
import { MeasurementListComponent } from '@app/measurement/components/measurement-list/measurement-list.component';
import { MeasurementService } from '@app/measurement/services/measurement.service';
import { MeasurementEditGuard } from '@app/measurement/services/measurement-guard.service';
import { MeasurementState } from './state/measurement.state';

@NgModule({
  imports: [
    CommonModule,
    CalendarModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    NgxsModule.forFeature([MeasurementState])
  ],
  declarations: [MeasurementDetailComponent, MeasurementListComponent],
  exports: [MeasurementDetailComponent, MeasurementListComponent],
  providers: [
    MeasurementService,
    MeasurementEditGuard
  ]
})
export class MeasurementModule {}
