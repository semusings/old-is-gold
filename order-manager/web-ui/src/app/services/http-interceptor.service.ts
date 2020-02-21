import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private auth: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const {authHeader, authToken} = {authHeader: 'Authorization', authToken: 'Bearer'};
    const token = this.auth.getToken();
    const isAuthenticated = this.auth.isAuthenticated();
    const newReq =
      isAuthenticated && !req.headers.has(authHeader)
        ? req.clone({setHeaders: {[authHeader]: `${authToken} ${token}`}}) : req;
    const newReq1 = !newReq.headers.has('Content-Type')
      ? newReq.clone({setHeaders: {'Content-Type': 'application/json'}})
      : newReq;
    return next.handle(newReq1)
      .pipe(catchError(
        (error: any, caught: Observable<HttpEvent<any>>) => {
          console.error(error);
          throw error;
        }
      ));
  }

}
