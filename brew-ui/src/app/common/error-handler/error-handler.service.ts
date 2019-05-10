import { ErrorHandler, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { throwError as observableThrowError } from 'rxjs';
@Injectable()
export class ErrorHandlerService implements ErrorHandler {
  public handleError(error: any) {
    const message = error.message;
    console.log('In error handler:' + message);

    if (error.error instanceof HttpErrorResponse) {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.message}`);
    } else {
      console.error('An error occur', error.message);
    }
    // return an observable with a user-facing error message
    return observableThrowError('An Error occured.');
  }
}
