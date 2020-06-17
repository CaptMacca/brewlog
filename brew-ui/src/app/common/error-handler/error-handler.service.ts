import { ErrorHandler, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NzMessageService } from 'ng-zorro-antd';

@Injectable()
export class ErrorHandlerService implements ErrorHandler {

  constructor(
    private readonly message: NzMessageService
  ) {}

  public handleError(error: any) {

    if (error instanceof HttpErrorResponse) {
      const message = error.error.message;
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error.message}`);
    } else {
       console.error('An error occur', error.message);
    }
    // return error to angular
    return error;
  }
}
