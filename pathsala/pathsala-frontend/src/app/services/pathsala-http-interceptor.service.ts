import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PathsalaHttpInterceptorService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const newReq = !req.headers.has('Content-Type')
      ? req.clone({setHeaders: {'Content-Type': 'application/json'}})
      : req;
    return next.handle(newReq)
      .pipe(catchError(
        (error: any, caught: Observable<HttpEvent<any>>) => {
          console.error(error);
          throw error;
        }
      ));
  }

}
