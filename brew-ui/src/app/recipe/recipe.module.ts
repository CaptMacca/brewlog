import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ImportRecipeComponent } from '@app/recipe/components/import-recipe/import-recipe.component';
import { RecipeColourComponent } from '@app/recipe/components/recipe-colour/recipe-colour.component';
import { RecipeDetailComponent } from '@app/recipe/components/recipe-detail/recipe-detail.component';
import { RecipeListComponent } from '@app/recipe/components/recipe-list/recipe-list.component';
import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { RecipeState } from '@app/recipe/state/recipe.state';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgxsModule } from '@ngxs/store';
import { NgMathPipesModule } from 'angular-pipes';
import { FileUploadModule } from 'ng2-file-upload';
import { AbvGaugeComponent } from './components/abv-gauge/abv-gauge.component';
import { IbuGaugeComponent } from './components/ibu-gauge/ibu-gauge.component';



@NgModule({
  imports: [
    BrowserModule,
    FileUploadModule,
    NgMathPipesModule,
    FontAwesomeModule,
    NgxsModule.forFeature([RecipeState])
  ],
  declarations: [
    RecipeListComponent,
    ImportRecipeComponent,
    RecipeDetailComponent,
    RecipeColourComponent,
    RecipeListComponent,
    AbvGaugeComponent,
    IbuGaugeComponent,
  ],
  providers: [RecipeService, RecipeResolverService],
  exports: [
    RecipeListComponent,
    RecipeDetailComponent,
    RecipeColourComponent,
    ImportRecipeComponent,
    AbvGaugeComponent,
    IbuGaugeComponent,
  ]
})
export class RecipeModule {}
