import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrewAddComponent } from '@app/brew/components/brew-add/brew-add.component';
import { BrewDetailComponent } from '@app/brew/components/brew-detail/brew-detail.component';
import { BrewListComponent } from '@app/brew/components/brew-list/brew-list.component';
import { Recent5brewsComponent } from '@app/brew/components/recent5brews/recent5brews.component';
import { BrewEditGuard } from '@app/brew/services/brew-guard.service';
import { BrewService } from '@app/brew/services/brew.service';
import { BrewState } from '@app/brew/state/brew.state';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';
import { NgxsModule } from '@ngxs/store';
import { RecipeModule } from '@app/recipe/recipe.module';
import { IntialEstimatesComponent } from '@app/brew/components/brew-add/subcomponents/initial-estimates/intial-estimates.component';
import { SummaryComponent } from '@app/brew/components/brew-add/subcomponents/summary/summary.component';
import { BrewSessionComponent } from '@app/brew/components/brew-detail/subcomponents/brew-session/brew-session.component';
import { BrewNotesComponent } from '@app/brew/components/brew-detail/subcomponents/brew-notes/brew-notes.component';
import { BrewMeasurementsComponent } from '@app/brew/components/brew-detail/subcomponents/brew-measurements/brew-measurements.component';
import { MeasurementService } from '@app/brew/services/measurement.service';
import { BrewResolverService } from '@app/brew/services/brew-resolver.service';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';
import { UiModule } from '@app/common/ui/ui.module';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    FontAwesomeModule,
    NgxsModule.forFeature([BrewState]),
    NgxsFormPluginModule,
    RecipeModule,
    RxReactiveFormsModule,
    UiModule,
    ],
  declarations: [
    BrewListComponent,
    BrewDetailComponent,
    BrewAddComponent,
    Recent5brewsComponent,
    IntialEstimatesComponent,
    SummaryComponent,
    BrewSessionComponent,
    BrewNotesComponent,
    BrewMeasurementsComponent
  ],
  providers: [BrewService, BrewEditGuard, MeasurementService, BrewResolverService],
  exports: [
    BrewListComponent,
    BrewDetailComponent,
    BrewAddComponent,
    Recent5brewsComponent,
    IntialEstimatesComponent,
    SummaryComponent,
    BrewSessionComponent,
    BrewNotesComponent,
    BrewMeasurementsComponent
  ]
})
export class BrewModule {}
