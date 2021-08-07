import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AbvGaugeComponent } from '@app/recipe/components/abv-gauge/abv-gauge.component';
import { IbuGaugeComponent } from '@app/recipe/components/ibu-gauge/ibu-gauge.component';
import { ImportRecipeComponent } from '@app/recipe/components/import-recipe/import-recipe.component';
import { RecipeColourComponent } from '@app/recipe/components/recipe-colour/recipe-colour.component';
import { RecipeDetailComponent } from '@app/recipe/components/recipe-detail/recipe-detail.component';
import { RecipeListComponent } from '@app/recipe/components/recipe-list/recipe-list.component';
import { Top5recipesComponent } from '@app/recipe/components/top5recipes/top5recipes.component';
import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgxsModule } from '@ngxs/store';
import { NgRoundPipeModule } from 'angular-pipes';
import { NgxGaugeModule } from 'ngx-gauge';
import { IbuFormatPipe } from '@app/recipe/pipes/ibu-format.pipe';
import { AbvFormatPipe } from '@app/recipe/pipes/abv-format.pipe';
import { SelectRecipeComponent } from '@app/recipe/components/select-recipe/select-recipe.component';
import { UiModule } from '@app/common/ui/ui.module';

@NgModule({
  imports: [
    BrowserModule,
    NgRoundPipeModule,
    FontAwesomeModule,
    NgxsModule.forFeature([RecipeState]),
    NgxGaugeModule,
    FormsModule,
    UiModule,
  ],
  declarations: [
    RecipeListComponent,
    ImportRecipeComponent,
    RecipeDetailComponent,
    RecipeColourComponent,
    AbvGaugeComponent,
    IbuGaugeComponent,
    Top5recipesComponent,
    IbuFormatPipe,
    AbvFormatPipe,
    SelectRecipeComponent,
  ],
  providers: [RecipeService, RecipeResolverService],
  exports: [
    RecipeListComponent,
    RecipeDetailComponent,
    RecipeColourComponent,
    ImportRecipeComponent,
    AbvGaugeComponent,
    IbuGaugeComponent,
    Top5recipesComponent,
    IbuFormatPipe,
    AbvFormatPipe,
    SelectRecipeComponent
  ]
})
export class RecipeModule {}
