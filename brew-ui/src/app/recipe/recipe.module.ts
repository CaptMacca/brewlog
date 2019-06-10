import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgxsModule } from '@ngxs/store';
import { FileUploadModule } from 'ng2-file-upload';
import { NgMathPipesModule } from 'angular-pipes';

import { RecipeListComponent } from '@app/recipe/components/recipe-list/recipe-list.component';
import { RecipeService } from '@app/recipe/services/recipe.service';
import { ImportRecipeComponent } from '@app/recipe/components/import-recipe/import-recipe.component';
import { RecipeDetailComponent } from '@app/recipe/components/recipe-detail/recipe-detail.component';
import { RecipeColourComponent } from '@app/recipe/components/recipe-colour/recipe-colour.component';
import { RecipeResolverService } from '@app/recipe/services/recipe-resolver.service';
import { RecipeState } from '@app/recipe/state/recipe.state';

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
    RecipeColourComponent
  ],
  providers: [RecipeService, RecipeResolverService],
  exports: [
    RecipeListComponent,
    RecipeDetailComponent,
    RecipeColourComponent,
    ImportRecipeComponent
  ]
})
export class RecipeModule {}
