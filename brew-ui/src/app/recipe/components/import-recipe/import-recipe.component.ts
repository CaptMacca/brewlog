import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { FileUploader } from 'ng2-file-upload/ng2-file-upload';
import { ToastrService } from 'ngx-toastr';
import { faCheck, faBan, faExclamationCircle } from '@fortawesome/free-solid-svg-icons';
import { Store } from '@ngxs/store';

import { environment } from '@env/environment';
import { LoadRecipes } from '@app/recipe/state/recipe.actions';
import { AuthState } from '@app/auth/state/auth.state';
import { RecipeService } from '@app/recipe/services/recipe.service';

@Component({
  selector: 'app-import-recipe',
  templateUrl: './import-recipe.component.html',
  styleUrls: ['./import-recipe.component.css']
})
export class ImportRecipeComponent implements OnInit {

  private url: string = environment.recipeUploadApiUrl;

  uploader: FileUploader;

  faCheck = faCheck;
  faBan = faBan;
  faExclamationCircle = faExclamationCircle;

  constructor(
    private store: Store,
    private router: Router,
    private toastr: ToastrService,
    private recipeService: RecipeService
    ) {}

  ngOnInit() {
    const accessToken = this.store.selectSnapshot(AuthState.getAccessToken);
    const username = this.store.selectSnapshot(AuthState.getUsername);
    this.uploader = new FileUploader({ url: this.url,  authToken: 'Bearer ' + accessToken});

    // Mock for now till we add user registration and login via oauth
    this.uploader.onBuildItemForm = (item, form) => {
      form.append('user', username);
    }

    this.uploader.onAfterAddingFile = file => {
      file.withCredentials = false;
    };

    this.uploader.onErrorItem = (item: any, response: any, status: any, headers: any) => {
      this.toastr.error(
        'File ' + item.file.name + ' uploaded failed. Reason : ' + response,
        'Error'
      );
    };

    this.uploader.onCompleteAll = () => {
      this.toastr.success('Recipe files have been uploaded', 'Success');
      this.recipeService.getRecipes(username).subscribe(
        recipes => this.store.dispatch(new LoadRecipes(recipes))
      );
    };
  }

  gotoRecipes() {
    this.router.navigate(['/recipes']);
  }
}
