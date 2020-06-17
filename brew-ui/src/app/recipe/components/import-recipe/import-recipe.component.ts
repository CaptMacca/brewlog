import { HttpClient, HttpRequest, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthState } from '@app/auth/state/auth.state';
import { LoadRecipes } from '@app/recipe/state/recipe.actions';
import { environment } from '@env/environment';
import { Store } from '@ngxs/store';
import { NzMessageService, UploadFile } from 'ng-zorro-antd';
import { filter, finalize } from 'rxjs/operators';

@Component({
  selector: 'app-import-recipe',
  templateUrl: './import-recipe.component.html',
  styleUrls: ['./import-recipe.component.css']
})
export class ImportRecipeComponent implements OnInit {
  private url: string = environment.recipeUploadApiUrl;
  uploading = false;
  fileList: UploadFile[] = [];

  constructor(
    private readonly store: Store,
    private readonly router: Router,
    private readonly message: NzMessageService,
    private readonly http: HttpClient
  ) {}

  ngOnInit() {}

  beforeUpload = (file: UploadFile): boolean => {
    this.fileList = this.fileList.concat(file);
    return false;
  };

  handleUpload(): void {
    const accessToken = this.store.selectSnapshot(AuthState.getAccessToken);
    const username = this.store.selectSnapshot(AuthState.getUsername);
    this.uploading = true;
    const formData = new FormData();
    // tslint:disable-next-line:no-any
    this.fileList.forEach((file: any) => {
      formData.append('files', file);
    });
    formData.append('user', username);
    const req = new HttpRequest('POST', this.url, formData, {
      reportProgress: true
    });
    req.headers.append('Bearer', accessToken);
    this.http.request(req).pipe(
        filter(e => e instanceof HttpResponse),
        finalize(() => {
          this.uploading = false;
          this.fileList = [];
        })
      ).subscribe(
        () => {},
        () => {
          this.message.error('upload failed.');
        },
        () => {
          this.message.success(`All Recipe file(s) have been uploaded`);
          this.store.dispatch(new LoadRecipes(username));
        }
      );
  }

  gotoRecipes() {
    this.router.navigate(['/main/recipes']);
  }
}
